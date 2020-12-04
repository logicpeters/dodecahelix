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
- the randomizer: press play to tweak a param every X seconds; press pause to stop; 
- dont let the "-" (decrement) option go below 0
- presets for most standard structures
- when adding a new combo module, make it the root
- ability to designate a control module, or at least order the module children
- input a seed and randomize all current module/structure values from that seed
  reset the pools in the ParamsMap from the seed
  go through each current usage, get the parameter and randomize from the parameter (if<1 double 0.1 - 1.0; if <5 double, 0.1-5.0, etc..)
- randomize values for the current displayed module
- export file should bring up a dialog - "Save As.."
- validate a module (via button)
- validate all modules in a tree (bottom button)
- video mode: tweak random param and update preview pane; pause to stop (button on bottom)
- export to video
- themes
- configurable titles / module ID's for tabs
  better defaults (i.e; perlin-##, etc..)
- swing alternative look and feel
- have doubles resolve between 0.1 and 3.0 by default
- have octaves resolve between 1 and 4




