# Leisure Pass Management API

A tourism pass management solution.

## Installation

Package jar (it also will run all tests) then execute it:

```
mvn clean package
cd target 
java -jar leisurepass-1.0-SNAPSHOT.jar
```

## Usage

[http://localhost:8080/](http://localhost:8080) will redirect you to swagger-api.html page, where you can find methods to work with app.
The system allow customers to add, cancel, and renew a pass by next methods:
```
POST /leisure-service/customer/{customerId}/create
PUT /leisure-service/customer/{customerId}/cancel/{id}
PUT /leisure-service/customer/{customerId}/renew/{id}
```
The system allow vendors to verify if a pass is active by this method:
```
GET /leisure-service/vendor/{vendorId}/verify/{id}
```
Apllication can be started without prerequirments, it's achived by using h2 embedded data base
Passes persist through an application restart by properties:
```
spring.datasource.url=jdbc:h2:file:./data/passdb
spring.jpa.hibernate.ddl-auto=update
```

The solution covered by tests using SpringBoot test and JUnit5, it could be executed by:
```
mvn test
```

Pass ID is unique and it's achived by using UUID

To manage requests/responses added zalando.logbook

## Notes
1. Customer can cancel or renew only their passes 
2. Customer provide vendorId and startTime of the pass (timestamp in milleseconds) (could be provided by front-end or mobile app), and pass length also in milliseconds
3. To disactivate pass it turn period of lenght to zero. Possible solution - just remove the row from database (it will be DELETE Http method)
4. Pass isn't active if period of lenght + start time less tnen check time, or period of lenght is zero
5. Pass Id generated as UUID

For production database it's more resonable to use Cassandra or MongoDB