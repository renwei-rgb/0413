@echo off
set JAVA_OPTS=-Dserver.port=8848 -Dspring.datasource.platform=mysql -Ddb.num=1 -Ddb.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true -Ddb.user=root -Ddb.password=rw123
java %JAVA_OPTS% -jar distribution/target/nacos-server-2.2.4.jar
