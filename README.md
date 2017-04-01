# README #

This README would normally document whatever steps are necessary to get your application up and running.

## Dependencies ##

* JDK 1.8+
* JavaFX 8+
* jUnit (will be downloaded by Gradle)

## Build ##

```bash
cd /path/to/project
./gradlew build
```

This command also runs the tests.

## Test ##

```bash
cd /path/to/project
./gradlew test
```

Test results stored at ```./build/reports/tests/test```

## Run ##

```bash
cd /path/to/project
./gradlew run
```

Ignore warnings on console about mismatching FXML versions.

## Game Controls ##

__Left Arrow__ - Move paddle left.
__Right Arrow__ - Move paddle right.

## Contribution guidelines ##

* Writing tests
* Code review
* Other guidelines

### Naming Conventions and Architecture ###

__Module__

A.K.A. Super Controller.

Usually used for set-up purposes, such as in creating components. These also 
typically provide services to child controllers.

Typical file naming is ```SomethingModule```.

__Component__

A.K.A. Controller.

Handles the communication between FXML views, services and other controllers, 
along with providing event handlers or observers. Scenes are created at this 
level.

Typical file naming is ```SomethingComponent```.

__Service__

A.K.A. Model or Utility

Services are classes made to do one thing, and do one thing very well. Modules 
will usually create service objects and pass them down to child controllers 
where needed. These usually provide either data models or utility functions.

Typical file naming is ```SomethingService```.

## Contacts ##

Jerry Fan - jfan082@aucklanduni.ac.nz

Chris Ding - hdin898@aucklanduni.ac.nz