# CRUD-User-Application
A CRUD (Create, Read, Update, Delete) User Application consisting of a Java SpringBoot API backend utilizing Java Persistence API and a PostgreSQL database housing Users (name, birth state, age).

Version: 0.0.3

### Table of Contents

## Backend Configuration

## Database Configuration
The service utilizes a PostgreSQL for storage of user information. This database is hosted in a docker container via `docker-compose`. To run this database for local testing, run the following command:
```bash
docker compose up --build postgres
```

This will allow for connections to the database via `localhost:5432/userdb`.

To kill the container, exit the Postgres viewer via Control-C and run the following command:
```bash
docker compose down -v
```

## Running the Service

### Running Outside of a Container

#### Local Dependencies

#### Local Tests

### Running the Containerized Service

To run the final product, first ensure that docker is running on your local device. Upon doing so, run the following command in the root of the repository directory:
```bash
docker compose up
```

This will build both the Postgres database and the CRUD User Application. It will also handle integration of the two services.

Once Spring is started, you may run [smoke tests](#smoke-tests) to demonstrate the service, or navigate to `localhost:8080/user` to begin running your own tests. Please reference [api-spec.yaml](/docs/api-spec.yaml) for more information.

### Smoke Tests

## Development Log
- 0.0.3 Initialized docker containerization
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
