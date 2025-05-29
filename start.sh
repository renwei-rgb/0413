#!/bin/bash

echo "=== Starting MySQL ==="
docker-compose up -d mysql
echo "Waiting for MySQL to be ready..."
sleep 20

echo "=== Starting Nacos ==="
docker-compose up -d nacos
echo "Waiting for Nacos to be ready..."
sleep 30

echo "=== Starting Eureka ==="
docker-compose up -d eureka
echo "Waiting for Eureka to be ready..."
sleep 20

echo "=== Starting Gateway ==="
docker-compose up -d gateway
echo "Waiting for Gateway to be ready..."
sleep 20

echo "=== Starting Nginx ==="
docker-compose up -d nginx
echo "Waiting for Nginx to be ready..."
sleep 10

echo "=== All services started ==="
echo "Nacos Dashboard: http://localhost:18848/nacos"
echo "Eureka Dashboard: http://localhost:8761"
echo "Gateway: http://localhost:8082"
echo "Nginx: http://localhost:8085" 