# Go

Notes:

The GUI is almost complete in terms of functionality (but not in terms of design!). Now in order to login you need to use the default username "admin" with default password "password".
In order to compile the files on Windows without an IDE, use this command in the directory where the .java files are:

javac --module-path %PATH_TO_FX% --add-modules=javafx.controls *.java

And make sure you have downloaded JavaFX and set an environment variable PATH_TO_FX which points to the \lib directory of that. Then in order to run the program, use:

java --module-path %PATH_TO_FX% --add-modules=javafx.controls Main


Current big "to-do's":

1. Review meeting minutes.
4. Add Javadoc (following the lecture guidelines) and produce the final output of it
5. Check coding conventions (following the lecture guidelines)
7. Record the demonstration videos
8. Contributions report
