package com.davidsonsw.neuralnet;

import org.ejml.data.DMatrixRMaj;

public class MatrixFacade {

    public interface NeuronDataElement {
        int numberOfInputs();
        int numberOfDatasets();
        int numberOfNeurons();
    }

    public static class InputsFacade implements NeuronDataElement {

        private final DMatrixRMaj matrix;

        public static InputsFacade newInputsFacade(DMatrixRMaj inputMatrix){
            return new InputsFacade(inputMatrix);
        }

        public InputsFacade(DMatrixRMaj matrix){
            this.matrix = matrix;
        }

        /**
         * DON'T USE - ENSURES IMMUTABILITY
         */
        private InputsFacade(){
            this.matrix = null;
        }

        @Override
        public int numberOfInputs() {
            return this.matrix.getNumCols();
        }

        @Override
        public int numberOfDatasets() {
            return this.matrix.getNumRows();
        }

        @Override
        public int numberOfNeurons() {
            throw new IllegalArgumentException("Inputs Neuron Data Elements are (num inputs) x (num datasets)");
        }
    }

    public static class WeightsFacade implements NeuronDataElement {

        private final DMatrixRMaj matrix;

        public static WeightsFacade newWeightsFacade(DMatrixRMaj inputMatrix){
            return new WeightsFacade(inputMatrix);
        }

        public WeightsFacade(DMatrixRMaj matrix){
            this.matrix = matrix;
        }

        /**
         * DON'T USE - ENSURES IMMUTABILITY
         */
        private WeightsFacade(){
            this.matrix = null;
        }

        @Override
        public int numberOfInputs() {
            return this.matrix.getNumCols();
        }

        @Override
        public int numberOfDatasets() {
            throw new IllegalArgumentException("Weights Neuron Data Elements are (num neurons) x (num inputs)");
        }

        @Override
        public int numberOfNeurons() {
            return this.matrix.getNumRows();
        }
    }

    public static class BiasesFacade implements NeuronDataElement {

        private final DMatrixRMaj matrix;

        public static BiasesFacade newWeightsFacade(DMatrixRMaj inputMatrix){
            return new BiasesFacade(inputMatrix);
        }

        public BiasesFacade(DMatrixRMaj matrix){
            this.matrix = matrix;
        }

        /**
         * DON'T USE - ENSURES IMMUTABILITY
         */
        private BiasesFacade(){
            this.matrix = null;
        }

        @Override
        public int numberOfInputs() {
            throw new IllegalArgumentException("Weights Neuron Data Elements are (num neurons) x (num datasets)");
        }

        @Override
        public int numberOfDatasets() {
            return this.matrix.getNumCols();
        }

        @Override
        public int numberOfNeurons() {
            return this.matrix.getNumRows();
        }
    }
}
