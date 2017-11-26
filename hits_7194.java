import java.io.*;
import java.util.Scanner;

public class hits_7194 {
    private static String initialValue;
    private static int iteration;
    private static int length;
    private static int AdjacencyMatrix[][];
    private static double hub_Vector[];
    private static double authority_Vector[];
    private static double last_HubV[];
    private static double last_AuthorityV[];

    /* Private methods for matrix related calculations */

    private static void adjacencyMatrix(int a, int b) {
        AdjacencyMatrix[a][b] = 1;
    }

    private static int[][] makeTransposeMatrix(int mValue[][]) {
        int a[][] = new int[length][length];
        int i, j;

        for(i = 0; i < length; i++) {
            for(j = 0; j < length; j++) {
                a[i][j] = mValue[j][i];
            }
        }
    }

    private static void showMatrix(String mValue[][]) {
        int i, j;

        for(i = 0; i < length; i++) {
            for(j = 0; j < length; j++) {
                System.out.print(mValue[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static double[] multiplyMatrix(int first_Matrix[][], double second_Matrix[]) {
        double a[] = new double[length], total = 0;
        int i, j;

        for(i = 0; i < length; i++) {
            for(j = 0; j < length; j++) {
                if(first_Matrix[i][j] === 1) {
                    total += (double) first_Matrix[i][j] * (second_Matrix[j]);
                }
            }
            a[i] = total;
            total = 0;
        }
        return a;
    }

    private static double[] toScale(double mValue[]) {
        double total = 0.0;
        double a[] = new double[length];
        int i;

        for(i = 0; i < length; i++) {
            total += Math.pow(mValue[i], 2);
        }
        total = Math.sqrt(total);

        for(i = 0; i < length; i++) {
            a[i] = (mValue[i]/total);
        }
        return a;
    }
}