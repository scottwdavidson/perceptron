package com.davidsonsw;

import com.davidsonsw.matrix.MatrixTransform;
import org.ejml.data.DMatrixRMaj;
import java.util.Random;

public class ReLUMain {

    private final static Random RANDOM = new Random();
    private final static int NUMBER_OF_INPUTS = 4;
    private final static int NUMBER_OF_NEURONS = 5;
    private final static int NUMBER_OF_DATASETS = 6;
    private static final DMatrixRMaj INPUTS;
    private static final DMatrixRMaj WEIGHTS;
    private static final DMatrixRMaj BIASES;

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

    public static void main(String[] args) {
//        System.out.println(INPUTS);
//        System.out.println(WEIGHTS);
//        System.out.println(BIASES);

        // execute activation steps, i.e., x * w + i (bias)
        DMatrixRMaj biasedWeightedInputs = MatrixTransform.applyDensityTransform(INPUTS, WEIGHTS, BIASES);

        // apply ReLU via Operation lambda function
        DMatrixRMaj reluResult = MatrixTransform.applyReLUTransform(biasedWeightedInputs);
        MatrixTransform.printMatrix(reluResult,"ReLU applied");

        // apply softmax
        DMatrixRMaj softmaxResult = MatrixTransform.applySoftMaxTransform(reluResult);
        MatrixTransform.printMatrix(softmaxResult,"SoftMax");

    }

}
