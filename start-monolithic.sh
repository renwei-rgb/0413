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

echo -e "${GREEN}Starting ATM Monolithic Application...${NC}"
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

# 3. 启动 ATM Monolithic Application
echo -e "${GREEN}Starting ATM Monolithic Application...${NC}"
cd atm-monolithic
mvn spring-boot:run > ../logs/atm-monolithic.log 2>&1 &
cd ..
if [ $? -eq 0 ]; then
    echo -e "${GREEN}ATM Monolithic Application started successfully${NC}"
else
    echo -e "${RED}Failed to start ATM Monolithic Application${NC}"
    exit 1
fi

echo -e "${GREEN}All services started successfully!${NC}"
echo "==========================="
echo "MySQL: localhost:13306"
echo "Redis: localhost:6379"
echo "ATM Application: http://localhost:8080"

# 保持脚本运行
wait 