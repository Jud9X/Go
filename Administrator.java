public class Administrator extends User {
    private int adminID;
    //TODO: join date
    
    public Administrator(String username, String fname, String lname, int adminID) {
        super(username, fname, lname);
        this.adminID = adminID;
    }
    
    public int getID() {
        return adminID;
    }
    
    //join date get() method
    
    public String toString() {
        return super.toString() + "\n" + "Admin ID number: " + adminID;
    }
    
    public static Player createPlayer(String username, String fname, String lname) {
        return new Player(username, fname, lname);
    }
    
    public static Administrator createAdmin(String username, String fname, String lname, int adminID) {
        return new Administrator(username, fname, lname, adminID);
    }
}