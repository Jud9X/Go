public class Administrator extends User {
    private int adminID;
    //TODO: join date
    
    public Administrator(String username_, String fname_, String lname_, int adminID_) {
        super(username_, fname_, lname_);
        adminID = adminID_;
    }
    
    public int getID() {
        return adminID;
    }
    
    //join date get() method
    
    public String toString() {
        return super.toString() + "\n" + "Admin ID number: " + adminID;
    }
    
    public static Player createPlayer(String username_, String fname_, String lname_) {
        return new Player(username_, fname_, lname_);
    }
    
    public static Administrator createAdmin(String username_, String fname_, String lname_, int adminID_) {
        return new Administrator(username_, fname_, lname_, adminID_);
    }
}