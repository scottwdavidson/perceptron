package com.davidsonsw;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Neuron {

    public static Neuron andNeuron(double x0, double x1) {
        return Neuron.builder()
                .x(new double[]{x0, x1})
                .w(new double[]{1, 1})
                .bias(-1).build();
    }
    public static Neuron orNeuron(double x0, double x1) {
        return Neuron.builder()
                .x(new double[]{x0, x1})
                .w(new double[]{1, 1})
                .bias(0).build();
    }

    public static Neuron nandNeuron(double x0, double x1) {
        return Neuron.builder()
                .x(new double[]{x0, x1})
                .w(new double[]{-1, -1})
                .bias(2).build();
    }

    public static Neuron norNeuron(double x0, double x1) {
        return Neuron.builder()
                .x(new double[]{x0, x1})
                .w(new double[]{-1, -1})
                .bias(1).build();
    }
    private double[] x;
    private double[] w;
    private double bias;
}
