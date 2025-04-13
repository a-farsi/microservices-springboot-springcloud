# microservices-springboot-springcloud

Through this project of a library book borrowing system, we deal with microservice architecture design and its implementation using the Spring Boot framework.

In what follows, we use the terms "service" and "microservice" interchangeably to refer to a microservice. When exceptions occur, we will specify according to the context.

Nous développons chaque microservice par le framework Spring Boot.

For simplicity, we consider three functionalities, each one treated as an application microservice: one for customer management, a second for book management, and a third for borrowing management.

In addition, we will need to develop:

- A microservice for the automatic registration of microservice instances created as part of our application. For this purpose, we use Netflix Eureka. Instances are created, for instance, to handle increased workload or to replace a failing one.
- A microservice for centralized configuration management of the microservices.
- A microservice acting as a gateway or proxy.



<p align="center">
<img src="figures/1.architecture_microservices.png" width=100%>
</p>
<p align="center">
Figure 1: Architecture of our application based-on microservices 
</p>

### Microservices Startup Order
We must follow a specific startup order for the microservices to ensure they are properly configured at launch and correctly registered with the discovery service.
Therefore, we start with the discovery-service, followed by the config-service.
The startup order of the following application microservices is not critical: customer-service, book-service, and borrowing-service.
Finally, we launch the gateway-service.


### How to interact with microservices:

-A user connects via the graphical interface or with a REST client to perform CRUD operations on our application.

-The request, which contains the name of the target microservice, first reaches the gateway microservice.

-The gateway then queries the registration management service, passing the name of the target microservice to request its IP address and port.

-The register-service responds to the gateway's request by sending the IP address and port of the requested microservice.

-Finally, the gateway forwards the user request to the microservice using the IP address and the port.

### Database Backup Strategy: 
We have chosen to separate the database so that each microservice has its own. This choice makes data integrity control more complex, which becomes the responsibility of the designer and must be managed at the service layers. We should note that in distributed databases, relationships between data that would traditionally be enforced by foreign keys must be handled at the application level instead.

### implementation of microservice

* The customer-service :
We implement this service using a three-layer architecture: controller, service, and DAO.
The DAO layer is responsible for interacting with the database.
We create a _Customer_ entity and a DAO that extends the _JpaRepository_ interface.
The service layer handles the business logic, including data validation and integrity checks, before saving or deleting data in the database.

<p align="center">
<img src="figures/2.list_of_all_customers.png" width=100%>
</p>
<p align="center">
Figure 2: Display the list of all saved customers 
</p>

* The book-service : 

We implement a _Book_ entity, a controller annotated with _@RestController_, a service layer, and a DAO that extends the _JpaRepository_ interface.

<p align="center">
<img src="figures/2.list_of_all_books.png" width=100%>
</p>
<p align="center">
Figure 2: Display the list of all saved books 
</p>

* The config-service :
It centralizes the configuration management for all microservices by allowing each microservice to load its configuration from a remote Git repository.
It is a Spring Boot application configured to point to this Git repository, where all configuration files are defined and managed.
Here is the url of the remote git repository that we use to gather all configuration files: _https://github.com/a-farsi/spring-config-repo/tree/main_

<p align="center">
<img src="figures/2.config-of-application-yaml-file.png" width=100%>
</p>
<p align="center">
Figure 2: Display the configuration in the application.yaml file  
</p>

<p align="center">
<img src="figures/2.config-of-register-service-yaml-file.png" width=100%>
</p>
<p align="center">
Figure 2: Display the configuration in the register-service.yaml file  
</p>



* The register-service : 
we use Spring Cloud Netflix Eureka Server

<p align="center">
<img src="figures/4.register-service.png" width=100%>
</p>
<p align="center">
Figure 2: Display the interface of the register-service  
</p>


* The proxy-service : 
It is a Spring Cloud Gateway

* The borrowing-service:

- A _borrow_ object can include one or multiple _borrowed books_.
- Each _borrow_ object is associated with only one _customer borrower_.
- And each borrowed book corresponds to a single _book_ item.

In this service, the entities to be persisted are the _borrow_ objects and the _borrowedbook_ objects, which we need to define as JPA entities.

The other objects, _customer_ and _book_, are already persisted in the _service-customer_ and _book_service_ respectively. 

Therefore, we will:
- Define them as DTOs with only getters and setters.
- Store the _bookID_ within the _borrowedbook_ object and the _customerID_ within the _borrow_ object. These references serve as foreign keys, useful for maintaining relationships between the tables of our application."

<p align="center">
<img src="figures/2.class_diagram.png" width=100%>
</p>
<p align="center">
Figure 2: Class diagram of the borrowing-service 
</p>



<p align="center">
<img src="figures/3.display-all-borrow-objects.png" width=100%>
</p>
<p align="center">
Figure 2: Display all borrows 
</p>



Parler de la configuration dans …

bootstrap.yaml, nous spécifions le nom du service et le port sur lequel l’application est joignable. Nous précisons le url du serveur d’enregistrement Eureka et l’URL vers le repot gît dans lequel il y a le complément de configuration à télécharger pour pouvoir lancer le microservice convenablement.
