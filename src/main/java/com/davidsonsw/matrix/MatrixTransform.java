package com.davidsonsw.matrix;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class MatrixTransform {


    private static final UniOperation E_TO_THE_N_OPERATION = (a) -> Math.exp(a);
    private static final UniOperation RELU_OPERATION = (a) -> a > 0.0 ? a : 0.0;

    /**
     * Density transformation - applies the standard activation function of multiplying by weights and adding a bias
     *
     * @param matrix
     * @return
     */
    public static DMatrixRMaj applyDensityTransform(DMatrixRMaj inputs, DMatrixRMaj weights, DMatrixRMaj biases) {
        DMatrixRMaj weightedInputs = mult(inputs, weights);
        printMatrix(weightedInputs, "weightedInputs");
        DMatrixRMaj biasedWeightedInputs = add(weightedInputs, biases);
        printMatrix(biasedWeightedInputs, "biasedWeightedInputs");

        return biasedWeightedInputs;
    }

    /**
     * @param matrix the matrix to be operated on
     * @return the newly created matrix with ReLU applied
     * <p>
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
    public static DMatrixRMaj applyReLUTransform(DMatrixRMaj matrix) {
        DMatrixRMaj reluResult = applyUniOperation(matrix, RELU_OPERATION);
        return reluResult;
    }

    /**
     * See https://towardsdatascience.com/softmax-activation-function-how-it-actually-works-d292d335bd78
     *
     * @param matrix
     * @return
     */
    public static DMatrixRMaj applySoftMaxTransform(DMatrixRMaj matrix) {

        // calculate the column sums
        DMatrixRMaj columnSums = sumColumns(matrix, E_TO_THE_N_OPERATION);

        // apply softmax to each element in a column using the columnSums
        DMatrixRMaj result = new DMatrixRMaj(matrix.getNumRows(), matrix.getNumCols());
        for (int col = 0; col < matrix.getNumCols(); col++) {
            double columnSum = columnSums.get(0, col);
            for (int row = 0; row < matrix.getNumRows(); row++) {
                result.set(row, col, E_TO_THE_N_OPERATION.operate(matrix.get(row, col)) / columnSum);
            }
        }

        return result;

    }

    public static void printMatrix(DMatrixRMaj matrix) {
        printMatrix(matrix, "");
    }

    public static void printMatrix(DMatrixRMaj matrix, String optionalLabel) {
        System.out.println("\n ------- " + optionalLabel + " -------\n");
        for (int i = 0; i < matrix.numRows; i++) {
            for (int j = 0; j < matrix.numCols; j++) {
                System.out.printf("%-6.4f ", matrix.get(i, j));
            }
            System.out.println();
        }
    }

    interface UniOperation {
        public double operate(double operandA);
    }

    interface Operation {
        public double operate(double operandA, double operandB);
    }

    protected static DMatrixRMaj mult(DMatrixRMaj multiplandA, DMatrixRMaj multiplandB) {

        if (multiplandA.getNumCols() != multiplandB.getNumRows()) {
            String message = "MultiplandA rows ( " + multiplandA.getNumRows() + ") is != MultipicandB cols (" +
                    multiplandB.getNumCols() + ")";
            throw new IllegalArgumentException(message);
        }
        DMatrixRMaj product = new DMatrixRMaj(multiplandA.numRows, multiplandB.numCols);
        CommonOps_DDRM.mult(multiplandA, multiplandB, product);
        return product;
    }

    protected static DMatrixRMaj add(DMatrixRMaj addendA, DMatrixRMaj addendB) {

        if (addendA.getNumRows() != addendB.getNumRows() && addendA.getNumCols() != addendB.getNumCols()) {
            String message = "AddendA rows/cols ( " + addendA.getNumRows() + "," + addendA.getNumCols() + ") is != AddendB rows/cols (" +
                    addendB.getNumRows() + "," + addendB.getNumCols() + ")";
            throw new IllegalArgumentException(message);
        }
        DMatrixRMaj sum = new DMatrixRMaj(addendA.numRows, addendA.numCols);
        CommonOps_DDRM.add(addendA, addendB, sum);
        return sum;
    }

    private static DMatrixRMaj applyUniOperation(DMatrixRMaj matrix, UniOperation uniOp) {
        DMatrixRMaj result = new DMatrixRMaj(matrix.numRows, matrix.numCols);
        for (int i = 0; i < matrix.numRows; i++) {
            for (int j = 0; j < matrix.numCols; j++) {
                result.set(i, j, uniOp.operate(matrix.get(i, j)));
            }
        }
        return result;
    }

    private static DMatrixRMaj sumColumns(DMatrixRMaj matrix, UniOperation uniOp) {
        DMatrixRMaj result = new DMatrixRMaj(1, matrix.getNumCols());
        for (int col = 0; col < matrix.getNumCols(); col++) {
            double columnSum = 0.0;
            for (int row = 0; row < matrix.getNumRows(); row++) {
                columnSum += uniOp.operate(matrix.get(row, col));
            }
            result.set(0, col, columnSum);
        }
        return result;
    }
}
