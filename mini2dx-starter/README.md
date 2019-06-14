## girgrat - an experimental mini2dx / libgdx app

A starter project to be used for mini2dx apps.

Based on mini2dx 1.4.4 and libgdx 1.9.4

## Features

- dependency injection using mini2dx's library
- entity-component system using artemis library
- using mini2dx's ScreenBasedGame
- sprite assets pre-loaded in a loading screen

## Project Layout

The project layout is the same as any LibGDX project.

```
settings.gradle            <- modules definitions
build.gradle               <- main Gradle build file, defines dependencies and plugins
gradlew                    <- script that will run Gradle on Unix systems
gradlew.bat                <- script that will run Gradle on Windows
gradle                     <- local gradle wrapper
local.properties           <- Intellij only file, defines android sdk location

core/
    build.gradle           <- Gradle build file for core project
    src/main/java          <- Source folder for all your game's code
    assets/                <- contains for your graphics, audio, etc.  Shared with other projects.

desktop/
    build.gradle           <- Gradle build file for desktop project
    src/                   <- Source folder for your desktop project, contains Desktop launcher class

android/
    build.gradle           <- Gradle build file for android project
    AndroidManifest.xml    <- Android specific config
    res/                   <- contains icons for your app and other resources
    src/                   <- Source folder for your Android project, contains Android launcher class

ios/
    build.gradle           <- Gradle build file for the ios project
    src/                   <- Source folder for your iOS project, contains iOS launcher class
```