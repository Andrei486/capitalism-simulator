# g2-monopoly
A simple game of Monopoly created using Java and Swing for SYSC3110.

## Authors

Andrei Popescu 101143798 (Andrei486 on GitHub)

Mohammad Alkhaledi 101162465 (malkh1 on GitHub)

Waleed Majbour 101144882 (wmajbour on GitHub)

Sebastian Lionais 101157892 (Lubbree and Sebastian on GitHub due to config issues)

## How to run

Download the g2-monopoly.jar file, and run `java -jar g2-monopoly.jar` from the command line in the directory where the file is located.
The text-based interface will open in the terminal, allowing commands to be entered.

## Milestone 1

### Deliverables

The Milestone 1 submission is a zip file which includes the following deliverables.
For all deliverables except the source code, the authors are included in the deliverable. For the source code, see GitHub.

- Readme.md (this file): a short description of all deliverables and authors
- g2-monopoly.jar (executable + source): a .jar file runnable on command line, containing the source and executable
- UML class diagram .png file: a diagram showing the structure of classes in this project, as well as their relationships
- Three UML sequence diagram .png files: diagrams showing the flow of events in three important operations
- Design discussion .pdf file: a file containing a discussion of important design decisions in the project
- User manual .pdf file: a file explaining how to play this version of Monopoly, and what text commands are valid.

### Known Issues

No issues are currently known.

### Next Milestone Roadmap

For Milestone 2 (ending November 5), the goals are:

- Implement a GUI view, which will show the board and players' positions. This will replace the existing text-based GUI, and will allow users to perform actions via buttons.
- Implement a controller class which will interface between the GUI view and the existing model, as per MVC.
- Implement unit test classes for all the Model classes (ie, every existing class except TextController, plus any future ones)

## Milestone 2

### Deliverables

The Milestone 2 submission is a zip file which includes the following deliverables.
For all deliverables except the source code, the authors are included in the deliverable. For the source code, see GitHub.

- Readme.md (this file): a short description of all deliverables and authors
- g2-monopoly.jar (executable + source): a .jar file runnable on command line, containing the source and executable, including unit tests
- UML class diagram .png file: a diagram showing the structure of classes in this project, as well as their relationships
- Two UML sequence diagram .png files: diagrams showing the flow of events when a player goes bankrupt or buys a property
- Design discussion .pdf file: a file containing a discussion of important design decisions in the project
- User manual .pdf file: a file explaining how to play this version of Monopoly, including images

### Known Issues

No issues are currently known.

### Next Milestone Roadmap

For Milestone 3 (ending November 22), the goals are:

- Implement different types of spaces, including railroads and jail
- Implement houses and hotels
- Update the GUI to make it more intuitive (add colors to each player, add player names, add text game logs)
- Create a rudimentary AI player (possibly multiple types?)
- Create unit tests for any new classes added
