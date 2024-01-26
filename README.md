**# Entertainment event project

## Task 1

Let's begin our Java story :)

Let's imagine that you are a computer science student. You have several friends who are
part of rock bands and love playing their music. However, they lack opportunities to monetize
their creativity. They need a system to keep track of the participants at their concerts, plan
their performances, announce future events, and so on.

### What to do?

* Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download)
  and [Java 17 SDK](https://www.jetbrains.com/help/idea/sdk.html)
* Fork from this project into your private one and open access to your
  mentor ([AlexeyGlebovSetronica](https://github.com/AlexeyGlebovSetronica))
    * How to fork - https://docs.github.com/en/get-started/quickstart/fork-a-repo
    * How to open access - https://docs.github.com/en/account-and-profile/setting-up-and-managing-your-personal-account-on-github/managing-access-to-your-personal-repositories/inviting-collaborators-to-a-personal-repository
* Open it in Idea and run
    * `Ctrl-Shift-A` - `Execute Gradle Task` - `gradle bootRun`
    * Check if you can open http://localhost:8080/event/api/v1/events

    * Try `List All Events`
    * Try `Search All Events`, it should fail with 500
* For this task you will need to create a new branch `TASK-1` and to do following things:
    * Read carefully everything you have in `src/main/**` and `*.gradle` and try to understand.
      Everywhere you have any doubts or questions
      on what is going on - leave a text comment right in the code, e.g:
      ```java
      public static void main(String[] args) {
          SpringApplication.run(EventingApplication.class, args);
      }
      ```
    * Implement following search
      API - [EventController::searchEvents](com/setronica/eventing/web/EventController.java)
      ```
      http://localhost:8080/event/api/v1/events/search?q=text
      ```
    * Create MR and send it to your mentor. He will review it and go through your comments.

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
