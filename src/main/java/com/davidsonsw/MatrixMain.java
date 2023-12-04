package com.davidsonsw;

import org.ejml.simple.SimpleMatrix;

public class MatrixMain {

    private static final SimpleMatrix simpleInput = new SimpleMatrix(
            new double[][]{
                    new double[]{1d, 2d, 3d}
            }
    );
    private static final SimpleMatrix multipleInputs = new SimpleMatrix(
            new double[][]{
                    new double[]{1d, 2d, 3d},
                    new double[]{1d, 2d, 3d},
                    new double[]{1d, 2d, 3d}
            }
    );

    private static final SimpleMatrix weights = new SimpleMatrix(
            new double[][]{
                    new double[]{1d, 2d, 3d},
                    new double[]{1d, 2d, 3d},
                    new double[]{1d, 2d, 3d}
            }
    );

    private static final SimpleMatrix biases = new SimpleMatrix(
            new double[][]{
                    new double[]{1d, 1d, 1d},
                    new double[]{1d, 1d, 1d},
                    new double[]{1d, 1d, 1d}
            }
    );

    private static final double BIAS = 1d;

    private static final SimpleMatrix multipleBiases = new SimpleMatrix(
            new double[][]{
                    new double[]{1d, 1d, 1d},
                    new double[]{1d, 1d, 1d},
                    new double[]{1d, 1d, 1d}
            }
    );


    public static void main(String[] args) {
//        simpleActivation();
//        multipleActivation();
        plusExample();
    }

    private static void simpleActivation() {

        // inputs (dot) weights
        SimpleMatrix inputsDotWeights = simpleInput.mult(weights);
        System.out.println(inputsDotWeights.toString());

        // add biases
        SimpleMatrix result = inputsDotWeights.plus(BIAS);
        System.out.println(result);
    }

    private static void plusExample(){

        // inputs (dot) weights
        SimpleMatrix inputsDotWeights = multipleInputs.mult(weights);
        System.out.println(inputsDotWeights.toString());

        // add biases
        SimpleMatrix result = inputsDotWeights.plus(1, biases);
        System.out.println(result);

    }
    /**
     * This method is demonstrating an optimization whereby multiple *INPUTS* are activated within a single series of
     * matrix manipulations by simply adding more "rows" to the inputs. Essentially you're processing all of your training
     * data in a single "run" which will be much more efficient.
     */
    private static void multipleActivation() {

        // inputs (dot) weights
        SimpleMatrix inputsDotWeights = multipleInputs.mult(weights);
        System.out.println(inputsDotWeights.toString());

        // add biases
        SimpleMatrix result = inputsDotWeights.plus(BIAS);
        System.out.println(result);
    }

}
