
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# 複製從前一個階段打包好的 JAR 檔案
COPY /target/*.jar IM.jar

# 啟動應用程式
CMD ["java", "-jar", "IM.jar"]