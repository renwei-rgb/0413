#!/bin/bash

# 设置颜色
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 检查是否为root用户
if [ "$EUID" -ne 0 ]; then 
    echo -e "${RED}Please run as root${NC}"
    exit 1
fi

# 创建日志目录
mkdir -p logs

echo -e "${GREEN}Starting all Docker services...${NC}"
echo "==========================="

# 检查 Docker 是否安装
if ! command -v docker &> /dev/null; then
    echo -e "${YELLOW}Docker not installed, installing...${NC}"
    yum install -y yum-utils
    yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
    yum install -y docker-ce docker-ce-cli containerd.io
    systemctl start docker
    systemctl enable docker
fi

# 1. 启动 MySQL
echo -e "${GREEN}Starting MySQL...${NC}"
docker run -d \
    --name mysql \
    -p 13306:3306 \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_DATABASE=atm \
    mysql:8.0

# 2. 启动 Redis
echo -e "${GREEN}Starting Redis...${NC}"
docker run -d \
    --name redis \
    -p 6379:6379 \
    redis:6.2

# 3. 启动 Nacos
echo -e "${GREEN}Starting Nacos...${NC}"
docker run -d \
    --name nacos \
    -p 8848:8848 \
    -p 9848:9848 \
    -p 9849:9849 \
    -e MODE=standalone \
    -e SPRING_DATASOURCE_PLATFORM=mysql \
    -e MYSQL_SERVICE_HOST=mysql \
    -e MYSQL_SERVICE_PORT=3306 \
    -e MYSQL_SERVICE_USER=root \
    -e MYSQL_SERVICE_PASSWORD=root \
    -e MYSQL_SERVICE_DB_NAME=nacos \
    nacos/nacos-server:latest

# 等待 Nacos 完全启动
echo -e "${YELLOW}Waiting for Nacos to start...${NC}"
sleep 10

# 4. 启动 Gateway
echo -e "${GREEN}Starting Gateway...${NC}"
cd atm-gateway
mvn spring-boot:run > ../logs/gateway.log 2>&1 &
cd ..

# 5. 启动 Auth Service
echo -e "${GREEN}Starting Auth Service...${NC}"
cd atm-auth
mvn spring-boot:run > ../logs/auth.log 2>&1 &
cd ..

echo -e "${GREEN}All Docker services started successfully!${NC}"
echo "==========================="
echo "MySQL: localhost:13306"
echo "Redis: localhost:6379"
echo "Nacos: http://localhost:8848/nacos"
echo "Gateway: http://localhost:8082"

# 保持脚本运行
wait 