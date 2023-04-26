## Purpose

The goal of this repository is to show what can be customized and what can not when using Spring profiles in Ahead-Of-Time transformations and native Spring Boot applications.

## How to compile your native application
With Gradle:
```
./gradlew nativeCompile
```
or with Maven:
```
./mvnw -Pnative native:compile
```

## How to run your native application

With Gradle, you can run the native application as follows:
```
build/native/nativeCompile/demo-profile-aot
```
With Maven:
```
target/demo-profile-aot
```

Which displays:
```
Default message from application.properties
```

## How can I use an environment specific Spring profile like the `prod` one?

You should enable those profiles at build time since it involves changing the beans of the application context.

With Gradle, it is possible to do that with for example:
```
./gradlew processAot --args='--spring.profiles.active=prod' nativeCompile
```

With Maven, you can for example define a Maven profile that will enable the Spring Boot one with:
```xml
<profiles>
		<profile>
			<id>prod</id>
			<build>
				<pluginManagement>
					<plugins>
						<plugin>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-maven-plugin</artifactId>
							<configuration>
								<profiles>
									<profile>prod</profile>
								</profiles>
							</configuration>
						</plugin>
					</plugins>
				</pluginManagement>
			</build>
		</profile>
	</profiles>
```
And when building the native executable or the container image, specify the profile with for example:
```
mvn -Pprod,native native:compile
```

When you compile your native executable like that, you get
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
