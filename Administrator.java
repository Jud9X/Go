import java.time.ZonedDateTime;

/**
 * A child of the User class, inheriting its constructor and methods, this class contains Administrator user information.
 * It adds the admin ID to the User class as required by the specification.
 * It includes two functions for creating new users: one for players and one for administrators.
 * @author Oliver
 * @version 1.2
 * */
public class Administrator extends User {
    private int adminID;
    
    /**
     * Create an Administrator user with the given information.
     * @param username The username, which must be unique and non-empty.
     * @param password The password, which can be non-unique and empty.
     * @param fname The user's first name, which can be non-unique and empty.
     * @param lname The user's last name, which can be non-unique and empty.
     * @param adminID The Administrator's admin ID, which must be an integer.
     * */
    public Administrator(String username, String password, String fname, String lname, int adminID) {
        super(username, password, fname, lname);
        this.adminID = adminID;
    }
    
    /**
     * Get the admin ID.
     * @return The admin ID which is an int.
     * */
    public int getAdminID() {
        return adminID;
    }
    
    /**
     * Turn the Administrator object into a String for printing.
     * @return A String containing the object information, which cannot be null.
     * */
    public String toString() {
        return super.toString() + " " + "Admin ID number: " + adminID;
    }
    
    /**
     * Create a new normal player.
     * @param username The username, which must be unique and non-empty.
     * @param password The password, which can be non-unique and empty.
     * @param fname The player's first name, which can be non-unique and empty.
     * @param lname The player's last name, which can be non-unique and empty.
     * @return Player A new Player object.
     * */
    public static Player createPlayer(String username, String password, String fname, String lname) {
        return new Player(username, password, fname, lname);
    }
    
    /**
     * Create a new administrator user.
     * @param username The username, which must be unique and non-empty.
     * @param password The password, which can be non-unique and empty.
     * @param fname The administrator's first name, which can be non-unique and empty.
     * @param lname The administrator's last name, which can be non-unique and empty.
     * @param adminID The administrator's admin ID, which must be an integer.
     * @return Player A new Administrator object.
     * */
    public static Administrator createAdmin(String username, String password, String fname, String lname, int adminID) {
        return new Administrator(username, password, fname, lname, adminID);
    }
}