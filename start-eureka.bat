@echo off

:: 启动Eureka服务
cd atm-eureka
call mvn spring-boot:run

:: 等待Eureka启动后启动其他服务
timeout /t 30

cd ..\atm-auth
call mvn spring-boot:run

:: 等待Auth服务启动后启动其他服务
timeout /t 30

cd ..\atm-user
call mvn spring-boot:run

:: 等待User服务启动后启动网关
timeout /t 30

cd ..\atm-gateway
call mvn spring-boot:run
