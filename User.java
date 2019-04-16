import java.io.Serializable;
import java.time.ZonedDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String fname;
    private String lname;
    private double winRate;
    private ZonedDateTime lastLoginTime;
    //TODO: profile image
    
    public User(String username, String fname, String lname) {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        winRate = 0; //?
    }
    
    public String getUsername() {
        return username;
    }
    
    public double getWinRate() {
        return winRate;
    }
    
    public void setWinRate(double newRate) {
        winRate = newRate;
        return;
    }
    
    public void setLastLoginTime(ZonedDateTime loginTime) {
        this.lastLoginTime = ZonedDateTime.of(loginTime.toLocalDateTime(), loginTime.getZone());
    }
    
    public String toString() {
        if (lastLoginTime == null) {
            return "Username: " + username + " "
                + "First name: " + fname + " "
                + "Last name: " + lname + " "
                + "Win rate: " + winRate + " "
                + "Last login: none";
        }
        else {
            return "Username: " + username + " "
                + "First name: " + fname + " "
                + "Last name: " + lname + " "
                + "Win rate: " + winRate + " " 
                + "Last login: " + lastLoginTime.toString();
        }
    }
}