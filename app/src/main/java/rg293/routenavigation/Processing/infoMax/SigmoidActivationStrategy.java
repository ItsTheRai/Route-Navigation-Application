/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rg293.routenavigation.Processing.infoMax;

import java.io.Serializable;

import rg293.routenavigation.Processing.ActivationStrategy;

/**
 *
 * @author Raimonds Grismanausks
 */
//class not used
public class SigmoidActivationStrategy implements ActivationStrategy, Serializable {

    public double activate(double weightedSum) {
        return 1.0 / (1 + Math.exp(-1.0 * weightedSum));
    }

    public double derivative(double weightedSum) {
        return weightedSum * (1.0 - weightedSum);
    }

    public SigmoidActivationStrategy copy() {
        return new SigmoidActivationStrategy();
    }
}