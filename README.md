# Simple Maze Solver

This Java application demonstrates a simple maze-solving algorithm using depth-first search (DFS). The program generates a random maze and attempts to find a path from the start node to the target node using DFS. The maze is displayed in a graphical user interface (GUI) created with Java Swing.

## Getting Started

To run the program, follow these steps:

1. Clone the repository to your local machine.
2. Compile and run the `View` class.

```bash
javac View.java
java View
```

1. **Enter the number of rows and columns when prompted.**
2. **The generated maze will be displayed, and the program will attempt to find a path using DFS.**

## Features

- **Maze Generation:** The program generates a random maze with obstacles, a start node, and a target node.
- **DFS Pathfinding:** It uses depth-first search to find a path from the start to the target node.
- **Visualization:** The GUI displays the maze, the explored path, and the current position of the algorithm.

## GUI Controls

- **Generate Maze Button:** Click to generate a new random maze and restart the DFS search.
- **Arrow Keys:** Navigate through the DFS path manually (forwards and backward).

## Code Overview

- **View.java:** The main GUI class responsible for creating the window, handling user input, and managing the maze visualization.
- **DepthFirst.java:** Contains the DFS algorithm used for pathfinding.

## Demo [Link](https://www.kapwing.com/videos/65a4e4a7793db9ac7d949d88)

## Dependencies

The program uses Java Swing for the GUI.

## Notes

- The DFS pathfinding is visualized using a timer to show the algorithm's progress.
- If no DFS path is found, a message is displayed in the GUI.

## Contributing

If you'd like to contribute to the project, feel free to fork the repository and submit a pull request.

