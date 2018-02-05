# XML document processor

## Prerequisites

MySQL - I am using Ver 14.14 Distrib 5.7.21, for Linux (x86_64)

Import sql script ./DB/schema.sql

Java 1.8 with Spring Boot

## To run

Script
```bash
cd xml-processor
mvn spring-boot:run
```

# To list all APIs
Go to
```bash
http://localhost:8181/swagger-ui.html
```

## To use
If the application is running send a POST request to:
```bash
http://localhost:8181/document
```
With ./resources/input.xml file as input.