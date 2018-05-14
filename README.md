# Sample To Do List web application using Spring Boot and MySQL

A simple Todo list application using Spring Boot with the following options:

- Spring JPA and MySQL for data persistence
- Thymeleaf templae for the rendering.

To build and run the sample from a fresh clone of this repo:

## Configure MySQL

1. Create two databases in your MySQL instance : `tododb` and `tododbtest` 
2. Update the application.properties file in the `src/main/resources` folder with the URL, username and password for your MySQL instance. The table schema for the Todo objects will be created for you in the database.


## Build and run the sample

1. `mvnw package`
3. `java -jar target/TodoDemo-0.0.1-SNAPSHOT.jar`
3. Open a web browser to http://localhost:8080

## Tests

## Run tests
Alias scripts are available to ease executing tests:

**All tests**
```
./test.sh
```

**Unit tests only**
```
./test.sh -u
```

**Integration tests only**
```
./test.sh -i
```

## Integration tests
Integration tests runs on a dedicated test database (`tododbtest`) whose table `todo_item` is erased before each test.

Integration test classes are identifiable by the use of `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` and test methods are tagged with `@Category(SlowTests.class)`

## Unit tests
Unit tests classes are identifiable by **absence** of `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` and their test methods are tagged with `@Category(FastTests.class)`

## Git pre-commit hook
Set the git pre-commit hook as following:
```
cp pre-commit.sh ./.git/hooks/pre-commit
chmod 755 ./.git/hooks/pre-commit
```