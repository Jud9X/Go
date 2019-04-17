import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Parent class of the Player and Administrator classes.
 * @author Oliver
 * @version 1.2
 * */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String fname;
    private String lname;
    private double winRate;
    private ZonedDateTime previousLastLoginTime;
    private ZonedDateTime lastLoginTime; //is actually the time for "this session"
    //TODO: profile image
    
    public User(String username, String password, String fname, String lname) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        winRate = 0; //start at 0? THIS NEEDS TO BE GIVEN AS A %, e.g. 54 instead of 0.54
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFname() {
        return fname;
    }
    
    public String getLname() {
        return lname;
    }
    
    public double getWinRate() {
        return winRate;
    }
    
    public void setWinRate(double newRate) {
        winRate = newRate;
        return;
    }
    
    public void setLastLoginTime(ZonedDateTime loginTime) {
        if (lastLoginTime == null) {
            previousLastLoginTime = loginTime;
        }
        else {
            previousLastLoginTime = lastLoginTime;
        }
        lastLoginTime = ZonedDateTime.of(loginTime.toLocalDateTime(), loginTime.getZone());
    }
    
    public ZonedDateTime getPreviousLastLoginTime() {
        if (previousLastLoginTime == null) return lastLoginTime;
        else return previousLastLoginTime;
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