# Basic Java Hello-world

## TASK 1: Containerize this app

Make a Dockerfile for this, use basic `javac` and `java` commands.

### First use a single stage, then split it into a build and runtime stage.

## TASK 2: Modify the app

Modify the code in the app and rebuild the image. Observe which layers are cached.

## TASK 3: Change my message

Change the messages shown at runtime, by changing the
env variables and by volume-mounting the included message.txt
