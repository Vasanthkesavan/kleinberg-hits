/* VASANTHAN KESAVAN cs610 7194 prp */

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
    private static int bug_Fix;
    private static double errorCount;
    private static int primaryCount;
    private static int secondaryCount;
    private static Boolean limit;
    private static int iterationCount;
    /* Private methods for matrix related calculations */

    private static void makeAdjacencyMatrix(int a, int b) {
        AdjacencyMatrix[a][b] = 1;
    }

    private static int[][] makeTransposeMatrix(int mValue[][]) {
        int a[][] = new int[length][length];

        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                a[i][j] = mValue[j][i];
            }
        }
        return a;
    }

    private static void showMatrix(String mValue[][]) {
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                System.out.print(mValue[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static double[] multiplyMatrix(int first_Matrix[][], double second_Matrix[]) {
        double a[] = new double[length];
        double total = 0;
    
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                if(first_Matrix[i][j] == 1) {
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
        
        for(int i = 0; i < length; i++) {
            total += Math.pow(mValue[i], 2);
        }
        total = Math.sqrt(total);

        for(int i = 0; i < length; i++) {
            a[i] = (mValue[i]/total);
        }
        return a;
    }

    private static Boolean checkArgs(String[] args) {
        if(args.length != 3) {
            return false;
        } else {
            return true;
        }
    }

    private static int parseArgInt(String val) {
        return Integer.parseInt(val);
    }

    private static Boolean checkAbsolute(double last_Vector, double vector, double count) {
        if(Math.abs(last_Vector - vector) < count) {
            return true;
        }
        return false;
    }

    private static Boolean vectorValueCheck(double vector) {
        if(String.valueOf(vector).equals("NaN")) {
            return true;
        }
        return false;
    }

    private static String printTheResult(int index, double authorityValue, double hubValue) {
        String result1 = String.format("%.7f", authorityValue / 1.0);
        String result2 = String.format("%.7f", hubValue / 1.0);

        String result = "A/H[" + " " + index + "]=" + result1 + "/" + result2 + " ";
        return result;
    }

    public static void main(String[] args) {
        if(checkArgs(args)) {
            try{
                iterationValue = parseArgInt(args[0]);
                initialValue = args[1];
                inputFile = args[2];

                Scanner inputStream = new Scanner(new File(inputFile));
                
                length = inputStream.nextInt();
                bug_Fix = inputStream.nextInt();

                /* Edge Cases */
                if(length > 10) {
                    iterationValue = 0;
                    initialValue = "-1";
                    s_Graph = false;
                }

                if(initialValue.equals("-1")) {
                    initialValue = String.valueOf((1 / ((float) length)));
                } else if(initialValue.equals("-2")) {
                    initialValue = String.valueOf((1 / ((float) Math.sqrt(length))));
                }

                /* Required matrices and vectors */
                AdjacencyMatrix = new int[length][length];
                authority_Vector = new double[length];
                last_AuthorityV = new double[length];
                hub_Vector = new double[length];
                last_HubV = new double[length];

                if(!(initialValue.equals("0"))) {
                    for(int i = 0; i < length; i++) {
                        authority_Vector[i] = Double.parseDouble(initialValue);
                    }

                    for(int i = 0; i < length; i++) {
                        hub_Vector[i] = Double.parseDouble(initialValue);
                    }
                }
                
                while(inputStream.hasNext()) {
                    int a = inputStream.nextInt();
                    int b = inputStream.nextInt();
                    makeAdjacencyMatrix(a, b);
                };
                int transpose_Matrix[][] = makeTransposeMatrix(AdjacencyMatrix);

                if(s_Graph) {
                    System.out.print("Base : 0 : ");
                    for(int i = 0; i < length - 1; i++) {
                        System.out.printf(printTheResult(i, authority_Vector[i], hub_Vector[i]));     
                    }
                    System.out.println();
                };

                if(iterationValue < 0) {
                    errorCount = Math.pow(10, (iterationValue));
                } else if(iterationValue == 0) {
                    errorCount = Math.pow(10, -5);
                }
                limit = true;
                iterationCount = 1;

                for(int i = 0; i < length; i++) {
                    last_AuthorityV[i] = authority_Vector[i];
                    last_HubV[i] = hub_Vector[i];
                }

                while(limit) {
                    for(int i = 0; i < length; i++) {
                        authority_Vector[i] = 0.0;
                    }

                    authority_Vector = multiplyMatrix(transpose_Matrix, hub_Vector);

                    for(int i = 0; i < length; i++) {
                        hub_Vector[i] = 0.0;
                    }

                    hub_Vector = multiplyMatrix(AdjacencyMatrix, authority_Vector);
                    authority_Vector = toScale(authority_Vector);
                    hub_Vector = toScale(hub_Vector);

                    if(s_Graph) {
                        System.out.print("Iter : " + (iterationCount) + " : ");
                        for(int i = 0; i < length - 1; i++) {
                            System.out.printf(printTheResult(i, authority_Vector[i], hub_Vector[i]));
                        }
                        System.out.println();
                    }

                    if(iterationValue > 0) {
                        if(iterationCount == iterationValue) {
                            limit = false;
                        }
                    } else {
                        primaryCount = 0;
                        secondaryCount = 0;
                        for(int i = 0; i < length; i++) {
                            if(checkAbsolute(last_AuthorityV[i], authority_Vector[i], errorCount) || vectorValueCheck(authority_Vector[i])) {
                                primaryCount++;
                            }
                            if(checkAbsolute(last_HubV[i], hub_Vector[i], errorCount) || vectorValueCheck(hub_Vector[i])) {
                                secondaryCount++;
                            }
                            last_AuthorityV[i] = authority_Vector[i];
                            last_HubV[i] = hub_Vector[i];
                        }
                        if(primaryCount == length && secondaryCount == length) {
                            limit = false;
                            if(s_Graph == false) {
                                System.out.println("Iter   : " + (iterationCount));
                                for(int i = 0; i < 4; i++) {
                                    System.out.printf(printTheResult(i, authority_Vector[i], hub_Vector[i]));
                                    System.out.println();
                                }
                                
                                System.out.println(" ...");
                            }
                        }
                    }
                    iterationCount++;
                }
            } catch(FileNotFoundException x) {
                x.printStackTrace();
            }
        }
    }
}
