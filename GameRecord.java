import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Records the details that need to be saved after a game is completed.
 * @author Oliver
 * @version 1.1
 * */
public class GameRecord implements Serializable {
    private ZonedDateTime dateCompleted;
    private User winner;
    private User loser;
    private String winnerUsername;
    private String loserUsername;
    
    public GameRecord(ZonedDateTime finish, User winner, User loser) {
        dateCompleted = finish;
        this.winner = winner;
        this.loser = loser;
        winnerUsername = winner.getUsername();
        loserUsername = loser.getUsername();
    }
    
    public ZonedDateTime getDateCompleted() {
        return dateCompleted;
    }
    
    public User getWinner() {
        return winner;
    }
    
    public User getLoser() {
        return loser;
    }
    
    public String getWinnerUsername() {
        return winnerUsername;
    }
    
    public String getLoserUsername() {
        return loserUsername;
    }
}