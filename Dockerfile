FROM node:latest

WORKDIR /app

# 安装 Windsurf
RUN npm install -g windsurf

# 设置环境变量
ENV PATH /app/node_modules/.bin:$PATH

# 暴露端口
EXPOSE 3000

# 启动命令
CMD ["windsurf"] 