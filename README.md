# README #

This README would normally document whatever steps are necessary to get your 
application up and running.

Diagrams are located in the project folder:
* mvc-diagram.pdf

Report and presentation is also there:
* Report.pdf
* Presentation.pptx

## Dependencies ##

* JDK 1.8+
* JavaFX 8+
* jUnit (will be downloaded by Gradle)

## Develop ##

### Build ###

```bash
cd /path/to/project
./gradlew build
```

This command also runs the tests.

### Test ###

```bash
cd /path/to/project
./gradlew test
```

Test results stored at ```./build/reports/tests/test```

### Run ###

```bash
cd /path/to/project
./gradlew run
```

Ignore warnings on console about mismatching FXML versions.

### Potential Issues ###

If a permission denied error occurs when trying to execute the gradle wrapper, 
the following command is required.

```bash
chmod +x gradlew
```

Special note for uni computers:

If gradle doesn't automatically pick up Java 8 (it did when tested), set the 
following JAVA_HOME variable.

```bash
PATH=$JAVA_HOME/jre/bin:$PATH
export PATH
```

## Game ##

### Menu Controls ###

__Left/Right Arrow Keys__ - slide slider or change radio button.
__Space/Enter__ - Check Boxes, select buttons.
__Tab__ - Move to next control.
__N Key__ - Continue/Next.
__B Key__ - Back.

### Game Controls ###

__Q__ - Player 1 Left.
__W__ - Player 1 Right.

__C__ - Player 2 Left.
__V__ - Player 2 Right.

__U__ - Player 3 Left.
__I__ - Player 3 Right.

__Left Arrow__ - Move paddle left.
__Right Arrow__ - Move paddle right.

__ESC__ - Quit (After match start).
__P__ - Pause

### How to Play ###

Use the shield to block the fireball, move it with your controls. If you 
successfully block the ball, you will become the active scoring player, and 
each hit on a scoring object such as a wall or warlord will earn you points, 
until another player deflects the ball. Be wary, the ball's impact will cause 
your shield to become temporarily stunned for 0.5 seconds, so aim wisely.

In domination mode, a time limit is established, within which you must 
eliminate all other players or be the highest scoring player to win. If you 
have the same score, the game will be a draw.

In deathmatch mode, the time limit is removed and you must destroy all other 
warlords to win.

Ghosting can be activated to give dead warlords a chance to seek revenge on 
their killers. Ghost warlords will continue to play as normal, without being 
able to win. They can still earn points however, and mess up other player's 
chances of winning.

Powerups can be activated to spawn every 20 seconds. Powerups include:

* [Red] Haste (+speed)
* [Blue] Double Damage (object penetration)
* [Yellow] Bounty (+score)
* [Purple] Invisiblity

These powerups last for a limited amount of time, or upon hitting a non-powerup 
object, whichever is shortest. The effects are stackable.

### Story ###

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