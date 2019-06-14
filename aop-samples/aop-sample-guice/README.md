aop-sample-guice
================

Sample Google Guice webapp using AOP for JBoss AS7.1.1


This is a sample web application to be deployed to JBoss AS7 for the purpose of evaluating different AOP libraries.  This is one of four AOP libraries/frameworks being evaluated:
- AspectJ
- SpringAOP
- Google Guice
- CDI

The web application for all four is a composed of a simple REST service that responds with a greeting.  It uses JBoss's built in jax-rs implementation - RestEasy, so additional configuration will be necessary if this is to be deployed on another application server, or servlet engine.

The AOP class is a method-interceptor that logs when the method is called (arguments and method name) and when it returns (return value).

