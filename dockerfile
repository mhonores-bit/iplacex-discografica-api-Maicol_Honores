FROM gradle:jdk21 as builder

WORKDIR /app

COPY ./build.gradle . 
COPY ./settings.gradle .

COPY src ./src

RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/discografia-1.jar app.jar

EXPOSE 3000

CMD ["java", "-jar", "discografia-1.jar"]