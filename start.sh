#!/bin/bash

echo "=== Starting MySQL ==="
docker-compose up -d mysql
echo "Waiting for MySQL to be ready..."
sleep 20

echo "=== Starting Nacos ==="
docker-compose up -d nacos
echo "Waiting for Nacos to be ready..."
sleep 30

echo "=== Starting Gateway ==="
docker-compose up -d atm-gateway
echo "Waiting for Gateway to be ready..."
sleep 20

echo "=== All services started ==="
echo "Nacos Dashboard: http://localhost:8848/nacos"
echo "Gateway: http://localhost:8080" 