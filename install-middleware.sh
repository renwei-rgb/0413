#!/bin/bash

# 设置颜色
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}Installing middleware services...${NC}"
echo "==========================="

# 1. 安装 MySQL
echo -e "${GREEN}Installing MySQL...${NC}"
if ! command -v mysql &> /dev/null; then
    yum install -y mysql-server
    systemctl enable mysqld
    systemctl start mysqld
    echo "MySQL installed successfully"
else
    echo "MySQL is already installed"
fi

# 2. 安装 Redis
echo -e "${GREEN}Installing Redis...${NC}"
if ! command -v redis-server &> /dev/null; then
    yum install -y redis
    systemctl enable redis
    systemctl start redis
    echo "Redis installed successfully"
else
    echo "Redis is already installed"
fi

# 3. 安装 Nacos
echo -e "${GREEN}Installing Nacos...${NC}"
if [ ! -d "nacos" ]; then
    # 下载 Nacos
    wget https://github.com/alibaba/nacos/releases/download/2.2.4/nacos-server-2.2.4.tar.gz
    tar -xzf nacos-server-2.2.4.tar.gz
    rm nacos-server-2.2.4.tar.gz
    mv nacos nacos-2.2.4
    echo "Nacos installed successfully"
else
    echo "Nacos is already installed"
fi

# 4. 安装 RocketMQ
echo -e "${GREEN}Installing RocketMQ...${NC}"
if [ ! -d "rocketmq" ]; then
    # 下载 RocketMQ
    wget https://archive.apache.org/dist/rocketmq/4.9.7/rocketmq-all-4.9.7-bin-release.zip
    unzip rocketmq-all-4.9.7-bin-release.zip
    rm rocketmq-all-4.9.7-bin-release.zip
    mv rocketmq-4.9.7 rocketmq
    echo "RocketMQ installed successfully"
else
    echo "RocketMQ is already installed"
fi

echo -e "${GREEN}All middleware services installed successfully!${NC}"
echo "==========================="
echo "Please run start-middleware.sh to start all services" 