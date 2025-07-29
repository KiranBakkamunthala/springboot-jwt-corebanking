                --Core Banking REST API - Project Documentation--
Technologies Used:
•	Java 17
•	Spring Boot 3.5
•	Spring Security with JWT
•	Spring Data JPA (Hibernate)
•	H2 In-Memory Database
•	Lombok
•	Swagger UI (Springdoc 2.5.0)
•	RESTful Web Services
________________________________________
How to Run the Project
----------------------------------------
1.	Clone the Repository:
 	git clone https://github.com/KiranBakkamunthala/springboot-jwt-corebanking.git
2.	Build the Project:
 	mvn clean install
3.	Run the Application:
 	mvn spring-boot:run
4.	Access Swagger UI: http://localhost:8081/swagger-ui/index.html
5.	Access H2 Console: http://localhost:8081/h2-console

o	JDBC URL: jdbc:h2:mem:bankdb
o	Username: sa, Password: *****
________________________________________
REST Endpoints (with JWT Token Protection)
-----------------------------------------
Authentication APIs
Method	URL	Description	Auth Required

POST	/auth/register	Register new user	No
POST	/auth/login	Authenticate & get token	No

Login Response:
{
  "token": "<JWT Token>"
}

Use the token as:
Authorization: Bearer <JWT Token>
________________________________________
Customer APIs
---------------------------------------
Method	URL	Description	JWT Required

POST	/customers/create	Create new customer	Yes
GET	/customers/{id}	Get customer by ID	Yes
________________________________________
Account APIs
----------------------------------------
Method	URL	Description	JWT Required
POST	/accounts/create?customerId=1&accountType=...	Create account	Yes
POST	/accounts/{id}/deposit?amount=...	Deposit to account	Yes
POST	/accounts/{id}/withdraw?amount=...	Withdraw from account	Yes
PUT	/accounts/{id}/close	Close account	Yes
GET	/accounts/{id}	Get account details	Yes
________________________________________
JWT Token Usage
----------------------------------------
All protected endpoints require the token in the request header:
Authorization: Bearer <your-token>
________________________________________
Swagger Testing Guide
----------------------------------------
1.	Visit: http://localhost:8081/swagger-ui/index.html
2.	Click Authorize (Top right)
3.	Enter:
 	Bearer <your-token>
4.	Now you can execute secured endpoints.

•	Spring Boot 3.5 REST API with layered architecture
•	JWT-based authentication and role-based access control
•	Exception handling with @RestControllerAdvice
•	Clean and reusable service-repository pattern
•	In-memory H2 database for fast demo/testing
•	Swagger UI for easy API documentation and testing
________________________________________
Future Enhancements
----------------------------------------
•	Add role-based authorization (ADMIN / USER)
•	Switch to MySQL or PostgreSQL
•	Add pagination, search, and sorting
•	Integration tests
•	CI/CD pipeline with Docker & Jenkins/GitHub Actions
_______________END------------------------------------
