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

echo -e "${GREEN}Starting all services...${NC}"
echo "==========================="

# 1. 启动 MySQL
echo -e "${GREEN}Starting MySQL...${NC}"
systemctl start mysqld
if [ $? -eq 0 ]; then
    echo -e "${GREEN}MySQL started successfully${NC}"
else
    echo -e "${RED}Failed to start MySQL${NC}"
    exit 1
fi

# 2. 启动 Redis
echo -e "${GREEN}Starting Redis...${NC}"
systemctl start redis
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Redis started successfully${NC}"
else
    echo -e "${RED}Failed to start Redis${NC}"
    exit 1
fi

# 3. 启动 Nacos
echo -e "${GREEN}Starting Nacos...${NC}"
cd nacos-2.2.4/bin
sh startup.sh -m standalone > ../../logs/nacos.log 2>&1 &
cd ../..
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Nacos started successfully${NC}"
else
    echo -e "${RED}Failed to start Nacos${NC}"
    exit 1
fi

# 等待 Nacos 完全启动
echo -e "${YELLOW}Waiting for Nacos to start...${NC}"
sleep 10

# 4. 启动 Eureka
echo -e "${GREEN}Starting Eureka...${NC}"
cd atm-eureka
mvn spring-boot:run > ../logs/eureka.log 2>&1 &
cd ..
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Eureka started successfully${NC}"
else
    echo -e "${RED}Failed to start Eureka${NC}"
    exit 1
fi

# 等待 Eureka 完全启动
echo -e "${YELLOW}Waiting for Eureka to start...${NC}"
sleep 10

# 5. 启动 Gateway
echo -e "${GREEN}Starting Gateway...${NC}"
cd atm-gateway
mvn spring-boot:run > ../logs/gateway.log 2>&1 &
cd ..
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Gateway started successfully${NC}"
else
    echo -e "${RED}Failed to start Gateway${NC}"
    exit 1
fi

# 6. 启动 Auth Service
echo -e "${GREEN}Starting Auth Service...${NC}"
cd atm-auth
mvn spring-boot:run > ../logs/auth.log 2>&1 &
cd ..
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Auth Service started successfully${NC}"
else
    echo -e "${RED}Failed to start Auth Service${NC}"
    exit 1
fi

echo -e "${GREEN}All services started successfully!${NC}"
echo "==========================="
echo "MySQL: localhost:13306"
echo "Redis: localhost:6379"
echo "Nacos: http://localhost:8848/nacos"
echo "Eureka: http://localhost:8761"
echo "Gateway: http://localhost:8082"

# 保持脚本运行
wait 