#Java Webapp Template
This project is intended to give a template for Webshops ready to start custom development.

The template consists of an Abstract Controller/Service/DAO to easily add new resources, some common spring configuration for webapps, a login and logout mechanism configured with Spring Security.

Used Components:
- Spring Web MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Thymeleaf
#Setup for development
Sometimes, for any reason, it's needed to add the [mysql-connector](/libs/mysql-connector-java-8.0.21.jar) manually to the classpath.

This dependency is explicit listed in the maven pom and should be added automatically to the classpath. But if this is not the case, just add the dependency manually.

To execute the Integrationtests, add a run configuration for JUnit and select the IntegrationTestSuite class as main class.

The main class for starting the application is CommonApplication.

Access the application through http://localhost:8081
