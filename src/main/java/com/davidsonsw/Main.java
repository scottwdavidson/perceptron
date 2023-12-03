package com.davidsonsw;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        // AND
        System.out.println(" -- AND -- ");
        double[] x = new double[2];
        for (int i = 0; i < 4; i++) {
            Neuron andNeuron = Neuron.andNeuron(Math.floor(i / 2.0), (double) i % 2);
            displayGatedPerceptron(andNeuron);
        }
        System.out.println(" -------- \n");

        // OR
        System.out.println(" -- OR -- ");
        for (int i = 0; i < 4; i++) {
            Neuron orNeuron = Neuron.orNeuron(Math.floor(i / 2.0), (double) i % 2);
            displayGatedPerceptron(orNeuron);
        }
        System.out.println(" -------- \n");

        // NAND
        System.out.println(" -- NAND -- ");
        for (int i = 0; i < 4; i++) {
            Neuron nandNeuron = Neuron.nandNeuron(Math.floor(i / 2.0), (double) i % 2);
            displayGatedPerceptron(nandNeuron);
        }
        System.out.println(" -------- \n");

        // NOR
        System.out.println(" -- NOR -- ");
        for (int i = 0; i < 4; i++) {
            Neuron norNeuron = Neuron.norNeuron(Math.floor(i / 2.0), (double) i % 2);
            displayGatedPerceptron(norNeuron);
        }
        System.out.println(" -------- \n");

        // XOR
        //
        //      __
        // Y1 = AB
        //
        //      ______
        //          __
        // Y2 = A * AB
        //
        //      ______
        //          __
        // Y3 = B * AB
        //
        //      _______________
        //      ______   ______
        //          __       __
        // Y4 = A * AB * B * AB
        System.out.println(" -- XOR -- ");
        for (int i = 0; i < 4; i++) {
            Neuron neuron1 = Neuron.nandNeuron(Math.floor(i / 2.0), (double) i % 2);
            double result1 = Perceptron.gatedActivation(neuron1);

            Neuron neuron2 = Neuron.nandNeuron(Math.floor(i / 2.0), result1);
            double result2 = Perceptron.gatedActivation(neuron2);

            Neuron neuron3 = Neuron.nandNeuron(result1, (double) i % 2);
            double result3 = Perceptron.gatedActivation(neuron3);

            Neuron neuron4 = Neuron.nandNeuron(result2, result3);
            displayGatedPerceptron(neuron4);
        }
        System.out.println(" -------- \n");

        // XNOR
        //
        // Implemented the same as XOR except you take the result 4 and put it into one
        // more NAND ( to "invert" it )
        System.out.println(" -- XNOR -- ");
        for (int i = 0; i < 4; i++) {
            Neuron neuron1 = Neuron.nandNeuron(Math.floor(i / 2.0), (double) i % 2);
            double result1 = Perceptron.gatedActivation(neuron1);

            Neuron neuron2 = Neuron.nandNeuron(Math.floor(i / 2.0), result1);
            double result2 = Perceptron.gatedActivation(neuron2);

            Neuron neuron3 = Neuron.nandNeuron(result1, (double) i % 2);
            double result3 = Perceptron.gatedActivation(neuron3);

            Neuron neuron4 = Neuron.nandNeuron(result2, result3);
            double result4 = Perceptron.gatedActivation(neuron4);

            Neuron neuron5 = Neuron.nandNeuron(result4, result4);
            displayGatedPerceptron(neuron5);
        }
        System.out.println(" -------- \n");
    }

    private static void displayGatedPerceptron(Neuron neuron) {
        System.out.printf("%d%d\t%d\n", (int) neuron.getX()[0], (int) neuron.getX()[1], (int) Perceptron.gatedActivation(neuron));
    }
}