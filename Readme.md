# Drawing Canvas

* A small project to create a new canvas and draw lines, rectangles and "bucket fill" the shape.

## Prerequisites

* Java Runtime (I used 1.9, but technically 7 will suffice)
* Maven (version 3.5.3)
* IntelliJ (Optional, for the best view of codes)

## How to Run

* In command line, assuming maven is set up, issue the command:
```
mvn clean compile
```
* To run the unit tests, run
```
mvn test
```
* Finally to run the main program, run
```
java -cp target/classes org.mrkagelui.drawingcanvas.Main
```

## Design consideration

* This project consists of mainly two classes:
  * Pixel
  * CanvasManager
  
* Pixel holds the status information of the system, can be viewed as the "Model" class; currently it holds two essential fields:
  * isAtLine: whether a line is drawn at it;
  * pixelChar: this represent the 'color' of this pixel
  
* CanvasManager is the "Control" of the system. It holds a "command" String member, upon setting it, the manager will parse the command and perform sanity checking, then upon calling "update" it will execute the command

* "View" is trivial in this project, CanvasManager has a #show(PrintStream) method which prints the pattern to the stream

* This is a resemblance of observer pattern. (A full such pattern could include a stream of commands and then a manager to listen to that, read the new command and update)

* It is assumed that if any of the input coordinates exceeds the canvas boundary, the whole command will be discarded