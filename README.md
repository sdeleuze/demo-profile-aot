## Purpose

The goal of this repository is to show what can be customized and what can not when using Spring profiles in Ahead-Of-Time transformations and native Spring Boot applications.

## How to compile your native application:
```
$ ./gradlew nativeCompile
```

## How to run

You can run the native application as follows:
```
build/native/nativeCompile/demo-profile-aot
```

Which displays:
```
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

## What can not be changed at runtime with the same executable?
You can not enable the `prod` profile since it involves changing the beans of the application context, and the registered beans have been pre-computed Ahead-Of-Time in the generated source file `build/generated/aotSources/com/example/DemoProfileAotApplication__BeanFactoryRegistrations.java`.

## What is Spring team plan to make that more flexible?

See https://github.com/spring-projects/spring-framework/issues/29844.