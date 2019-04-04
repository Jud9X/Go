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
}