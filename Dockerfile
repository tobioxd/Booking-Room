# build jar from source code using maven 
FROM maven:alpine AS build-stage
WORKDIR /build
# resolve dependencies 
COPY pom.xml /build/
RUN mvn verify --fail-never

# build jar
COPY . /build/
RUN ./mvnw clean package

FROM azul/zulu-openjdk-alpine:21-jre-headless-latest AS production-stage
WORKDIR /app
COPY --from=build-stage /build/target/*.jar app.jar
EXPOSE 80
CMD ["java", "-jar", "app.jar"]


