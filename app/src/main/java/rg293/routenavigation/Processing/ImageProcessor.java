package rg293.routenavigation.Processing;///*
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import Route.PanoramicImage;
//import Route.PerfectMemoryRouteBufferedImage;
//import java.awt.FlowLayout;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.image.BufferedImage;
//import java.awt.image.DataBufferByte;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

import rg293.routenavigation.interfaces.ImageProcessing;

/**
 *
 * @author Raimonds Grismanausks
 */
public class ImageProcessor implements ImageProcessing {
//    private ImageConverter con;
    FeatureDetector fast;
    DescriptorExtractor descriptor;
    DescriptorMatcher matcher;
    MatOfKeyPoint keypoints;

    public ImageProcessor() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        con = new ImageConverter();
        fast = FeatureDetector.create(FeatureDetector.ORB);
        descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        keypoints=new MatOfKeyPoint();
    }


    @Override
    public Mat removeColour(Mat img){
//        System.out.println(img.channels());
//        System.out.println("----------------------------------------");
        if(img.channels()!=1){
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
        }
        return img;
    }


    @Override
    public Mat applyGaussianBlur(Mat im, int kernelWidth, int kernelHeight){
        Imgproc.GaussianBlur(im, im, new Size(kernelWidth,kernelHeight),0);
        return im;
    }


    @Override
    public Mat scaleImage(Mat img, int newWidth, int newHeight, boolean keepAspectRatio){
//        if (img.type()==CvType.CV_8UC4){
            float scale=(float)newWidth/(float)img.width();
        if(keepAspectRatio){
            Imgproc.resize(img, img, new Size(newWidth,img.height()*scale));
        }
        else Imgproc.resize(img, img, new Size(newWidth,newHeight));

        byte[]data = new byte[img.rows()*img.cols()*4];
        return img;
    }

    public Mat convertMatToDescriptors(Mat image){
        //use orb feature extraction supported by OpenCV
        ImageProcessor imgp = new ImageProcessor();
        fast.detect(image,keypoints);
        Mat descriptors=new Mat();
        descriptor.compute(image,keypoints,descriptors);
        keypoints.release();
        return descriptors;
    }

    @Override
    public Mat equaliseHist(Mat image){
        Imgproc.equalizeHist(image, image);
        return image;
    }

    public FeatureDetector getFast() {
        return fast;
    }

    public DescriptorExtractor getDescriptor() {
        return descriptor;
    }

    public DescriptorMatcher getMatcher() {
        return matcher;
    }

    public MatOfKeyPoint getKeypoints() {
        return keypoints;
    }
}