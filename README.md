# project2

A small robot has been designed to be able to navigate its way through mazes to find a target at some given location. This task resembles those used in
the classic learning experiments of the 1960s which included laboratory mice (and cheese, mild electric shocks, mice of the opposite sex, etc). The objective of the robot (or mouse as it was then) is to find the given target as rapidly and efficiently as possible, learning the maze over several runs and so on.
![](https://tva1.sinaimg.cn/large/e6c9d24egy1h0bcw5n6l4j20n40giabf.jpg)
# GETTING STARTED

### clone this repo to your local

`git clone https://github.com/YW-2022/PROJECT2.git` 

### cd to this repo

`cd Robot-Maze` 

### how to run

Type into you terminal window the command

`javac -classpath maze-environment.jar DumboController.java`

This compiles the controller program DumboController.java into a corresponding class file (DumboController.class). The compilation is performed in the context of the robotmaze environment (through the -classpath maze-environment.jar extension) which is just what we want.

You can now run the robot-maze environment by typing

`java -jar maze-environment.jar &`

The **&** symbol runs the java command in the background, such that it frees the window so that you can still use it for other business. Admire the baroque elegance of the highly
sophisticated computer graphics in the robot-maze environment program. To change the maze, click on the Generators button and then on the **PrimGenerator** in the window
above. You will see that this fills the Current Generator information panel. If you now click on the New Maze button at the bottom right you will get a **new maze** (generated through an application of Prim’s algorithm).

