import java.time.ZonedDateTime;

public class GameRecord {
    private ZonedDateTime dateCompleted;
    private User winner;
    private User loser;
    
    public GameRecord(ZonedDateTime finish, User winner, User loser) {
        dateCompleted = finish;
        this.winner = winner;
        this.loser = loser;
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
}