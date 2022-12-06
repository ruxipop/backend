#buildkit is activated by default on host docker
FROM maven:3.8.6-openjdk-18 AS builder
#WORKDIR /opt/automotive-bootcamp/
WORKDIR /app
COPY pom.xml pom.xml
RUN mvn -e -B dependency:resolve
COPY src/ src/
RUN mvn clean package -DskipTests

FROM openjdk:18-jdk-alpine3.14
WORKDIR /app
COPY --from=builder /app/target/*.jar energy.jar

ENV TZ=UTC
ENV DATABASE_IP=db
ENV DATABASE_PORT=5432
ENV DATABASE_USER=popruxi
ENV DATABASE_PASSWORD=ruxi
ENV DATABASE_DBNAME=db


COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]
