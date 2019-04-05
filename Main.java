import java.util.Arrays;

public class Main {
    public static void main(final String[] args) {
        Player p1 = new Player("ohart", "Oliver", "Hart");
        Player p2 = new Player("fred", "Fred", "West");
        GameState g = new GameState(3, p1, p2);
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        g.placePiece(2, 0);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        g.placePiece(1, 0);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        g.placePiece(1, 0);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        g.placePiece(1, 1);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println("Pass count: " + g.passCount);
        g.pass();
        System.out.println("Pass count: " + g.passCount);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println("Pass count: " + g.passCount);
        g.pass();
        System.out.println("Pass count: " + g.passCount);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println("Pass count: " + g.passCount);
        g.pass();
        System.out.println("Pass count: " + g.passCount);
        System.out.println(Arrays.deepToString(g.showBoard()));
        System.out.println(g.currentPlayerTurn);
        System.out.println(Arrays.deepToString(g.showBoard()));
        g.placePiece(0, 0);
        System.out.println(Arrays.deepToString(g.showBoard()));
    }
}