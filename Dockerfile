FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /build
COPY imdb-converter-lib/pom.xml .
COPY imdb-converter-lib/src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/target/IMDBConverter-1.0-jar-with-dependencies.jar app.jar

# Define volume for data
VOLUME /data

# Default command: runs the app. 
# Users should mount /data and pass arguments like: -d /data -f
ENTRYPOINT ["java", "-cp", "app.jar", "IMDBToTurtleCLApp"]
