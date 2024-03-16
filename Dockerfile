FROM openjdk:8-jdk-alpine

LABEL maintainer="yimingleo@outlook.com"

WORKDIR /app

# 输出一句已运行
RUN echo "Dockerfile is working"

# 将 jar 文件复制到容器内
COPY ./mshive-backend.jar /app/mshive-backend.jar

# 暴露容器的端口号（假设你的应用使用8080端口）
EXPOSE 8080

# 在容器启动时运行的命令
CMD ["java", "-jar", "mshive-backend.jar"]