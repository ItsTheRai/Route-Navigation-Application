package rg293.routenavigation.Processing.infoMax;


import java.util.ArrayList;
import java.util.List;

import rg293.routenavigation.Processing.ActivationStrategy;

public class Neuron {

    private List<Synapse> inputs;
    private ActivationStrategy activationStrategy;
    private double output;
    private double derivative;
    private double weightedSum;
    private double error;

    public Neuron() {
        inputs = new ArrayList<>();
//        this.activationStrategy = activationStrategy;
        error = 0;
    }

    public void addInput(Synapse input) {
        inputs.add(input);
    }

    public List<Synapse> getInputs() {
        return this.inputs;
    }

    public double[] getWeights() {
        double[] weights = new double[inputs.size()];

        int i = 0;
        for(Synapse synapse : inputs) {
            weights[i] = synapse.getWeight();
            i++;
        }

        return weights;
    }

    public void calculateWeightedSum() {
        weightedSum = 0;
        for (Synapse synapse:inputs){
//        inputs.stream().forEach((synapse) -> {
            weightedSum += synapse.getWeight() * synapse.getSourceNeuron().getOutput();
        }
    }

    public double getWeightedSum(){
        weightedSum = 0;
        for(Synapse synapse : inputs) {
            weightedSum += synapse.getWeight() * synapse.getSourceNeuron().getOutput();
        }
        return weightedSum;
    }

    public double getWeightedWeightedSum(){
        double input = 0;
        for(Synapse synapse : inputs) {
            input += synapse.getWeight() * synapse.getWeight() * synapse.getSourceNeuron().getOutput();
        }
        return input;
    }



    public void activate() {

        calculateWeightedSum();
        output = Math.tanh(weightedSum);
//        output = activationStrategy.activate(weightedSum);
//        derivative = activationStrategy.derivative(output);
    }

    public double getOutput() {
        return this.output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getDerivative() {
        return this.derivative;
    }

    public ActivationStrategy getActivationStrategy() {
        return activationStrategy;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}