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
    
    /**
     * Creates a new record of the game.
     * @param finish The time the game ended.
     * @param winner The User object of the winner.
     * @param loser The User object of the loser.
     * */
    public GameRecord(ZonedDateTime finish, User winner, User loser) {
        dateCompleted = finish;
        this.winner = winner;
        this.loser = loser;
        winnerUsername = winner.getUsername();
        loserUsername = loser.getUsername();
    }
    
    /**
     * Gets the date the game finished.
     * @return The date the game finished.
     * */
    public ZonedDateTime getDateCompleted() {
        return dateCompleted;
    }
    
    /**
     * Gets the winner.
     * @return The winner of the game as a User object.
     * */
    public User getWinner() {
        return winner;
    }
    
    /**
     * Gets the loser.
     * @return The loser of the game as a User object.
     * */
    public User getLoser() {
        return loser;
    }
    
    /**
     * Gets the winner's username.
     * @return The winner of the game's username.
     * */
    public String getWinnerUsername() {
        return winnerUsername;
    }
    
    /**
     * Gets the loser's username.
     * @return The loser of the game's username.
     * */
    public String getLoserUsername() {
        return loserUsername;
    }
}