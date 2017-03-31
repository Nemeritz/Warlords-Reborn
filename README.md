# README #

This README would normally document whatever steps are necessary to get your application up and running.

## Dependencies ##

* JDK1.8+

## Build ##

```bash
cd /path/to/project
./gradlew build
```

If you need to specify a JDK version for some reason (like on UoA computers *hint* *hint*), use the following build command instead:

```bash
./gradlew -Dorg.gradle.java.home=/path_to_jdk_directory
```

Uni Computer Example

```bash
./gradlew -Dorg.gradle.java.home=$JAVA_HOME/jre/
```

## Contribution guidelines ##

* Writing tests
* Code review
* Other guidelines

### Naming Conventions and Architecture ###

__Module__

A.K.A. Super Controller.

Handles the creation and controlling of child controllers. Their distinctive 
feature is that they don't have an associated view. These also typically 
provide services to child controllers.

In terms of usage with JavaFX, modules also handles scene transitions.

Typical file naming is ```SomethingModule```.

__Component__

A.K.A. JFX Controller, View and Style trio.

Handles the communication between views and any storage classes, along with 
providing event hooks to modules. These typically come in trios of a Java 
class, FXML file and a CSS file. Scenes are created at this level.

Typical file naming is ```SomethingComponent```.

__Service__

A.K.A. Model or Utility

Services are classes made to do one thing, and do one thing very well. Modules 
will usually create service objects and pass them down to child controllers 
where needed.

Typical file naming is ```SomethingService```.

## Controls ##

left arrow for slider to move left and right arrow for slider to move right

## Contacts ##

Jerry Fan - jfan082@aucklanduni.ac.nz
Chris Ding - hdin898@aucklanduni.ac.nz