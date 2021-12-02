# Mortage plan
A rest api for calculating loan

This appliacation can calculate loan according to mortgage plan and also can read a text file and print out the result for each customer.

## Getting Started

To install this application, run the following commands:

```bash
git clone 
Import project in your IDN and go to project checkout location in your system.
```

To install all of its dependencies and start the application, follow the instructions below.
 
```bash
./mvnw spring-boot:run
```
The app runs on http://localhost:9090/

Then, in another window, send one customer data with http://localhost:9090/prospects with curl or postman or your web browser: 

```
$ curl -d '{ "customerName": "Juha", "totalLoan": 1000.0, "interest": 5.0, "years": 2 }' -H 'Content-Type: application/json' http://localhost:9090/prospect
```

Click Add New Prospect link to process new prospect

```
Software Requirements

1. Java 1.8
2. IDE
3. Postgres SQL

```