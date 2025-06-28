# CRUD-User-Application
A CRUD (Create, Read, Update, Delete) User Application consisting of a Java SpringBoot API backend utilizing Java Persistence API and a PostgreSQL database housing Users (name, birth state, age).

Version: 0.0.6

### Table of Contents
- [Local Dependencies](#local-dependencies)
- [Backend Configuration](#backend-configuration)
- [Database Configuration](#database-configuration)
- [Running The Service](#running-the-service)
    - [Running Outside of a Container](#running-outside-of-a-container)
    - [Local Tests](#local-tests)
    - [Running The Containerized Service](#running-the-containerized-service)
    - [Smoke Tests](#smoke-tests)
- [Development Log](#development-log)

## Local Dependencies
To run the service locally, you will need to install the following on your system:
- openjdk@24
- Maven 3.9.10
- docker:latest

## Backend Configuration

## Database Configuration
The service utilizes a PostgreSQL for storage of user information. This database is hosted in a docker container via `docker-compose`. To run this database for local testing and development, ensure docker is running and run the following command:
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
For local development and testing, the Postgres database must be running. Please refer to [Database Configuration](#database-configuration). Before running either tests or the development server, you will need an `application.properties` file in the root of the project directory. See [application.properties.example](/docs/application.properties.example) for details.


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

## Development Log
- 0.0.6 Create Postman Preset Smoke Test File
- 0.0.5 Added integration/unit tests for Create & Read Endpoints
- 0.0.4 Documented Test Integration
- 0.0.3 Initialized Docker Containerization
- 0.0.2 Initialized Project
- 0.0.1 Added Documentation Template



# Requirements
Create a CRUD application that allows users to be created and updated within a system. In this scenario, let's say a user has a name, age, and birth state. The application should:
- Create a public project in your github account
- Create all CRUD operations with endpoints so that they can be tested with Postman or a curl
- Create a backend container to house the CRUD operations
- Create a database container of your choice to store and manipulate new and existing users
- Utilize Docker for containerization
- Create a docker-compose file to spin up both containers
- Implement coding standards
- Create integration tests
- Compose a readme that shows how to test and run the application
- Feel free to add anything if time permists
- Complete by 6/30 end of day.
