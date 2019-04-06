import java.util.Scanner;

public class Score {
    //get dead stones manually, return int[] with final 2 values being removeBlackCount and then removeWhiteCount
    public static int[] markDeadStones(int[][] endingBoard) {
        int[] deads = new int[endingBoard.length * endingBoard.length];
        int deadsIndex = 0;
        Scanner deadStones = new Scanner(System.in);
        boolean done = false;
        int removeBlackCount = 0;
        int removeWhiteCount = 0;
        while (!done) {
            System.out.println("Type coordinates of dead stones in the form 'y x' without quotes, starting from top left as 0,0 or 'done' w/o quotes");
            String point = deadStones.nextLine();
            if (point.equals("done")) {
                done = true;
            }
            else { //should check that they've actually chosen a stone rather than an empty intersection?
                String[] coords = point.split(" ");
                deads[deadsIndex] = Integer.parseInt(coords[0]);
                ++deadsIndex;
                deads[deadsIndex] = Integer.parseInt(coords[1]);
                ++deadsIndex;
                if (endingBoard[deads[deadsIndex-1]][deads[deadsIndex]] == 1) ++removeBlackCount;
                else ++removeWhiteCount;
            }
        }
        int[] finalDeads = new int[removeBlackCount + removeWhiteCount + 2];
        for (int i = 0; i < removeBlackCount  + removeWhiteCount; ++i) finalDeads = deads[i];
        finalDeads[finalDeads.length-2] = removeBlackCount;
        finalDeads[finalDeads.length-1] = removeWhiteCount;
        return finalDeads;
    }
    
    public static int[][] removeDeadStones(int[][] endingBoard, int[] deadCoordinates) { //deadCoordinates includes the 2 counts at the end
        for (int i = 0; i < deadCoordinates.length-3; i += 2) {
            endingBoard[deadCoordinates[i]][deadCoordinates[i+1]] = 0;
        }
        return endingBoard;
    }
    
    public static int[] calculateTerritory(int[][] finalBoard) {
        //stuff
        return new int[2]; //for testing
    }
    
    public static int[] calculateFinalScores(int[] territory, int[] captures, int deadBlacks, int deadWhites) {
        //stuff
        return new int[2]; //for testing
    }
}