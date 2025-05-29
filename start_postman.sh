#!/bin/bash

# 允许 X11 转发
xhost +local:docker

# 构建 Postman 镜像
docker build -t postman -f Dockerfile.postman .

# 运行 Postman 容器
docker run -d \
    --name postman \
    -e DISPLAY=$DISPLAY \
    -v /tmp/.X11-unix:/tmp/.X11-unix \
    -v $HOME/.config/Postman:/home/postman/.config/Postman \
    postman

echo "Postman 已启动！"
echo "如果看不到 Postman 窗口，请确保已安装 X11 转发并允许 X11 连接："
echo "xhost +local:docker" 