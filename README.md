# Go

Notes:

The GUI is almost complete in terms of functionality (but not in terms of design!). Now in order to login you need to use the default username "admin" with default password "password".
In order to compile the files on Windows without an IDE, use this command in the directory where the .java files are:

javac --module-path %PATH_TO_FX% --add-modules=javafx.controls *.java

And make sure you have downloaded JavaFX and set an environment variable PATH_TO_FX which points to the \lib directory of that. Then in order to run the program, use:

java --module-path %PATH_TO_FX% --add-modules=javafx.controls Main


Current big "to-do's":

0. Post the meeting minutes for review.
1. Complete the required functionality (user dashboard news [new users (line 473 of Main) and new games completed (line 478 of Main)] and profile pictures (line 454 of Main))
2. Improve the GUI (including both design and layout)
3. Add exception handling if necessary
4. Add Javadoc (following the lecture guidelines)
5. Check coding conventions (following the lecture guidelines)
6. Optional: make the GUI look nice and add cool extra features (like highlighting where a piece will go when the mouse hovers over that area, different colour themes, optional confirmation boxes after each move, music...)
7. Record the demonstration videos
8. Optional: reduce the size of Main by putting scenes in their own classes?
9. Optional: unit/integration testing
