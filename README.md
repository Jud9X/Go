# Go

Notes:

The game can no longer be played (on command line) as the GUI is in the early stages of development. The code is currently very untidy as I don't really know what I'm doing. In order to compile the files on Windows without an IDE, use this command in the directory where the .java files are:

javac --module-path %PATH_TO_FX% --add-modules=javafx.controls *.java

And make sure you have downloaded JavaFX and set an environment variable PATH_TO_FX which points to the \lib directory of that. Then in order to run the program, use:

java --module-path %PATH_TO_FX% --add-modules=javafx.controls Main


Current big "to-do's":

1. Complete the required functionality (e.g. Record class, dates, profile pictures etc...)
2. Improve JavaFX GUI (and add login page)
3. Add exception handling (as part of improving GUI)
4. Add saving and loading
5. Add Javadoc
6. Make the GUI look nice and add cool extra features (like highlighting where a piece will go when the mouse hovers over that area, different colour themes, optional confirmation boxes after each move, undo (last) move(s), music...)
7. Record demonstration video
8. Optional: unit/integration testing
