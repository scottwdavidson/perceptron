package com.davidsonsw.matrix;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class MatrixUtil {

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

    public static boolean areEquivalent(DMatrixRMaj matrix1, DMatrixRMaj matrix2){
        int size = matrix1.getNumRows() * matrix1.getNumCols();
        for (int i = 0; i < size ; i++){
            if (matrix1.get(i) != matrix2.get(i)){
                return false;
            }
        }
        return true;
    }
}
