# Go

In order to first login you need to use the default username "admin" with default password "password".

In order to compile the files on Windows without an IDE, use this command in the directory where the .java files are:

javac --module-path %PATH_TO_FX% --add-modules=javafx.controls *.java

And make sure you have downloaded JavaFX and set an environment variable PATH_TO_FX which points to the \lib directory of that. Then in order to run the program, use:

java --module-path %PATH_TO_FX% --add-modules=javafx.controls Main

The Javadoc is incomplete.
