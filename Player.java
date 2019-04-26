/**
 * A child of the User class, inheriting its constructor and methods, this class contains Player user information.
 * @author Oliver
 * @version 1.0
 * */
public class Player extends User {
    public Player(String username, String password, String fname, String lname) {
        super(username, password, fname, lname);
    }
    
    /**
     * Turn the Administrator object into a String for printing.
     * @return A String containing the object information, which cannot be null.
     * */
    public String toString() {
        return super.toString();
    }
}