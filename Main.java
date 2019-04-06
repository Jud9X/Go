import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        Player p1 = new Player("ohart", "Oliver", "Hart");
        Player p2 = new Player("fred", "Fred", "West");
        GameState g = new GameState(4, p1, p2);
        Scanner userInput = new Scanner(System.in);
        while (!g.finished) {
            System.out.println("This player's turn: " + g.currentPlayerTurn);
            System.out.println(Arrays.deepToString(g.getBoard()));
            System.out.println("Type coordinates to place your piece in the form 'y x' without quotes, starting from top left as 0,0 or 'pass' w/o quotes");
            String point = userInput.nextLine();
            if (point.equals("pass")) {
                g.pass();
                System.out.println("Number of consecutive passes: " + g.passCount);
            }
            else {
                String[] coords = point.split(" ");
                g.placePiece(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            }
            System.out.println(Arrays.deepToString(g.getBoard()));
        }
        userInput.close();
        System.out.println("game is over");
    }
}