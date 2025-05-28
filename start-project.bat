@echo off

:: 设置颜色
color 0A

:: 检查Java环境
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java not found! Please install Java first.
    pause
    exit /b 1
)

:: 设置项目根目录
set PROJECT_DIR=%cd%

:: 创建日志目录
if not exist logs mkdir logs

:: 启动顺序：
echo Starting project services...
echo ===========================

:: 1. 启动Nacos配置中心
echo Starting Nacos...
start cmd /k "cd atm-eureka && mvn spring-boot:run > %PROJECT_DIR%/logs/eureka.log 2>&1"
timeout /t 10

:: 2. 启动认证服务
echo Starting Auth Service...
start cmd /k "cd atm-auth && mvn spring-boot:run > %PROJECT_DIR%/logs/auth.log 2>&1"
timeout /t 5

:: 3. 启动用户服务
echo Starting User Service...
start cmd /k "cd atm-user && mvn spring-boot:run > %PROJECT_DIR%/logs/user.log 2>&1"
timeout /t 5

:: 4. 启动检查服务
echo Starting Check Service...
start cmd /k "cd atm-check && mvn spring-boot:run > %PROJECT_DIR%/logs/check.log 2>&1"
timeout /t 5

:: 5. 启动网关服务
echo Starting Gateway...
start cmd /k "cd atm-gateway && mvn spring-boot:run > %PROJECT_DIR%/logs/gateway.log 2>&1"
timeout /t 5

:: 6. 启动前端服务
echo Starting Frontend...
start cmd /k "cd tss-atm-web-vue && npm install && npm run serve > %PROJECT_DIR%/logs/frontend.log 2>&1"

echo All services started successfully!
echo ===========================
echo Logs are saved in %PROJECT_DIR%/logs directory
echo You can check each service's log for more details

pause
