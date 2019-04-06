import java.util.Scanner;

public class Score {
    //get dead stones manually
    public static int[] markDeadStones(int[][] endingBoard) {
        int[] deads = new int[endingBoard.length * endingBoard.length];
        int deadsIndex = 0;
        Scanner deadStones = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Type coordinates of dead stones in the form 'y x' without quotes, starting from top left as 0,0 or 'done' w/o quotes");
            String point = deadStones.nextLine();
            if (point.equals("done")) {
                done = true;
            }
            else {
                String[] coords = point.split(" ");
                deads[deadsIndex] = Integer.parseInt(coords[0]);
                ++deadsIndex;
                deads[deadsIndex] = Integer.parseInt(coords[1]);
                ++deadsIndex;
            }
        }
        return deads;
    }
    
    public static int[][] removeDeadStones(int[][] endingboard, int[] deadCoordinates) {
        //stuff
        return endingboard; //for testing
    }
    
    public static int[] calculateTerritory(int[][] finalBoard) {
        //stuff
        return new int[2]; //for testing
    }
    
    public static int[] calculateFinalScores(int[] territory, int[] captures) {
        //stuff
        return new int[2]; //for testing
    }
}