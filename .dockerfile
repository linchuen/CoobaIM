#FROM maven:3.9-amazoncorretto-21 AS build
#WORKDIR /app
#
## 複製 pom.xml 和 src 目錄
#COPY pom.xml .
#COPY src ./src
#
## 使用 Maven 打包專案
#RUN mvn clean package -DskipTests
#
## 第二階段：使用 JDK 來執行應用程式
#FROM amazoncorretto:21-alpine-jdk
#WORKDIR /app
#
## 複製從前一個階段打包好的 JAR 檔案
#COPY --from=build /app/target/*.jar app.jar
#
## 啟動應用程式
#CMD ["java", "-jar", "app.jar"]


FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# 複製從前一個階段打包好的 JAR 檔案
COPY /target/*.jar app.jar

# 啟動應用程式
CMD ["java", "-jar", "app.jar"]