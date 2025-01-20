


mvn clean install
mvn spring-boot:run


# docker run -e SPRING_PROFILES_ACTIVE=staging -e DB_URL=jdbc:h2:file:~/transferdb-staging -e DB_USERNAME=sa -e DB_PASSWORD=yourpassword -p 8080:8080 transfer-scheduler
mvnw.cmd clean package
docker build -t transfer-scheduler .
docker-compose up --build