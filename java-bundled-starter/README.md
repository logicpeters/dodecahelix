## Consola ##

Consola is a starter project for building a modular swing application with a bundled JRE.  This allows you to deliver swing apps to users who do not have or want a Java runtime installed.  It is an exercise in usage of the 'jlink' command and the java module system of Java 9+.

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


