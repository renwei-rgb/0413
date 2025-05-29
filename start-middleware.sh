#!/bin/bash

# 设置颜色
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}Starting middleware services...${NC}"
echo "==========================="

# 1. 启动 MySQL
echo -e "${GREEN}Starting MySQL...${NC}"
if ! systemctl is-active --quiet mysqld; then
    systemctl start mysqld
    if [ $? -eq 0 ]; then
        echo "MySQL started successfully"
    else
        echo -e "${RED}Failed to start MySQL${NC}"
        exit 1
    fi
else
    echo "MySQL is already running"
fi

# 2. 启动 Nacos
echo -e "${GREEN}Starting Nacos...${NC}"
cd nacos-2.2.4/bin
if [ -f "startup.sh" ]; then
    ./startup.sh -m standalone
    if [ $? -eq 0 ]; then
        echo "Nacos started successfully"
    else
        echo -e "${RED}Failed to start Nacos${NC}"
        exit 1
    fi
else
    echo -e "${RED}Nacos startup script not found${NC}"
    exit 1
fi
cd ../..

# 3. 启动 Redis
echo -e "${GREEN}Starting Redis...${NC}"
if ! systemctl is-active --quiet redis; then
    systemctl start redis
    if [ $? -eq 0 ]; then
        echo "Redis started successfully"
    else
        echo -e "${RED}Failed to start Redis${NC}"
        exit 1
    fi
else
    echo "Redis is already running"
fi

# 4. 启动 RocketMQ
echo -e "${GREEN}Starting RocketMQ...${NC}"
# 启动 NameServer
if [ -d "rocketmq" ]; then
    cd rocketmq/bin
    nohup sh mqnamesrv > ../../logs/rocketmq-namesrv.log 2>&1 &
    sleep 5
    
    # 启动 Broker
    nohup sh mqbroker -n localhost:9876 > ../../logs/rocketmq-broker.log 2>&1 &
    cd ../..
    echo "RocketMQ started successfully"
else
    echo -e "${RED}RocketMQ directory not found${NC}"
    exit 1
fi

echo -e "${GREEN}All middleware services started successfully!${NC}"
echo "==========================="
echo "MySQL: localhost:3306"
echo "Nacos: http://localhost:8848/nacos"
echo "Redis: localhost:6379"
echo "RocketMQ: localhost:9876" 