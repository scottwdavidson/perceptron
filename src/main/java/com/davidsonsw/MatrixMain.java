package com.davidsonsw;

import org.ejml.simple.SimpleMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.data.DMatrixIterator;


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
//        plusExample();
        playWithDMatrix();
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

    private static void playWithDMatrix() {
        DMatrixRMaj matrix1 = new DMatrixRMaj(new double[][]{{1, 2}, {3, 4}});
        DMatrixRMaj matrix2 = new DMatrixRMaj(new double[][]{{5, 6}, {7, 8}});

        // Create an empty matrix to store the result
        DMatrixRMaj result = new DMatrixRMaj(matrix1.numRows, matrix2.numCols);

        // Multiply the matrices
        CommonOps_DDRM.mult(matrix1, matrix2, result);

        // Print the result
        for (int i = 0; i < result.numRows; i++) {
            for (int j = 0; j < result.numCols; j++) {
                System.out.print(result.get(i, j) + " ");
            }
            System.out.println();
        }

        // Add the matrices
        CommonOps_DDRM.add(matrix1, matrix2, result);

        // Print the result
        for (int i = 0; i < result.numRows; i++) {
            for (int j = 0; j < result.numCols; j++) {
                System.out.print(result.get(i, j) + " ");
            }
            System.out.println();
        }

        DMatrixIterator iterator = result.iterator(true, 0, 0, result.numRows-1, result.numCols-1);

        // Iterate through the matrix
        while (iterator.hasNext()) {
            double value = iterator.next();
            int index = iterator.getIndex();
            System.out.println("(" + index + "): " + value);
            result.set(index, value + 3.5);
        }
        iterator = result.iterator(true, 0, 0, result.numRows-1, result.numCols-1);

        // Iterate through the matrix
        while (iterator.hasNext()) {
            double value = iterator.next();
            int index = iterator.getIndex();
            System.out.println("(" + index + "): " + value);
        }

    }
}
