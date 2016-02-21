/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rg293.routenavigation.interfaces;

//import org.opencv.core.Mat;


import org.opencv.core.Mat;

/**
 *
 * @author Main User
 */
public interface PerfectMemoryAccuracyMeasures {
    public double getIDF1(Mat im1, Mat im2);
    public double getIDF2(Mat im1, Mat im2);
    public double getIDF3(Mat im1, Mat im2);
//    public double[] getRIDF(BufferedImage im1, int FoV);
//    public double getImprovementRate(BufferedImage[] im);
    
}