## Task 2

Next we will continue learning Spring Framework and Java. Today we will have a new task is to connect PostgreSQL database to the project.
In the project is available file docker-compose.yml with the described PostgreSQL database service.

In Spring Framework to work with the database created sub-module data-jpa, which implements Java Persistence API.

When working with the database it is necessary to take care of the database schema management. We will use the liquibase tool.
In summary, for convenient work with the database we need to connect the following packages:
1. Spring Data JPA
2. Liquibase
3. PostgreSQL driver

Additional information:
https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa
https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html
https://www.baeldung.com/liquibase-refactor-schema-of-java-app
https://docs.liquibase.com/start/home.html


Add the dependencies to our project:

```groovy
  implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtimeOnly 'org.postgresql:postgresql:42.7.1'
  implementation 'org.liquibase:liquibase-core:4.25.1'
```

    Note that for `spring-boot-starter` packages it is not necessary to specify the package version, as the appropriate version of the gradle library will be selected by the `io.spring.dependency-management` plugin.
    ```
    plugins {
      id 'java'
      id 'org.springframework.boot' version '3.2.2'
      id 'io.spring.dependency-management' version '1.1.4' // <-- here
    }
    ```


Now it is necessary to describe the liquibase configuration and specify the database connection configuration.

`LiquibaseConfiguration`

```java
@Configuration
public class LiquibaseConfiguration {
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
```

Data Scheme:

Migration index file `classpath:liquibase/master.xml`:
```xml
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.4.xsd">
    <include file="classpath:liquibase/changelog/00000000000000_initial_schema.xml" />
</databaseChangeLog>
```

And the first migration:
```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.4.xsd http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
    <changeSet id="20240126-1" author="alex.glebov">
....
    </changeSet>
</databaseChangeLog>

```
DB connection configs at `application.yml`:

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/event
    username: event_user
    password: pa55w0rd
```

And we are ready to start our application.

### What to do?
Practical exercise:
Describe a CRUD to handle the `Event` entity, i.e. read, create, update, delete operations.
Update the service code, and controller methods for all the above operations.

---

#### Misc

Don't forget to create custom docker network to avoid any inconvenient circumstances with 
other docker services and keep current environment as isolated from the other projects

I can suggest to create 
```
docker network create -d bridge --subnet 172.21.0.0/24 --gateway 172.21.0.1 dockernet
```**