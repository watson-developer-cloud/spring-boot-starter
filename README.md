# Watson Spring Boot

This project adds Spring Boot support for the Watson services.

This means that you can now "auto-wire" any of the Watson services into your Spring Boot application
using the standard Spring autoconfiguration framework.

All you need to do is:

1. Add the Watson spring-boot-starter dependency to your Spring Boot app:

    In your maven `pom.xml`
    ```
    <dependency>
      <groupId>com.ibm.watson.developer_cloud</groupId>
      <artifactId>spring-boot-starter</artifactId>
      <version>4.2.1</version>
    </dependency>
    ```

    or in your gradle `build.gradle`, in the dependencies stanza, add
    ```
    compile 'com.ibm.watson.developer_cloud:spring-boot-starter:4.2.1'
    ```

2. Add your Watson service(s) credentials and version info to your application
properties file.  The standard location for application properties is the
`src/main/resources/application.properties` file, but your application might
use a different location. The properties to add are:

  - `watson.<service>.url`: The base URL for the service.  This can be omitted if your
  service uses the default service url
  - `watson.<service>.username` and `watson.<service>.password` OR `watson.<service>.apiKey`:
  The credentials for accessing the service.
  The credentials can be omitted from the application properties file if they are
  supplied through the `VCAP_SERVICES` environment variable.
  - `watson.<service>.versionDate`: Some Watson services use a `versionDate` parameter to
  indicate the service behavior expected by the application.  For services that accept it,
  this is a required parameter and must be specified in the application properties.

    Both Eclipse and IntelliJ (Ultimate Edition) offer autocomplete support for
    Spring Boot application properties and should show these properties, along with
    their required type and Javadoc description, in the autocomplete menu.

3. Autowire the service into your application. Autowiring is the key mechanism of the
Spring framework for injecting dependencies.  It is accomplished by specifying the
`@Autowired` annotation on the declaration for the service instance.
Here's an example using the Watson Conversation service:
  ```java
  @Autowired
  protected Conversation service;
  ```

The Spring framework takes care of the rest.  Your application can now simply start
using the service instance.
