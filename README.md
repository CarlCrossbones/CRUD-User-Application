# CRUD-User-Application
A CRUD (Create, Read, Update, Delete) User Application consisting of a Java SpringBoot API backend utilizing Java Persistence API and a PostgreSQL database housing Users (name, birth state, age).

Version: 1.0.0

### Table of Contents
- [Local Dependencies](#local-dependencies)
- [Backend Configuration](#backend-configuration)
- [Database Configuration](#database-configuration)
- [Running The Service](#running-the-service)
    - [Running Outside of a Container](#running-outside-of-a-container)
    - [Local Tests](#local-tests)
    - [Running The Containerized Service](#running-the-containerized-service)
    - [Smoke Tests](#smoke-tests)
- [Features of Note](#features-of-note)
- [Development Log](#development-log)
- [Future Work](#future-work)

## Local Dependencies
To run the service locally, you will need to install the following on your system:
- openjdk@24
- Maven 3.9.10
- docker:latest

## Backend Configuration
The backend is run using Spring Data Java Persistence API. There are four key endpoints following the CRUD operations of persistent storage:
- CREATE: `/users/create`
- READ: `/users`
- UPDATE: `/users/update`
- DELETE: `/users/delete`

These endpoints follow RESTful standards, with the types of calls being GET, POST, PATCH, and DELETE. Furthermore, the Java classes are documented with standard Javadoc styles and should adhere to the Google Java Style guide.
- **Note**: For specific details about each endpoint, see the [OpenAPI Specification Chart](/docs/api-spec.yaml). This chart is best viewed using [Swagger UI](https://swagger.io/tools/swagger-ui/).

## Database Configuration
The service utilizes a PostgreSQL database for storage of user information. This database is hosted in a docker container via `docker-compose`. To run this database for local testing and development, ensure docker is running and run the following command:
```bash
docker compose up --build postgres
```

This will allow for connections to the database via `localhost:5432/userdb`.

To kill the container, exit the Postgres viewer via Control-C and run the following command:
```bash
docker compose down -v
```
**Database Credentials**
- Database Name: userdb
- Database Username: user
- Database Password: password

## Running The Service

### Running Outside of a Container
For local development and testing, the Postgres database must be running. Please refer to [Database Configuration](#database-configuration) for help doing that. Before running either tests or the development server, you will need an `application.properties` file in the root of the project directory. See [application.properties.example](/docs/application.properties.example) for details.

To run the service, issue the following command:
```bash
mvn spring-boot:run
```


### Local Tests
Unit tests are run with full integration with the Postgres database. To begin testing, ensure that the Postgres database is running by referring to [Database Configuration](#database-configuration).
In a separate terminal from where you are running Postgres, run the tests by running:
```bash
mvn clean test
```

### Running the Containerized Service

To run the whole service, first ensure that docker is running. Upon doing so, run the following command in the root of the repository directory:
```bash
docker compose up
```

This will build both the Postgres database and the CRUD User Application. It will also handle integration of the two services.

Once Spring is started, you may run [smoke tests](#smoke-tests) to demonstrate the service, or navigate to `localhost:8080/user` to begin running your own tests. Please reference [api-spec.yaml](/docs/api-spec.yaml) for more information.

### Smoke Tests
Preset Postman tests have been set up in [Smoke Tests](/smoke-tests/CRUD%20User%20Application%20Smoke%20Tests.postman_collection.json). To use this collection, open Postman, or the VSCode Postman extension and import the collection. You can then run the tests as needed. There is no set order. Therefore, if you want to test deleting a user, you would first have to test creating a user, etc.

## Features of Note
- The READ endpoint, `/users` is able to return a list of all users, a list of a specific user, or a list of users meeting a certain criteria based on query parameters. Thus, `/users` can retain its status as a GET endpoint while note requiring a request body nor any extension of the url.

## Development Log
- 1.0.0 Release 1
- 0.5.2 Polished README
- 0.5.1 Cleaned up documentation
- 0.5.0 Cleaned up imports
- 0.4.0 Added Delete Endpoint `/users/delete`
- 0.3.0 Added Update Endpoint `/users/update`
- 0.2.0 Added Create endpoint `/users/create`
- 0.1.0 Added Read endpoint `/users`
- 0.0.6 Create Postman Preset Smoke Test File
- 0.0.5 Added integration/unit tests for Create & Read Endpoints
- 0.0.4 Documented Test Integration
- 0.0.3 Initialized Docker Containerization
- 0.0.2 Initialized Project
- 0.0.1 Added Documentation Template

## Future Work
- Add comprehensive input validation
- Complete a Front End component
- Make testing a part of the containerization process (CI/CD pipeline)
- Add quality testing
- Add a "Delete All" endpoint
- Add functionality to create multiple users at once
- Add functionality to update multiple users at once
- Add functionality to delete multiple users at once
