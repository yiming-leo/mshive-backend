FROM openjdk:8-jdk-alpine

LABEL maintainer="yimingleo@outlook.com"

WORKDIR /app

# 将 jar 文件复制到容器内
COPY ./mshive-backend.jar /app/mshive-backend.jar

# 暴露容器的端口号（假设你的应用使用8080端口）
EXPOSE 8080

# 在容器启动时运行的命令
CMD echo "Stopping old Docker container if it exists..." \
    && docker stop mshive-backend || true \
    && docker rm mshive-backend || true \
    && echo "Running new Docker container..." \
    && docker run -d -p 8080:8080 --name mshive-backend mshive-backend