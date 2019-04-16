import java.io.Serializable;
import java.time.ZonedDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String fname;
    private String lname;
    private double winRate;
    private ZonedDateTime lastLoginTime;
    //TODO: profile image
    
    public User(String username, String password, String fname, String lname) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        winRate = 0; //?
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
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
                + "Password: " + password + " " //should be present?
                + "First name: " + fname + " "
                + "Last name: " + lname + " "
                + "Win rate: " + winRate + " "
                + "Last login: none";
        }
        else {
            return "Username: " + username + " "
                + "Password: " + password + " " 
                + "First name: " + fname + " "
                + "Last name: " + lname + " "
                + "Win rate: " + winRate + " " 
                + "Last login: " + lastLoginTime.toString();
        }
    }
}