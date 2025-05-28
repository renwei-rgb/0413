@echo off

:: 安装服务
cd atm-eureka
winsw.exe install atm-eureka.xml

:: 启动服务
winsw.exe start atm-eureka.xml

:: 检查服务状态
winsw.exe status atm-eureka.xml
