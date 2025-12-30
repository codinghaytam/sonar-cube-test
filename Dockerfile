FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the main JAR file
COPY target/spaghetti-finance-1.0.0.jar app.jar

# Copy Prometheus dependencies
COPY lib/simpleclient-0.16.0.jar lib/
COPY lib/simpleclient_httpserver-0.16.0.jar lib/
COPY lib/simpleclient_hotspot-0.16.0.jar lib/
COPY lib/simpleclient_common-0.16.0.jar lib/

EXPOSE 8083

# Run the application with all dependencies in classpath
ENTRYPOINT ["java", "-cp", "app.jar:lib/*", "MainApp"]