package rg293.routenavigation.Processing.infoMax;///*
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raimonds Grismanausks
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.opencv.core.Mat;
//import org.jfree.ui.Layer;

public class NeuralNetwork {

//    private String name;
    private final List<Layer> layers;
    private Layer input;
    private Layer novelty;

    public NeuralNetwork() {
//        this.name = name;
        layers = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
        layers.add(layer);

        if(layers.size() == 1) {
            input = layer;
        }

        if(layers.size() > 1) {
            //clear the output flag on the previous output layer, but only if we have more than 1 layer
            Layer previousLayer = layers.get(layers.size() - 2);
            previousLayer.setNextLayer(layer);
        }

        novelty = layers.get(layers.size() - 1);
    }


    public boolean trainNetwork(Mat inputVector){
        System.out.println(inputVector.cols());
        System.out.println(inputVector.type());
//        for(Neuron n:this.getLayers())
        double n = 0.01;//learning rate
        //init values if not done
        //map mat vector to input layer
        for (int i=0;i<inputVector.cols();i++){
            this.input.getNeurons().get(i).setOutput(inputVector.get(0, i)[0]);
        }


//        layers.get(0).feedForward();

        for(int i = 1; i < layers.size(); i++) {
            int c=0;
            for(Neuron neuron : layers.get(i).getNeurons()){//neuron i
                double[] weights = neuron.getWeights();
                for (int j=0;j<weights.length;j++){
                    weights[j]=                             //weight between neuron i and neuron j
                    (n/inputVector.cols())*
                    (weights[j]-
                    (neuron.getOutput()+neuron.getWeightedSum())
                            )* neuron.getWeightedWeightedSum();
                }
                System.out.println("counting "+c++);
            }
        }
        return true;
    }

    public double testImage(Mat inputVector){
        double result=0;
        System.out.println(inputVector.cols());
        System.out.println(inputVector.type());
        //put input layer in neural network
        for (int i=0;i<inputVector.cols();i++){
            this.input.getNeurons().get(i).setOutput(inputVector.get(0, i)[0]);
        }
        this.layers.get(this.layers.size()-1).feedForward();
        //calculate score
        for(int i = 1; i < layers.size(); i++) {
            for(Neuron neuron : layers.get(i).getNeurons()){
                result+=Math.abs(neuron.getWeightedSum());
            }
        }
        return result;
    }

    public void setInputs(double[] inputs) {
        if(input != null) {
            int biasCount = input.hasBias() ? 1 : 0;

            if(input.getNeurons().size() - biasCount != inputs.length) {
                throw new IllegalArgumentException("The number of inputs must equal the number of neurons in the input layer");
            }

            else {
                List<Neuron> neurons = input.getNeurons();
                for(int i = biasCount; i < neurons.size(); i++) {
                    neurons.get(i).setOutput(inputs[i - biasCount]);
                }
            }
        }
    }

    public double[] getOutput() {

        double[] outputs = new double[novelty.getNeurons().size()];

        for(int i = 1; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            layer.feedForward();
        }

        int i = 0;
        for(Neuron neuron : novelty.getNeurons()) {
            outputs[i] = neuron.getOutput();
            i++;
        }

        return outputs;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void reset() {
        int c=0;
        //reset data
        for(Layer layer : layers) {
            for(Neuron neuron : layer.getNeurons()) {
                for(Synapse synapse : neuron.getInputs()) {
                    synapse.setWeight((Math.random() * 1) - 0.5);
                }
            }
        }

        //normalist data
        for(Layer layer : layers) {
            for(Neuron neuron : layer.getNeurons()) {
                double [] weights = new double[neuron.getInputs().size()];
                for (int i=0;i<neuron.getInputs().size();i++){
                    weights[i]=neuron.getInputs().get(i).getWeight();
                }
                double mean = this.getMean(weights);
                double std = this.getStandartDeviation(weights);
                for (int i=0;i<weights.length;i++){
                    neuron.getInputs().get(i).setWeight((weights[i]-mean)*1/std);
                }
                System.out.println("reset counter"+c++);
            }
        }
    }

    public double getMean(double[] array){
        double mean=0;
        for (int  i=0;i<array.length;i++){
            mean +=array[i];
        }
        return mean/array.length;
    }

    public double getStandartDeviation(double[] numbers){
        double mean = this.getMean(numbers);
//        double[] deviations = new double[array.length];
        double sd = 0;
        for (int i = 0; i < numbers.length; i++)
        {
            sd += Math.pow((numbers[i] - mean) , 2) / numbers.length;
        }
        double standardDeviation = Math.sqrt(sd);
        return standardDeviation;
    }

    public double[] getWeights() {

        List<Double> weights = new ArrayList<>();

        for(Layer layer : layers) {

            for(Neuron neuron : layer.getNeurons()) {

                for(Synapse synapse: neuron.getInputs()) {
                    weights.add(synapse.getWeight());
                }
            }
        }

        double[] allWeights = new double[weights.size()];

        int i = 0;
        for(Double weight : weights) {
            allWeights[i] = weight;
            i++;
        }
        return allWeights;
    }

    public void copyWeightsFrom(NeuralNetwork sourceNeuralNetwork) {
        if(layers.size() != sourceNeuralNetwork.layers.size()) {
            throw new IllegalArgumentException("Cannot copy weights. Number of layers do not match (" + sourceNeuralNetwork.layers.size() + " in source versus " + layers.size() + " in destination)");
        }

        int i = 0;
        for(Layer sourceLayer : sourceNeuralNetwork.layers) {
            Layer destinationLayer = layers.get(i);

            if(destinationLayer.getNeurons().size() != sourceLayer.getNeurons().size()) {
                throw new IllegalArgumentException("Number of neurons do not match in layer " + (i + 1) + "(" + sourceLayer.getNeurons().size() + " in source versus " + destinationLayer.getNeurons().size() + " in destination)");
            }

            int j = 0;
            for(Neuron sourceNeuron : sourceLayer.getNeurons()) {
                Neuron destinationNeuron = destinationLayer.getNeurons().get(j);

                if(destinationNeuron.getInputs().size() != sourceNeuron.getInputs().size()) {
                    throw new IllegalArgumentException("Number of inputs to neuron " + (j + 1) + " in layer " + (i + 1) + " do not match (" + sourceNeuron.getInputs().size() + " in source versus " + destinationNeuron.getInputs().size() + " in destination)");
                }

                int k = 0;
                for(Synapse sourceSynapse : sourceNeuron.getInputs()) {
                    Synapse destinationSynapse = destinationNeuron.getInputs().get(k);

                    destinationSynapse.setWeight(sourceSynapse.getWeight());
                    k++;
                }

                j++;
            }

            i++;
        }
    }
}