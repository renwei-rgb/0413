@echo off
echo Starting services in correct order...

:: 1. Start Eureka service
echo Starting Eureka service...
cd atm-eureka
start mvn spring-boot:run

:: Wait for Eureka to start (10 seconds)
timeout /t 10

:: 2. Start Gateway service
echo Starting Gateway service...
cd ../atm-gateway
start mvn spring-boot:run

:: 3. Start Auth service
echo Starting Auth service...
cd ../atm-auth
start mvn spring-boot:run

:: 4. Start User service
echo Starting User service...
cd ../atm-user
start mvn spring-boot:run

:: 5. Start Check service
echo Starting Check service...
cd ../atm-check
start mvn spring-boot:run

echo All services have been started.
echo Please wait for them to fully initialize.
echo You can check the status at:
echo - Eureka: http://localhost:8761/eureka
echo - Gateway: http://localhost:8082
echo - Auth: http://localhost:9100
echo - User: http://localhost:9101
echo - Check: http://localhost:9102
