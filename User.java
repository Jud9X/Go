public class User { //should be private??
    private String username;
    private String fname;
    private String lname;
    private double winRate;
    //TODO: date and time of last login
    //TODO: profile image
    
    public User(String username_, String fname_, String lname_) { //should be private??
        this.username = username_;
        this.fname = fname_;
        this.lname = lname_;
        winRate = 0; //?
    }
    
    public String getUsername() {
        return username;
    }
    
    public double getWinRate() {
        return winRate;
    }
    
    public void updateWinRate(double newRate) {
        winRate = newRate;
        return;
    }
    
    public String toString() {
        return "Username: " + username + "\n"
            + "First name: " + fname + "\n"
            + "Last name: " + lname + "\n"
            + "Win rate: " + winRate;
    }
}