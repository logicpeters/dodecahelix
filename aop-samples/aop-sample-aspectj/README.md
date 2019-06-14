aop-sample-aspectj
==================

Sample AspectJ-based webapp for JBoss AS7.1.1

This is a sample web application to be deployed to JBoss AS7 for the purpose of evaluating different AOP libraries.  This is one of four AOP libraries/frameworks being evaluated:
- AspectJ
- SpringAOP
- Google Guice
- CDI

The web application for all four is a composed of a simple REST service that responds with a greeting.  It uses JBoss's built in jax-rs implementation - RestEasy, so additional configuration will be necessary if this is to be deployed on another application server, or servlet engine.

The AOP aspect is a simple method-interceptor that logs when the method is called and when it returns.

For this sample, you will need to set up a javaagent to run with your jvm.  The purpose of the javaagent is to perform load-time weaving (LTW) which applies the aspects to your class, when the class is loaded into the jvm.  Steps to set up LTW:
- download and install the latest stable release of AspectJ:
   http://www.eclipse.org/aspectj/downloads.php
- find the aspectjweaver.jar in the lib folder of AspectJ (that was just installed)
- Add a -javaagent argument to JBoss's standalone.conf:

JAVA_OPTS="$JAVA_OPTS -Dlog4j.debug -javaagent:/path/to/aspectj/home/lib/aspectjweaver.jar"





