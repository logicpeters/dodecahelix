aop-sample-mongo-cdi
====================

Sample AOP advice demonstrating validation of a business object being persisted into a Mongo datastore

This is an expansion of the aop-sample-cdi project, which further demonstrates the usage of AOP with a simple validation example.

The environment for this example requires:
- JBoss AS 7.1 : If another applicated server is to be used, additional configuration would be required for the RestEasy setup in web.xm (this comes bundled with JBoss which handles its wiring).
- Mongo database (version 2.4.3 used for testing)

Settings for the Mongo database are currently hard-coded in the MongoSettings class (uses the default port and localhost).

In this example, a second Rest service, called PartsRestService is set up via RestEasy and wired using CDI injection.  The Parts rest service has endpoints for CRUD operations on a simple business object called a Part.  

One of the wirings is to a DAO interface called PartDAO, with two implementations against Mongo : one which uses manual object mapping, and another that uses Mongojack to do the object mapping.  The purpose of the two implementations is to demonstrate the use of "Qualifiers" in CDI to distinguish between the two beans.

The RestService endpoint for creating a Part (a POST operation) is the pointcut for the @PartValidation advice (look in the org.dmp.mongo.model.part package).  The AOP class that is set up (PartValidationMongoAdvice.java) is also wired to the DAO, to perform a lookup of parts by number.  If a part exists in the database with that number, the advice class will throw a ValidationException.

The point of all this is to show how we can reduce some of the noise inside of the DAO/manager type classes by separating out the validation logic into an aspect instead of the main flow of the method.
