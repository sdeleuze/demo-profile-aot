## Purpose

The goal of this repository is to showcase how Spring profiles can be used with AOT.
It provides both a Gradle and a Maven-based build.

## Building to a native image

There are several ways to build a native image, check [the reference guide](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html#native-image.developing-your-first-application) for more details.

To build with GraalVM and Gradle:
```
./gradlew nativeCompile
```

or Maven:
```
./mvnw -Pnative native:compile
```

## Running the application

With Gradle, you can run the native application as follows:
```
build/native/nativeCompile/demo-profile-aot
```

With Maven:
```
target/demo-profile-aot
```

Running this sample app displays the following:
```
Default message from application.properties
```

## Enabling a specific Spring profile

Spring profiles are supported by native images, but you need to enable them at build time when AOT runs.
AOT pre-processes your application and evaluates auto-configurations, changing the profile at runtime is therefore ignored.

You can use any Gradle feature to customize the `processAot` task.
For instance, the following example uses a `aotProfile` property with a fallback on the `default` profile if it is not set.

```kotlin
tasks.withType<ProcessAot> {
    args("--spring.profiles.active=" + (project.properties["aotProfiles"] ?: "default"))
}
```

You can then specify the profile(s) your native application needs as part of the regular build command:

```
./gradlew nativeCompile -PaotProfiles=prod
```

Maven users can augment the existing `native` profile of the Spring Boot parent by exposing a similar property, as shown in the following example:

```xml
<profile>
    <id>native</id>
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <executions>
                        <execution>
                            <id>process-aot</id>
                            <configuration>
                                <profiles>${aot.profiles}</profiles>
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</profile>
```

With a default value set in the `properties` section of the build:

```xml
<properties>
    <aot.profiles>default</aot.profiles>
</properties>
```

Once this is in place, the profile(s) the application needs can also be specified on the command-line:

```
mvn -Pnative native:compile -Daot.profiles=prod
```

If you compile this sample application this way, you get
```
Hello from prod
Default message from application.properties
```

## What can you change at runtime with the same executable?
You can run the same application in debug mode by using the following command:
```
build/native/nativeCompile/demo-profile-aot --debug
```

You can customize the log level by using the following command:
```
build/native/nativeCompile/demo-profile-aot -Dlogging.level.org.springframework.beans.factory.support.DefaultListableBeanFactory=debug
```

You can enable the `local` profile since it does not involve changing the beans of the application context:
```
build/native/nativeCompile/demo-profile-aot -Dspring.profiles.active=local
```
Which displays:
```
Customized message from application-local.properties
```

You can also customize a configuration property value specifying directly the property value:
```
build/native/nativeCompile/demo-profile-aot -Dsample.message="Customized message from the command line"
```
Which displays:
```
Customized message from the command line
```
