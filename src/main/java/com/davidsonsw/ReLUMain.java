package com.davidsonsw;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;


import java.util.Random;

/**
 * Rectify Linear Unit ( ReLU ) : see <a href="https://builtin.com/machine-learning/relu-activation-function">ReLU</a>
 * <p>
 * This example demonstrates randomized inputs ( between 0 and 1 ),
 * randomized weights ( between 0 and 1 using Gaussian so most data is centered around zero ) and
 * randomized bias ( between 0 and 1 using Gaussian so most data is centered around zero )
 * </p>
 * <p>
 * Quick explanation of terms and matrix operation order:
 * - A neuron has inputs so we're going to use INPUT to as such.
 * - We're applying an optimization here to allow a single matrix multiple over multiple inputs, which are referred to
 * as DATASETs ( the video training uses "input size" for INPUT and "number of inputs" for DATASETs which can be
 * confusing)
 * - WEIGHTS and BIASES matrix names are self-explanatory
 * - Because the DATASETs value is the variable here, we must ensure it's an "outside" matrix dimension so that we can
 * multiple and add our statically sized matrices together ( static based on the number of inputs into a neuron at
 * a particularly points in the network and the number of neurons input "into" in the network ).
 * - I've elected to use the *opposite* approach as the video training to keep the multiplication order of:
 * INPUT * WEIGHTED-NEURON + BIAS ( where there's a single bias per NEURON )
 * </p>
 */
public class ReLUMain {

    private final static Random RANDOM = new Random();
    private final static int NUMBER_OF_INPUTS = 4;
    private final static int NUMBER_OF_NEURONS = 5;
    private final static int NUMBER_OF_DATASETS = 6;
    private static final DMatrixRMaj INPUTS;
    private static final DMatrixRMaj WEIGHTS;
    private static final DMatrixRMaj BIASES;

    private static final UniOperation E_TO_THE_N_OPERATION = (a) -> Math.exp(a);
    private static final UniOperation RELU_OPERATION = (a) -> a > 0.0 ? a : 0.0;



    // initialize matrices with random data
    static {

        // inputs ( NUMBER_OF_DATASETS x NUMBER_OF_INPUTS )
        double[][] inputs = new double[NUMBER_OF_DATASETS][NUMBER_OF_INPUTS];
        for (int dataset = 0; dataset < NUMBER_OF_DATASETS; dataset++) {
            for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
                inputs[dataset][input] = RANDOM.nextDouble();
            }
        }
        INPUTS = new DMatrixRMaj(inputs);

        // weights ( NUMBER_OF_INPUTS x NUMBER_OF_NEURONS)
        double[][] weights = new double[NUMBER_OF_INPUTS][NUMBER_OF_NEURONS];
        for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
            for (int neuron = 0; neuron < NUMBER_OF_NEURONS; neuron++) {
                weights[input][neuron] = RANDOM.nextGaussian();
            }
        }
        WEIGHTS = new DMatrixRMaj(weights);

        // biases ( NUMBER_OF_DATASETS X NUMBER_OF_NEURONS )
        double[][] biases = new double[NUMBER_OF_DATASETS][NUMBER_OF_NEURONS];
        for (int neuron = 0; neuron < NUMBER_OF_NEURONS; neuron++) {
            double nextGaussian = RANDOM.nextGaussian();
            for (int dataset = 0; dataset < NUMBER_OF_DATASETS; dataset++) {

                // NOTE: loops are iterating by COL b/c the matrix is row DATASET oriented so the assignment
                // is unexpected as each dataset has to have duplicate values for each neuron
                //
                // NOTE: this may be why the author flips everything so datasets are presented as columns w/
                // neurons being rows
                biases[dataset][neuron] = nextGaussian;
            }
        }
        BIASES = new DMatrixRMaj(biases);

    }

    interface UniOperation {
        public double operate(double operandA);
//        public double operate(double ... operands);
    }
    interface Operation {
        public double operate(double operandA, double operandB);
//        public double operate(double ... operands);
    }
    private static DMatrixRMaj mult(DMatrixRMaj multiplandA, DMatrixRMaj multiplandB) {

        if (multiplandA.getNumCols() != multiplandB.getNumRows()) {
            String message = "MultiplandA rows ( " + multiplandA.getNumRows() + ") is != MultipicandB cols (" +
                    multiplandB.getNumCols() + ")";
            throw new IllegalArgumentException(message);
        }
        DMatrixRMaj product = new DMatrixRMaj(multiplandA.numRows, multiplandB.numCols);
        CommonOps_DDRM.mult(multiplandA, multiplandB, product);
        return product;
    }

    private static DMatrixRMaj add(DMatrixRMaj addendA, DMatrixRMaj addendB) {

        if (addendA.getNumRows() != addendB.getNumRows() && addendA.getNumCols() != addendB.getNumCols()) {
            String message = "AddendA rows/cols ( " + addendA.getNumRows() + "," + addendA.getNumCols() + ") is != AddendB rows/cols (" +
                    addendB.getNumRows() + "," + addendB.getNumCols() + ")";
            throw new IllegalArgumentException(message);
        }
        DMatrixRMaj sum = new DMatrixRMaj(addendA.numRows, addendA.numCols);
        CommonOps_DDRM.add(addendA, addendB, sum);
        return sum;
    }

    private static DMatrixRMaj applyUniOperation(DMatrixRMaj matrix, UniOperation uniOp){
        DMatrixRMaj result = new DMatrixRMaj(matrix.numRows, matrix.numCols);
        for (int i = 0; i < matrix.numRows; i++) {
            for (int j = 0; j < matrix.numCols; j++) {
                result.set(i,j, uniOp.operate(matrix.get(i,j)));
            }
        }
        return result;
    }

    private static DMatrixRMaj applySumColumnsOperation(DMatrixRMaj matrix, UniOperation uniOp){
        DMatrixRMaj result = new DMatrixRMaj(1, matrix.getNumCols());
        for (int col = 0; col < matrix.getNumCols(); col++) {
            double columnSum = 0.0;
            for (int row = 0; row < matrix.getNumRows(); row++) {
                columnSum += uniOp.operate(matrix.get(row,col));
            }
            result.set(0,col, columnSum);
        }
        return result;
    }

    private static DMatrixRMaj applySoftMaxOperation(DMatrixRMaj matrix){

        // calculate the column sums
        UniOperation eToTheN = (a) -> Math.exp(a);
        DMatrixRMaj columnSums = applySumColumnsOperation(matrix,E_TO_THE_N_OPERATION);

        // apply softmax to each element in a column using the columnSums
        DMatrixRMaj result = new DMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
        for (int col = 0; col < matrix.getNumCols(); col++) {
            double columnSum = columnSums.get(0,col);
            for (int row = 0; row < matrix.getNumRows(); row++) {
                result.set(row, col, E_TO_THE_N_OPERATION.operate(matrix.get(row, col)) / columnSum);
            }
        }

        return result;

    }
    private static void printMatrix(DMatrixRMaj matrix){
        printMatrix(matrix, "");
    }
    private static void printMatrix(DMatrixRMaj matrix, String optionalLabel){
        System.out.println("\n ------- " + optionalLabel + " -------\n");
        for (int i = 0; i < matrix.numRows; i++) {
            for (int j = 0; j < matrix.numCols; j++) {
                System.out.printf("%-6.4f ", matrix.get(i, j));
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
//        System.out.println(INPUTS);
//        System.out.println(WEIGHTS);
//        System.out.println(BIASES);

        // execute activation steps, i.e., x * w + i (bias)
        DMatrixRMaj weightedInputs = mult(INPUTS, WEIGHTS);
        printMatrix(weightedInputs, "weightedInputs");
        DMatrixRMaj biasedWeightedInputs = add(weightedInputs, BIASES);
        printMatrix(biasedWeightedInputs, "biasedWeightedInputs");

        // apply ReLU via Operation lambda function
        DMatrixRMaj reluResult = applyUniOperation(biasedWeightedInputs, RELU_OPERATION);
        printMatrix(reluResult,"ReLU applied");

        // calculate column sums
        DMatrixRMaj columnSumsResult = applySumColumnsOperation(reluResult, E_TO_THE_N_OPERATION);
        printMatrix(columnSumsResult,"ColumnSum");

        // apply softmax
        DMatrixRMaj softmaxResult = applySoftMaxOperation(reluResult);
        printMatrix(softmaxResult,"SoftMax");

    }

}
