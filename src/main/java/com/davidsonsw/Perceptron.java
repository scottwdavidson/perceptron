package com.davidsonsw;

public class Perceptron {


    static double activation(Neuron neuron) {

        double z = 0.0;
        for (int i = 0; i < neuron.getX().length ; i++ ) {
            z += neuron.getX()[i] * neuron.getW()[i];
        }

//        System.out.println("Neuron: " + neuron.toString());
//        System.out.println("z (before bias): " + z);
        return z + neuron.getBias();
    }

    static double gatedActivation(Neuron neuron) {
        return activation(neuron) > 0 ? 1.0 : 0.0;
    }
}
