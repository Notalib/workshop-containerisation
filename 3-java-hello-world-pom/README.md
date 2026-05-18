# Java Hello-world with pom dependencies

## TASK 1: Containerize this app

Make a Dockerfile for this app using maven to build a "fat" jar.

### First use a single stage, then split it into a build and runtime stage.

## TASK 2: Modify the app

Modify the code in the app and rebuild the image. Observe which layers are cached.

## TASK 3: Change my message

Change the messages shown at runtime, by changing the
ENV variables and by volume-mounting the included message.txt

## BONUS TASK: Try changing the app to log in JSON format

Look in resources
