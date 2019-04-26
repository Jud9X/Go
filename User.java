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
    private ZonedDateTime previousLoginTime;
    private ZonedDateTime thisLoginTime;
    private int gameCount;
    private int winCount;
    private double winRate;
    private int previousLeaderboardPosition;
    private int leaderboardPosition;
    private ZonedDateTime joinDate;
    private int profileImg;
    
    public User(String username, String password, String fname, String lname) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        gameCount = 0;
        winCount = 0;
        winRate = 0;
        previousLeaderboardPosition = 0;
        leaderboardPosition = 0;
        joinDate = ZonedDateTime.now();
        profileImg = 0;
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
    
    public int getGameCount() {
        return gameCount;
    }
    
    public int getWinCount() {
        return winCount;
    }
    
    public int getWinRate() {
        return (int) winRate;
    }
    
    public void incrementWinCount() {
        ++winCount;
        return;
    }
    
    public void incrementGameCount() {
        ++gameCount;
        winRate = ((double) winCount / gameCount) * 100;
        return;
    }
    
    public void setThisLoginTime(ZonedDateTime loginTime) {
        if (thisLoginTime == null) {
            previousLoginTime = null;
        }
        else {
            previousLoginTime = thisLoginTime;
        }
        thisLoginTime = ZonedDateTime.of(loginTime.toLocalDateTime(), loginTime.getZone());
    }
    
    public ZonedDateTime getPreviousLoginTime() {
        if (previousLoginTime == null) return null;
        else return previousLoginTime;
    }
    
    public ZonedDateTime getJoinDate() {
        return joinDate;
    }
    
    public int getPreviousLeaderboardPosition() {
        return previousLeaderboardPosition;
    }
    
    public void setPreviousLeaderboardPosition(int pos) {
        previousLeaderboardPosition = pos;
    }
    
    public int getLeaderboardPosition() {
        return leaderboardPosition;
    }
    
    public void setLeaderboardPosition(int pos) {
        leaderboardPosition = pos;
    }
    
    public int getProfileImg() {
        return profileImg;
    }
    
    public void setProfileImg(int choice) {
        profileImg = choice;
    }
    
    public String toString() { //necessary?
        if (previousLoginTime == null) {
            return "Username: " + username + " "
                + "Password: " + password + " " //should be present?
                + "First name: " + fname + " "
                + "Last name: " + lname + " "
                + "Win rate: " + winRate + " " 
                + "Join date: " + joinDate.toString() + " " 
                + "Last login: none";
        }
        else {
            return "Username: " + username + " "
                + "Password: " + password + " " 
                + "First name: " + fname + " "
                + "Last name: " + lname + " "
                + "Win rate: " + winRate + " " 
                + "Join date: " + joinDate.toString() + " " 
                + "Last login: " + previousLoginTime.toString();
        }
    }
}