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
- allow you to delete combo sources
- convenience menu option to add COMBO + SOURCE (various qualifiers) to an existing SOURCE
- input a seed and randomize all current module/structure values from that seed
  reset the pools in the ParamsMap from the seed
  go through each current usage, get the parameter and randomize from the parameter (if<1 double 0.1 - 1.0; if <5 double, 0.1-5.0, etc..)
- randomize values for the current module
- export file should bring up a dialog
- validate a module (via button)
- validate all modules in a tree (bottom button)
- video mode: tweak random param and update preview pane; pause to stop (button on bottom)
- themes
- configurable titles for tabs
- swing alternative look and feel
- have doubles resolve between 0.1 and 3
- have octaves resolve between 1 and 4




