# Go

Notes:

The GUI is almost complete in terms of functionality (but not in terms of design!). Now in order to login you need to use the default username "admin" with default password "password".
In order to compile the files on Windows without an IDE, use this command in the directory where the .java files are:

javac --module-path %PATH_TO_FX% --add-modules=javafx.controls *.java

And make sure you have downloaded JavaFX and set an environment variable PATH_TO_FX which points to the \lib directory of that. Then in order to run the program, use:

java --module-path %PATH_TO_FX% --add-modules=javafx.controls Main


Current big "to-do's":

1. Complete the required functionality (e.g. Record class, user dashboard info (news, games completed), profile pictures etc...)
2. Improve and finish user dashboard and improve GUI (including design and layout)
3. Add exception handling if necessary (as part of improving GUI)
4. Add saving and loading games (users is done)
5. Reduce the size of main by putting scenes in their own classes??
6. Add Javadoc
7. Optional: make the GUI look nice and add cool extra features (like highlighting where a piece will go when the mouse hovers over that area, different colour themes, optional confirmation boxes after each move, music...)
8. Record demonstration video
9. Optional: unit/integration testing
