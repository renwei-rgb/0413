@echo off
start cmd /k "cd atm-eureka && mvn spring-boot:run"
start cmd /k "cd atm-auth && mvn spring-boot:run"
start cmd /k "cd atm-user && mvn spring-boot:run"
start cmd /k "cd atm-gateway && mvn spring-boot:run"
start cmd /k "cd atm-check && mvn spring-boot:run"