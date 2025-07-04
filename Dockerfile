# Gunakan base image openjdk 17
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy jar build ke dalam container
COPY target/*.jar app.jar

# Expose port untuk Render
EXPOSE 8080

# Jalankan aplikasi
ENTRYPOINT ["java","-jar","app.jar"]