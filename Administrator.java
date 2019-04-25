import java.time.ZonedDateTime;

/**
 * Administrator class that is a child of the User class, inheriting its constructor and methods.
 * It also adds the admin ID and join date as required by the specification.
 * It includes two functions for creating new users: one for players and one for administrators.
 * @author Oliver
 * @version 1.1
 * */
public class Administrator extends User {
    private int adminID;
    
    public Administrator(String username, String password, String fname, String lname, int adminID) {
        super(username, password, fname, lname);
        this.adminID = adminID;
    }
    
    public int getAdminID() {
        return adminID;
    }
    
    public String toString() {
        return super.toString() + " " + "Admin ID number: " + adminID;
    }
    
    public static Player createPlayer(String username, String password, String fname, String lname) {
        return new Player(username, password, fname, lname);
    }
    
    public static Administrator createAdmin(String username, String password, String fname, String lname, int adminID) {
        return new Administrator(username, password, fname, lname, adminID);
    }
}