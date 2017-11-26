import java.io.*;
import java.util.Scanner;

public class hits_7194 {
    private static String initialValue;
    private static int iterationValue;
    private static int length;
    private static int AdjacencyMatrix[][];
    private static double hub_Vector[];
    private static double authority_Vector[];
    private static double last_HubV[];
    private static double last_AuthorityV[];
    private static Boolean s_Graph = true;
    private static String inputFile;
    private static int transpose_Matrix[][];

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
        Double total = 0.0;
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

    private static Boolean checkArgs(args) {
        if(args.length == 3) {
            return true;
        } else {
            return false;
        }
    }

    private static int parseArgInt(val) {
        return Integer.parseInt(val);
    }

    public static void main(String[] args) {
        if(checkArgs(args)) {
            try{
                iterationValue = parseArgInt(args[0]);
                initialValue = args[1];
                inputFile = args[2];

                Scanner inputStream = new Scanner(new File(inputFile));
                length = inputStream.nextInt();

                /* Edge Cases */
                if(length > 10) {
                    iterationValue = 0;
                    initialValue = 0
                    s_Graph = false;
                }

                if(initialValue.equals("-1")) {
                    initialValue = String.valueOf((1 / ((float) length)))
                } else if(initialValue.equals("-2")) {
                    initialValue = String.valueOf((1 / ((float) Math.sqrt(length))))
                }

                /* Required matrices and vectors */

                AdjacencyMatrix = new int[length][length];
                authority_Vector = new double[length];
                last_AuthorityV = new double[length];
                hub_Vector = new double[length];
                last_HubV = new double[length];

                if(initialValue.equals("0") == false) {
                    for(int i = 0; i < length; i++) {
                        authority_Vector[i] = Double.parseDouble(initialValue);
                    }

                    for(int j = 0; j < length; j++) {
                        hub_Vector[i] = Double.parseDouble(initialValue);
                    }
                }
                
                while(inputStream.hasNext()) {
                    int a = inputStream.nextInt();
                    int b = inputStream.nextInt();
                    adjacencyMatrix(a, b);
                }



            } catch(FileNotFoundException x) {
                x.printStackTrace();
            }
        }
    }
}