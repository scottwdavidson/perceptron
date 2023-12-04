package com.davidsonsw;

import org.ejml.simple.SimpleMatrix;

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
 *
 * </p>
 */
public class ReLUMain {

    private final static Random RANDOM = new Random();
    private final static int NUMBER_OF_INPUTS = 4;
    private final static int NUMBER_OF_NEURONS = 5;
    private final static int NUMBER_OF_DATASETS = 6;
    private static final SimpleMatrix INPUTS;
    private static final SimpleMatrix WEIGHTS;
    private static final SimpleMatrix BIASES;

    // initialize matrices with random data
    static {

        // inputs ( NUMBER_OF_DATASETS x NUMBER_OF_INPUTS )
        double[][] inputs = new double[NUMBER_OF_DATASETS][NUMBER_OF_INPUTS];
        for (int dataset = 0; dataset < NUMBER_OF_DATASETS; dataset++) {
            for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
                inputs[dataset][input] = RANDOM.nextDouble();
            }
        }
        INPUTS = new SimpleMatrix(inputs);

        // weights ( NUMBER_OF_INPUTS x NUMBER_OF_NEURONS)
        double[][] weights = new double[NUMBER_OF_INPUTS][NUMBER_OF_NEURONS];
        for (int input = 0; input < NUMBER_OF_INPUTS; input++) {
            for (int neuron = 0; neuron < NUMBER_OF_NEURONS; neuron++) {
                weights[input][neuron] = RANDOM.nextGaussian();
            }
        }
        WEIGHTS = new SimpleMatrix(weights);

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
        BIASES = new SimpleMatrix(biases);
    }

    public static void main(String[] args) {
//        System.out.println(INPUTS);
//        System.out.println(WEIGHTS);
//        System.out.println(BIASES);

        SimpleMatrix result = INPUTS.mult(WEIGHTS);
        System.out.println(result);
        System.out.println(BIASES);
        result = result.plus(1, BIASES);
        System.out.println(result);

    }

}
