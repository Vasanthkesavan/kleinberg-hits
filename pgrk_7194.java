import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class pgrk_7194 {
    private static int AdjacencyMatrix[][];

    private static void makeAdjacencyMatrix(int a, int b) {
        AdjacencyMatrix[a][b] = 1;
    }

    private static Boolean checkArgs(String[] args) {
        if(args.length != 3) {
            return false;
        } else {
            return true;
        }
    }
    
}