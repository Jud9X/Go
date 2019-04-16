import java.time.ZonedDateTime;

public class Administrator extends User {
    private int adminID;
    private ZonedDateTime joinDate;
    
    public Administrator(String username, String password, String fname, String lname, int adminID) {
        super(username, password, fname, lname);
        this.adminID = adminID;
        joinDate = ZonedDateTime.now();
    }
    
    public ZonedDateTime getJoinDate() {
        return joinDate;
    }
    
    public int getAdminID() {
        return adminID;
    }
    
    //join date get() method
    
    public String toString() {
        return super.toString() + " " + "Admin ID number: " + adminID + " " + "Join date: " + joinDate.toString();
    }
    
    public static Player createPlayer(String username, String password, String fname, String lname) {
        return new Player(username, password, fname, lname);
    }
    
    public static Administrator createAdmin(String username, String password, String fname, String lname, int adminID) {
        return new Administrator(username, password, fname, lname, adminID);
    }
}