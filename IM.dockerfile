FROM maven:3.9-amazoncorretto-21 AS builder
WORKDIR /app

# 1. 先複製 pom.xml，利用快取機制來加速依賴下載
COPY pom.xml .
COPY CustomerService/pom.xml CustomerService/pom.xml
COPY IM/pom.xml IM/pom.xml

# 2. 預先下載所有的 Maven 依賴，避免每次構建時重複下載
RUN mvn dependency:go-offline -f ./IM/pom.xml

# 3. 現在才複製 src 以減少 Docker cache 失效的機會
COPY IM/src IM/src

# 4. 建置專案，並跳過測試
RUN mvn package -f ./IM/pom.xml -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

COPY --from=builder /app/IM/target/*.jar ./IM.jar
CMD ["java", "-jar", "IM.jar"]
