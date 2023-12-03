package com.davidsonsw;

import org.ejml.simple.SimpleMatrix;

public class MatrixMain {
    public static void main(String[] args) {

        SimpleMatrix firstMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1d, 5d},
                        new double[] {2d, 3d},
                        new double[] {1d ,7d}
                }
        );

        SimpleMatrix secondMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1d, 2d, 3d, 7d},
                        new double[] {5d, 2d, 8d, 1d}
                }
        );

        System.out.println(firstMatrix.toString());
        System.out.println(secondMatrix.toString());

        SimpleMatrix firstTimesSecond = firstMatrix.mult(secondMatrix);
        System.out.println(firstTimesSecond.toString());
    }

}
