package com.davidsonsw;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.ejml.data.DMatrixRMaj;

import java.util.Random;

@Builder
@ToString
@Getter
public class NeuronLayer {

    private final int numberOfInputs;
    private final int numberOfNeurons;
    private final DMatrixRMaj weights;
    private final DMatrixRMaj biases;

    public static NeuronLayer randomNeuronLayer(int numberOfInputs, int numberOfNeurons) {
        Random random = new Random();

        double[][] weights = new double[numberOfInputs][numberOfNeurons];
        for (int input = 0; input < numberOfInputs; input++) {
            for (int neuron = 0; neuron < numberOfNeurons; neuron++) {
                weights[input][neuron] = random.nextGaussian();
            }
        }

        double[][] biases = new double[numberOfInputs][numberOfNeurons];
        for (int neuron = 0; neuron < numberOfNeurons; neuron++) {
            double nextGaussian = random.nextGaussian();
            for (int input = 0; input < numberOfInputs; input++) {
                biases[input][neuron] = nextGaussian;
            }
        }


        NeuronLayer nl = NeuronLayer.builder()
                .numberOfNeurons(4)
                .weights(new DMatrixRMaj(weights))
                .biases(new DMatrixRMaj(biases))
                .build();
        return nl;
    }
}