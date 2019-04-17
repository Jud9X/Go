/**
 * Player class that is a child of the User class, inheriting its constructor and methods.
 * @author Oliver
 * @version 1.0
 * */
public class Player extends User {
    public Player(String username, String password, String fname, String lname) {
        super(username, password, fname, lname);
    }
    
    public String toString() { //necessary?
        return super.toString();
    }
}