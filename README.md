# Microservices based Quiz Management System

 ## Description
 Quiz Management System is my very first microservice project which is chiefly designed by multiple enriching services, known as **account service** for user authentication and registeration using JWT.
 **user service** for user identification and profiles, **question service** for question configuration, interactive logics between question and quiz services. 
 Moroever, Quiz service is configured with OpenFeign, enhancing inter-service communications between other services and acting like replacement for RestTemplate or Webclient.
 **Score service** is also created to calculate scores of each user based on userId and scores of user based on both quiz and user ids. **Service registery** class uses eureka server to enable seamless discovery among eureka clients(microservices) 
 
## Key Features
- JWT based authentication and role-based access control.
- Feign Client for clean inter-service communication.
- Modular architecture: each service can be developed, maintained, and scaled independently.
- Fault isolation: when one service fails, only the responsible team needs to handle the error.

## Testing

This project includes **unit and integration tests** for different services to ensure reliability and correctness of business logic.
Tests are written using **JUnit 5 and Mockito** to mock dependencies such as repositories and Feign clients.

### Running Tests
From the root of each microservice, you can run:
``` mvn clean test```
or for all services at once (if you have a parent pom.xml):
```mvn clean install```

### Test Cover by Services
#### Account Service
- Integration tests for user registration and login endpoints.
#### Quiz Service
- Unit tests for quiz creation, fetching questions by quiz ID.
- Integration tests with mocked AccountService, User Service, Score Service validations (via Feign client).
#### Question Service
- Both Unit and Integration Tests, mainly fabricated to test methods which are essential for intergration between quiz and question services.
  
## Technologies Used
## Tech Stack

| Layer           | Technology                      |
|------------------|----------------------------------|
| Backend          | Java 21, Spring Boot,Spring REST API, JPA Hibernate|
| Database          | PostgreSQL, H2                 |
| Authentication  | JWT                             |
|Inter-Service Communication | OpenFeign             |
|Service Discovery       | Eureka Server            |
|Testing                 |  JUnit, Mockito          |
| Build Tool       | Maven                          |

## Usage (Postman / API Docs)
> [!NOTE]
> Example workflow:
> 1. Register or log in (Account Service) → get JWT token
> 2. Use token to access Quiz Service endpoints
> 3. Submit quiz answers → Score Service calculates score
> 

### Contributing

## Fork the Repository:
**We welcome contributions to enhance the project! Follow these steps to contribute:
Click the "Fork" button at the top right of the repository page.**

## Create a New Branch:
```git checkout -b feature/your-feature```

## Make Your Changes:
Implement your feature or bug fix.

## Commit Your Changes:
```git commit -m "Add feature: your feature description"```

## Push To The Branch
```git push origin feature/your-feature```

## Create a Pull Request:
Open a pull request on GitHub and describe your changes.

### Acknowledgements
🙏
Special thanks to the open-source community for the tools and libraries used in this project
