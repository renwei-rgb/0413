@echo off

:: 停止服务
winsw.exe stop atm-eureka.xml

:: 卸载服务
winsw.exe uninstall atm-eureka.xml
