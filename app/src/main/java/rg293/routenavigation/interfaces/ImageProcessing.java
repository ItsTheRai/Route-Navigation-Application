package rg293.routenavigation.interfaces;///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */

import org.opencv.core.Mat;
import org.opencv.core.Mat;

import rg293.routenavigation.exceptions.ImageSizeError;

/**
 *
 * @author Main User
 */
public interface ImageProcessing {
//    public BufferedImage removeColour(BufferedImage img);
    public Mat removeColour(Mat img);
//    public BufferedImage scaleImage(BufferedImage img, int newWidth, int newHeight,
//                                    boolean keepAspectRation) throws ImageSizeError;
    public Mat scaleImage(Mat img, int newWidth, int newHeight,
                          boolean keepAspectRation);
//    public BufferedImage applyGaussianBlur(BufferedImage img, int kernelWidth, int kernelHeight);
    public Mat applyGaussianBlur(Mat img, int kernelWidth, int kernelHeight);
    public Mat equaliseHist(Mat image);
}
