package com.davidsonsw.matrix;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class MatrixLoss {

    /**
     * Cross Entropy - applies the cross entropy formula of -expected(index) * log(actual(index)). Each column of expected
     * values will contain one value of 1.0 and the rest will be 0.0, so we're essentially isolating the relative expected
     * values from the actual matrix column ( which each row representing another dataset )
     *
     * @param actual - the actual values determined by the neural net
     * @param expected - the expected value, essentially one element in each column will be 1.0 indicated the relative expected value
     * @return a matrix in which each column has a single value representing the actual neural nets' calculation
     */
    public static DMatrixRMaj applyCrossEntropy(DMatrixRMaj actual, DMatrixRMaj expected) {

        DMatrixRMaj result = new DMatrixRMaj(actual.getNumRows(), actual.getNumCols());
        for (int row = 0; row < actual.getNumRows(); row++) {
            for (int col = 0; col < actual.getNumCols(); col++) {
                result.set(row, col, -1.0 * expected.get(row,col) * Math.log(actual.get(row,col)));
            }
        }

        return result;
    }
}
