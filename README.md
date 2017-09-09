# Sample spring boot rest client application using Kotlin #

This is a sample spring boot REST client application using [Kotlin](https://kotlinlang.org) language.

Application is bootstrapped using [Spring Initializr](http://start.spring.io)

## Modules used ##
* Web - for exposing the REST endpoints
* Actuator - for various metrics

[JSONPlaceholder](https://jsonplaceholder.typicode.com) - used as the test REST API

**RestTemplate** from Spring, is used for calling the REST endpoints.

**Kotlin Coroutine** feature is used for making non-blocking calls to REST endpoints *(It is an experimental feature,
 so not included in the spring boot dependencies by default. To be added manually in the pom.xml)*

## Endpoints ##

* GET - /posts/all (blocking calls)
* GET - /posts/async/all (non-blocking calls)

## To build and run the application ##
```text
mvn clean install -Dmaven.test.skip=true
```

## Tools used ##
* IntelliJ IDEA
* Git
* Postman