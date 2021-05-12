# quarkuscoffeeshop-majestic-monolith project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script

./mvnw compile quarkus:dev
```

## Running in prod
```shell script
export PGSQL_URL="jdbc:postgresql://localhost:5432/coffeeshopdb?currentSchema=coffeeshop" \
PGSQL_USER="coffeeshopuser" \
PGSQL_PASSWORD="redhat-21" \
PGSQL_URL_BARISTA="jdbc:postgresql://localhost:5432/coffeeshopdb?currentSchema=barista" \
PGSQL_USER_BARISTA="coffeeshopuser" \
PGSQL_PASSWORD_BARISTA="redhat-21" \
PGSQL_URL_KITCHEN="jdbc:postgresql://localhost:5432/coffeeshopdb?currentSchema=kitchen" \
PGSQL_USER_KITCHEN="coffeeshopuser" \
PGSQL_PASSWORD_KITCHEN="redhat-21"

docker run quarkuscoffeeshop/quarkuscoffeeshop-majestic-monolith -e PGSQL_URL=${PGSQL_URL} \
-e PGSQL_USER=${PGSQL_USER} \
-e PGSQL_PASSWORD=${PGSQL_PASSWORD} \
-e PGSQL_URL_BARISTA=${PGSQL_URL_BARISTA} \
-e PGSQL_USER_BARISTA=${PGSQL_USER_BARISTA} \
-e PGSQL_PASSWORD_BARISTA=${PGSQL_PASSWORD_BARISTA} \
-e PGSQL_URL_KITCHEN=${PGSQL_URL_KITCHEN} \
-e PGSQL_USER_KITCHEN=${PGSQL_USER_KITCHEN} \
-e PGSQL_PASSWORD_KITCHEN=${PGSQL_PASSWORD_KITCHEN}
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkuscoffeeshop-majestic-monolith-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkuscoffeeshop-majestic-monolith-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkuscoffeeshop-majestic-monolith-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# Qute example

<p>This example uses a Qute template to render a subatomic-particle generator web page.</p>
<p><b>Go subatomic!</b></p>

Quarkus guide: https://quarkus.io/guides/qute
Reference guide: https://quarkus.io/guides/qute-reference
# RESTEasy JSON serialisation using Jackson

<p>This example demonstrate RESTEasy JSON serialisation by letting you list, add and remove quark types from a list.</p>
<p><b>Quarked!</b></p>

Guide: https://quarkus.io/guides/rest-json
