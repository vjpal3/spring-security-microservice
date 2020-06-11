### Auth Microservice
  - A Spring Boot microservice with REST API that leverages Spring Security using JWT web tokens
  - REST API endpoints for user registration and login
  - User data persisted using PostgreSQL
  - Provides exception handling for invalid username/password and duplicate username
  - Global Exception Handling using @ControllerAdvice
  - Authenticates the user by validating the token sent by Zuul API Gateway before forwarding the request to other microservices
  - [Other microservices in this architecture](https://github.com/vjpal3/Bigdata-Microservices-Spring-Cloud-Repos)

  ### Built with:
  - Java
  - Spring Boot
  - Spring Security with JSON Web Token
  - Spring Data JPA-Hibernate
  - PostgreSQL
  - Maven
  - REST API