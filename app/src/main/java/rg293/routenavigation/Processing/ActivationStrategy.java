/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rg293.routenavigation.Processing;

/**
 *
 * @author Raimonds Grismanausks
 */
public interface ActivationStrategy {
    double activate(double weightedSum);
}