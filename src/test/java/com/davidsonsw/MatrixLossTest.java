package com.davidsonsw;

import com.davidsonsw.matrix.MatrixLoss;
import com.davidsonsw.matrix.MatrixTransform;
import com.davidsonsw.matrix.MatrixUtil;
import org.ejml.data.DMatrixRMaj;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;

class MatrixLossTest {

    private final static Random RANDOM = new Random();
    private final static int NUMBER_OF_INPUTS = 4;
    private final static int NUMBER_OF_DATASETS = 6;
    private static final DMatrixRMaj ACTUAL;
    private static final DMatrixRMaj EXPECTED;

    private static final DMatrixRMaj EXPECTED_CROSS_ENTROPY;

    // initialize matrices with random data
    static {

        {
            double[][] actual = new double[NUMBER_OF_DATASETS][NUMBER_OF_INPUTS];
            for (int dataset = 0; dataset < NUMBER_OF_DATASETS; dataset++) {
                for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
                    actual[dataset][input] = input + 1.0;
                }
            }
            ACTUAL = new DMatrixRMaj(actual);
        }

        {
            double[][] expected = new double[NUMBER_OF_DATASETS][NUMBER_OF_INPUTS];
            for (int dataset = 0; dataset < NUMBER_OF_DATASETS; dataset++) {
                for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
                    if (dataset == input) {
                        expected[dataset][input] = 1.0;
                    } else {
                        expected[dataset][input] = 0.0;
                    }
                }
            }
            EXPECTED = new DMatrixRMaj(expected);
        }

        {
            double[][] expected = new double[NUMBER_OF_DATASETS][NUMBER_OF_INPUTS];
            for (int dataset = 0; dataset < NUMBER_OF_DATASETS; dataset++) {
                for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
                    if (dataset == input) {
                        expected[dataset][input] = -1 * Math.log(input + 1.0);
                    } else {
                        expected[dataset][input] = 0.0;
                    }
                }
            }
            EXPECTED_CROSS_ENTROPY = new DMatrixRMaj(expected);
        }

    }

    @Test
    void testCrossEntropy() {
        MatrixTransform.printMatrix(ACTUAL);
        MatrixTransform.printMatrix(EXPECTED);
        System.out.println(EXPECTED_CROSS_ENTROPY.toString());
        DMatrixRMaj actualCrossEntroy = MatrixLoss.applyCrossEntropy(ACTUAL,EXPECTED);
        System.out.println(actualCrossEntroy.toString());
        Assertions.assertTrue(MatrixUtil.areEquivalent(EXPECTED_CROSS_ENTROPY,actualCrossEntroy));
    }
}