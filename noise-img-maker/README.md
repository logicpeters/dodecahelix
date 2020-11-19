## Noise Image Maker (nim) ##

This project is an application for building images based on noise algorithms.  The base library for noise generation is here:

    https://github.com/SpongePowered/noise

### Usage

This project uses the gradle wrapper for build, run and packaging.  

To build:

    gradle build
    
To run:

    gradle run

Creating and executing a custom runtime image:

    gradlew jlink
    cd build/image/bin
    ./nim
    
    
### TODO

- add a new source module
- designate the root module
- add a modifier to a source
- change the type of a source module
- validate a module (via button)
- validate all modules in a tree
- add another source
- combine two sources
- link between modules


