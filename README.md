# Kaychy Backend

Java 21 + Spring Boot modular monolith.

## Local infrastructure

docker compose up -d

If Postgres was previously started with different credentials or an invalid data
volume, recreate it with:

docker compose down -v
docker compose up -d

## Run

mvn spring-boot:run

## Endpoints

GET http://localhost:8080/api/v1/system/ping
GET http://localhost:8080/actuator/health
http://localhost:8080/swagger-ui.html

## Domain boundaries

auth/
customer/
tailor/
catalog/
order/
payment/
review/
notification/
admin/

Each business module will use:
api/
application/
domain/
infrastructure/
