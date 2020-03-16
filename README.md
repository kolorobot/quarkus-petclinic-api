# Quarkus Pet Clinic API

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn how to build PetClinic REST API with Quarkus, please visit: https://blog.codeleak.pl/2020/02/getting-started-with-quarkus.html.

## Developing the application

- Run development database locally with Docker:

```
docker run -it --name petclinic-dev -p 5433:5432 -e POSTGRES_DB=petclinic-dev -e POSTGRES_USER=petclinic-dev -e POSTGRES_PASSWORD=petclinic-dev -d postgres:11.6-alpine
```

- Run your application in dev mode that enables live coding using:

```
./mvnw quarkus:dev
```

## Packaging and running the application

- Run database locally with Docker:

```
docker run -it --name petclinic-db -p 5432:5432 -e POSTGRES_DB=petclinic -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -d postgres:11.6-alpine
```

- Package the application:

```
./mvnw package
```

It produces the executable `quarkus-petclinic-api-1.0.1-runner.jar` file in `/target` directory with dependencies copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-petclinic-api-1.0.1-runner.jar`.

- Create `uber-jar`:

```
./mvnw clean package -Dquarkus.package.uber-jar=true
```

## Creating distribution for AWS Elastic Beanstalk

- Create distribution (zip) for AWS Elastic Beanstalk:

```
./mvnw clean package assembly:single -Dquarkus.package.uber-jar=true
```

This will create `zip` package with the `uber-jar`, `Dockerfile` and `config/application.properties`.

- Upload `quarkus-petclinic-api-1.0.1-eb.zip` to AWS Elastic Beanstalk.

>Learn how to launch single container Docker environment with Quarkus based application on Elastic Beanstalk: https://blog.codeleak.pl/2020/03/deploy-quarkus-application-to-elastic-beanstalk.html

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/quarkus-petclinic-api-1.0.1-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .