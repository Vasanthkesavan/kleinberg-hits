/* VASANTHAN KESAVAN cs610 7194 prp */

import java.io.*;
import java.util.Scanner;

public class pgrk_7194 {
    private static int AdjacencyMatrix[][];
    private static int iterationValue;
    private static int iterationCount;
    private static String initialValue;
    private static String fileInput;
    private static int length;
    private static int bug_Fix;
    private static Boolean s_Graph = true;
    private static double vector[];
    private static double source[];
    private static int outDegree[];
    private static double errorCount;
    private static Boolean limit;
    private static double dampingValue = 0.85;
    private static double primaryCount;

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

    private static int parseArgInt(String val) {
        return Integer.parseInt(val);
    }

    private static String printTheResult(int index, double source) {
        Float result1 = Float.parseFloat(String.valueOf(source));
        String result2 = String.format("%.7f", result1 / 1.0);
        String result = "P[" + " " + index + "]=" + result2 + " ";
        return result;
    }

    public static void main(String[] args) {
        if(checkArgs(args)) {
            try {
                iterationValue = parseArgInt(args[0]);
                initialValue = args[1];
                fileInput = args[2];

                if(!(parseArgInt(initialValue) >= -2 && parseArgInt(initialValue) <= -1)) {
                    System.out.println("The initial value has to be -1, -2, 0, 1");
                    return;
                }

                Scanner fileStream = new Scanner(new File(fileInput));
                length = fileStream.nextInt();
                bug_Fix = fileStream.nextInt();

                AdjacencyMatrix = new int[length][length];

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
                vector = new double[length];
                source = new double[length];

                for(int i = 0; i < length; i++) {
                    vector[i] = 0.0;
                    source[i] = 0.0;
                }

                if(!(initialValue.equals("0"))) {
                    for(int i = 0; i < length; i++) {
                        source[i] = Double.parseDouble(initialValue);
                    }
                }

                while(fileStream.hasNext()) {
                    int a = fileStream.nextInt();
                    int b = fileStream.nextInt();
                    makeAdjacencyMatrix(a, b);
                }
                outDegree = new int[length];

                for(int i = 0; i < length; i++) {
                    outDegree[i] = 0;
                    for(int j = 0; j < length; j++) {
                        outDegree[i] += AdjacencyMatrix[i][j];
                    }
                }

                if(s_Graph) {
                    System.out.print("Base : 0 :" );
                    for(int i = 0; i < length; i++) {
                        System.out.printf(printTheResult(i, source[i]));
                    }
                    System.out.println();
                }

                if(iterationValue < 0) {
                    errorCount = Math.pow(10, iterationValue);
                } else if(iterationValue == 0) {
                    errorCount = Math.pow(10, -5);
                }
                iterationCount = 1;
                limit = true;

                while(limit) {
                    for(int i = 0; i < length; i++) {
                        vector[i] = 0.0;
                    }

                    for(int i = 0; i < length; i++) {
                        for(int j = 0; j < length; j++) {
                            if(AdjacencyMatrix[j][i] == 1) {
                                vector[i] = (vector[i]) + (source[j] / outDegree[j]);
                            }
                        }
                    }

                    for(int i = 0; i < length; i++) {
                        vector[i] = (dampingValue * (vector[i]) + ((1 - dampingValue) / (float) length));
                    }

                    if(s_Graph) {
                        System.out.print("Iter : " + (iterationCount) + " :");
                        for(int i = 0; i < length; i++) {
                            System.out.printf(printTheResult(i, vector[i]));
                        }
                        System.out.println();
                    }

                    if(iterationValue > 0) {
                        if(iterationCount == iterationValue) {
                            limit = false;
                        }
                    } else {
                        primaryCount = 0;
                        for(int i = 0; i < length; i++) {
                            if(Math.abs((source[i]) - vector[i]) < errorCount) {
                                primaryCount++;
                            }
                        }
                        if(primaryCount == length) {
                            limit = false;
                            if(!s_Graph) {
                                System.out.print("Iter   : " + iterationCount);
                                for(int i = 0; i < 3; i++) {
                                    System.out.println();
                                    System.out.printf(printTheResult(i, vector[i]) + " ");
                                }
                                System.out.println();
                                System.out.println("     ...other vertices omitted");
                            }
                        }
                    }
                    iterationCount++;
                    for(int i = 0; i < length; i++) {
                        source[i] = vector[i];
                    }
                }
            } catch(FileNotFoundException x) {
                x.printStackTrace();
            }   
        }
    }
}