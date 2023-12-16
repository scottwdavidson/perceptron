package com.davidsonsw;

import com.davidsonsw.matrix.MatrixTransform;
import com.davidsonsw.neuralnet.MatrixFacade;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.ejml.data.DMatrixRMaj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Builder
@ToString
@Getter
public class NeuronLayer {

    private final int numberOfInputs;
    private final int numberOfNeurons;
    private final DMatrixRMaj weights;
    private final DMatrixRMaj biases;
    private final List<Transform> transforms = new ArrayList<>();

    public DMatrixRMaj applyTransforms(DMatrixRMaj inputs){

        // 1st must be DENSE
        if ( transforms.get(0).compareTo(Transform.DENSE) != 0 ){
            throw new IllegalStateException("1st transform must be DENSE otherwise there's nothing to work on ... ");
        }

        MatrixFacade.InputsFacade inputsFacade = MatrixFacade.InputsFacade.newInputsFacade(inputs);

        if (inputsFacade.numberOfInputs() != this.numberOfInputs){
            String message = "Input dimension mismatch: inputs is of dimension (" +
                    inputs.getNumRows() + "," + inputs.getNumCols() + ") but was expected to be (" +
                    this.numberOfInputs + ", <num datasets>)";
            throw new IllegalArgumentException(message);
        }

        DMatrixRMaj result = inputs;
        for (Transform transform: this.transforms){
            if (Transform.DENSE == transform){
                result = MatrixTransform.applyDensityTransform(inputs, this.weights, this.biases);
            }
            else if(Transform.RELU == transform){
                result = MatrixTransform.applyReLUTransform(result);
            }
            else if(Transform.SOFTMAX == transform){
                result = MatrixTransform.applySoftMaxTransform(result);
            }
            else {
                throw new IllegalStateException("Unrecognized transform: <" + transform + ">");
            }
        }

        return result;
    }

    public static NeuronLayer randomDenseNeuronLayer(int numberOfInputs, int numberOfNeurons) {
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


        NeuronLayer neuronLayer = NeuronLayer.builder()
                .numberOfNeurons(4)
                .weights(new DMatrixRMaj(weights))
                .biases(new DMatrixRMaj(biases))
                .build();
        neuronLayer.getTransforms().add(Transform.DENSE);
        return neuronLayer;
    }
}