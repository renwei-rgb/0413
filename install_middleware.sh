#!/bin/bash

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m'

# 打印带颜色的信息
info() {
    echo -e "${GREEN}[INFO] $1${NC}"
}

warn() {
    echo -e "${YELLOW}[WARN] $1${NC}"
}

error() {
    echo -e "${RED}[ERROR] $1${NC}"
}

# 检查命令是否存在
check_command() {
    if ! command -v $1 &> /dev/null; then
        error "$1 未安装，正在安装..."
        return 1
    fi
    return 0
}

# 创建安装目录
MIDDLEWARE_DIR="/opt/middleware"
mkdir -p $MIDDLEWARE_DIR
cd $MIDDLEWARE_DIR

# 安装基础工具
info "安装基础工具..."
yum install -y wget unzip java-1.8.0-openjdk

# 安装 Nacos
info "安装 Nacos..."
if ! check_command "nacos"; then
    wget https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.zip
    unzip nacos-server-2.2.3.zip
    echo "export NACOS_HOME=$MIDDLEWARE_DIR/nacos" >> /etc/profile
    echo "export PATH=\$PATH:\$NACOS_HOME/bin" >> /etc/profile
    source /etc/profile
fi

# 安装 RabbitMQ
info "安装 RabbitMQ..."
if ! check_command "rabbitmq-server"; then
    yum install -y erlang
    yum install -y rabbitmq-server
    systemctl enable rabbitmq-server
    systemctl start rabbitmq-server
    rabbitmq-plugins enable rabbitmq_management
fi

# 安装 Elasticsearch
info "安装 Elasticsearch..."
if ! check_command "elasticsearch"; then
    wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.0-linux-x86_64.tar.gz
    tar -xzf elasticsearch-7.17.0-linux-x86_64.tar.gz
    echo "export ES_HOME=$MIDDLEWARE_DIR/elasticsearch-7.17.0" >> /etc/profile
    echo "export PATH=\$PATH:\$ES_HOME/bin" >> /etc/profile
    source /etc/profile
fi

# 安装 MinIO
info "安装 MinIO..."
if ! check_command "minio"; then
    wget https://dl.min.io/server/minio/release/linux-amd64/minio
    chmod +x minio
    mv minio /usr/local/bin/
fi

# 安装 MongoDB
info "安装 MongoDB..."
if ! check_command "mongod"; then
    cat > /etc/yum.repos.d/mongodb-org.repo << EOF
[mongodb-org-4.4]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/\$releasever/mongodb-org/4.4/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-4.4.asc
EOF
    yum install -y mongodb-org
    systemctl enable mongod
    systemctl start mongod
fi

# 安装 Kafka
info "安装 Kafka..."
if ! check_command "kafka-server-start"; then
    wget https://downloads.apache.org/kafka/3.4.0/kafka_2.13-3.4.0.tgz
    tar -xzf kafka_2.13-3.4.0.tgz
    echo "export KAFKA_HOME=$MIDDLEWARE_DIR/kafka_2.13-3.4.0" >> /etc/profile
    echo "export PATH=\$PATH:\$KAFKA_HOME/bin" >> /etc/profile
    source /etc/profile
fi

# 安装 ZooKeeper
info "安装 ZooKeeper..."
if ! check_command "zkServer.sh"; then
    wget https://downloads.apache.org/zookeeper/zookeeper-3.8.1/apache-zookeeper-3.8.1-bin.tar.gz
    tar -xzf apache-zookeeper-3.8.1-bin.tar.gz
    echo "export ZK_HOME=$MIDDLEWARE_DIR/apache-zookeeper-3.8.1-bin" >> /etc/profile
    echo "export PATH=\$PATH:\$ZK_HOME/bin" >> /etc/profile
    source /etc/profile
fi

# 创建启动脚本
info "创建启动脚本..."
cat > $MIDDLEWARE_DIR/start_all.sh << 'EOF'
#!/bin/bash

# 启动 Nacos
echo "启动 Nacos..."
cd $NACOS_HOME/bin
./startup.sh -m standalone

# 启动 RabbitMQ
echo "启动 RabbitMQ..."
systemctl start rabbitmq-server

# 启动 Elasticsearch
echo "启动 Elasticsearch..."
cd $ES_HOME
./bin/elasticsearch -d

# 启动 MinIO
echo "启动 MinIO..."
minio server /data --console-address ":9001" &

# 启动 MongoDB
echo "启动 MongoDB..."
systemctl start mongod

# 启动 ZooKeeper
echo "启动 ZooKeeper..."
cd $ZK_HOME
./bin/zkServer.sh start

# 启动 Kafka
echo "启动 Kafka..."
cd $KAFKA_HOME
./bin/kafka-server-start.sh -daemon config/server.properties

echo "所有中间件已启动！"
EOF

chmod +x $MIDDLEWARE_DIR/start_all.sh

# 创建停止脚本
info "创建停止脚本..."
cat > $MIDDLEWARE_DIR/stop_all.sh << 'EOF'
#!/bin/bash

# 停止 Nacos
echo "停止 Nacos..."
cd $NACOS_HOME/bin
./shutdown.sh

# 停止 RabbitMQ
echo "停止 RabbitMQ..."
systemctl stop rabbitmq-server

# 停止 Elasticsearch
echo "停止 Elasticsearch..."
pkill -f elasticsearch

# 停止 MinIO
echo "停止 MinIO..."
pkill -f minio

# 停止 MongoDB
echo "停止 MongoDB..."
systemctl stop mongod

# 停止 Kafka
echo "停止 Kafka..."
cd $KAFKA_HOME
./bin/kafka-server-stop.sh

# 停止 ZooKeeper
echo "停止 ZooKeeper..."
cd $ZK_HOME
./bin/zkServer.sh stop

echo "所有中间件已停止！"
EOF

chmod +x $MIDDLEWARE_DIR/stop_all.sh

# 打印访问信息
info "安装完成！"
echo "================================================"
echo "中间件访问信息："
echo "Nacos: http://localhost:8848/nacos (nacos/nacos)"
echo "RabbitMQ: http://localhost:15672 (guest/guest)"
echo "Elasticsearch: http://localhost:9200"
echo "MinIO: http://localhost:9001 (minioadmin/minioadmin)"
echo "MongoDB: mongodb://localhost:27017"
echo "Kafka: localhost:9092"
echo "ZooKeeper: localhost:2181"
echo "================================================"
echo "启动所有中间件：$MIDDLEWARE_DIR/start_all.sh"
echo "停止所有中间件：$MIDDLEWARE_DIR/stop_all.sh"
echo "================================================" 