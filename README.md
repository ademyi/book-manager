## Library Management System


### Features

- Spring Boot and REST via DTO
- CRUD operations
- Lend and return books 


### Running the application locally

`mvn spring-boot:run`

or

    mvn clean package
    java -jar target/book-manager-0.0.1.jar

After then please go to the link below

    http://localhost:8090

Sample Operations :

* lending a book from the library

`curl -X PUT -H "Content-Type: application/json" -d '{"bookId":"1","personId":"10"}' http://localhost:8090/books/lend/1`

* returning a book to the library

`curl -X PUT -H "Content-Type: application/json" -d '{"bookId":"1","personId":"10"}' http://localhost:8090/books/return/1`
