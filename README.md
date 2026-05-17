# Java Sudoku Game

A Java-based Sudoku desktop game built using Java Swing and object-oriented programming. The project includes difficulty selection, board loading, game validation, save/load functionality, and a structured Model-View-Controller style architecture.

## Project Overview

This project was developed to practice Java software design, GUI development, file handling, and algorithmic problem solving through a complete Sudoku game application.

The application allows users to choose a difficulty level, load Sudoku boards, interact with the puzzle through a graphical interface, validate entries, and manage game state.

## Features

- Java Swing desktop interface
- Difficulty selection
- Easy, medium, and hard Sudoku boards
- CSV-based Sudoku board loading
- Sudoku board validation
- Duplicate checking for rows, columns, and boxes
- Game state handling
- Save/load support
- Structured Model, View, and Controller packages
- Object-oriented design using multiple classes and responsibilities

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- File handling
- CSV files
- NetBeans
- Ant build system
- Git and GitHub

## Project Structure

```text
java-sudoku-game/
│
├── src/
│   ├── Controller/        Application control flow and user interaction logic
│   ├── Model/             Sudoku game logic, board loading, validation, and checking
│   └── View/              Java Swing graphical interface
│
├── easy/                  Easy Sudoku board files
├── medium/                Medium Sudoku board files
├── hard/                  Hard Sudoku board files
│
├── nbproject/             NetBeans project configuration
├── build.xml              Ant build configuration
├── manifest.mf            Manifest file
├── valid.csv              Sample valid board file
├── .gitignore
└── README.md
```

## Main Components

### Model

The `Model` package contains the core Sudoku logic, including:

- Board reading
- Game state management
- Row validation
- Column validation
- Box validation
- Duplicate detection
- Difficulty handling
- Board loading

### View

The `View` package contains the Java Swing screens used by the player, including:

- Start screen
- Difficulty selection
- Game frame
- File/path selection interface
- User action handling

### Controller

The `Controller` package connects the interface with the game logic and manages the flow of the application.

## How to Run the Project

### Recommended Method: NetBeans

1. Install Java JDK.
2. Install Apache NetBeans.
3. Clone the repository:

```bash
git clone https://github.com/Youssufathalla/java-sudoku-game.git
```

4. Open NetBeans.
5. Select:

```text
File > Open Project
```

6. Choose the `java-sudoku-game` folder.
7. Right-click the project and select:

```text
Clean and Build
```

8. Click:

```text
Run
```

The application should start from the main game interface.

## Command Line Method

From the project directory, run:

```bash
ant clean jar
```

Then run:

```bash
java -jar dist/java-sudoku-game.jar
```

## What I Learned

- Building a complete Java desktop application
- Applying object-oriented programming to a game project
- Separating responsibilities using Model, View, and Controller-style structure
- Working with Java Swing components
- Reading and processing CSV files
- Implementing Sudoku validation logic
- Managing game state
- Cleaning and publishing a Java project professionally on GitHub

## Future Improvements

- Improve the graphical interface design
- Add a timer
- Add scoring system
- Add hint functionality
- Add automatic Sudoku puzzle generation
- Add unit tests
- Improve exception handling
- Add more puzzle files
- Package the application as an installer

## Author

Youssuf Hatem Fathalla