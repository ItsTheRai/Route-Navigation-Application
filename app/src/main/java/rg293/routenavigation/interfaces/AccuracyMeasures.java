/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rg293.routenavigation.interfaces;

//import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import rg293.routenavigation.exceptions.ImageSizeError;

/**
 *
 * @author Main User
 */
public interface AccuracyMeasures {
    public double getIDF1(Mat im1,Mat im2) throws ImageSizeError;
    public double getIDF2(Mat im1,Mat im2);
    public double getIDF3(Mat im1,Mat im2);
    public double[] getRIDF(Mat im1) throws ImageSizeError;
    public double getImprovementRate(Mat[] im);
    
}