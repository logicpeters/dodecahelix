## Consola ##

Consola is a starter project for developing a modular swing CLI (command line interface) application with a bundled JRE.  This allows the java developer to deliver CLI apps to users who do not have or want a Java runtime installed.

It is an exercise in usage of the 'jlink' command and the java module system of Java 9+.

The application itself is a simple terminal/shell emulator with a customizable display (font sizes, colors, background).


### Usage

This project uses the gradle wrapper for build, run and packaging.  

To build:

    gradlew build
    
To run:

    gradlew run

Creating and executing a custom runtime image:

    gradlew jlink
    cd build/image/bin
    ./consola
    
    
### Command and Response

The fundamental interactivity of a CLI application is the command/respone mechanism.

#### Subject Combobox

The subject combobox is a feature that allows you to more easily switch contexts and run commands against those different contexts.  A context/subject can be something like a directory of a filesystem, or a category of resource.  Think of it as a convenience for passing a parameter value to the command, where the parameter is required for most if not all commands.  The subject parameter value is always a string.  Note that the CommandHandler interface accepts a subject string and command string as inputs.

A list of subjects is returned for every Response (Collection<String> getSubjects).  If this collection is empty, the a single option will be available and set to the default value from prefs.Constants.DEFAULT_SUBJECT.

In many applications there may be no need for such a context, and this option can be disabled by setting the prefs.Constants.SUBJECT_OPTIONS_ENABLED value to false.





