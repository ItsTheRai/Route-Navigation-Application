package rg293.routenavigation.Route;///*
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import Processing.ImageConverter;
//import java.awt.FlowLayout;
//import java.awt.image.BufferedImage;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
import org.opencv.core.Mat;

import rg293.routenavigation.Processing.ImageProcessor;

/**
 *
 * @author Main User
 */
public abstract class Route{
    private final int width;
    private final int height;
//    private ImageConverter imCon;
    private ImageProcessor imProc;

    public Route(int width,int height){
//        imCon = new ImageConverter();
        imProc = new ImageProcessor();
        this.width=width;
        this.height=height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

//    public ImageConverter getImCon() {
//        return imCon;
//    }

    public ImageProcessor getImProc() {
        return imProc;
    }

    //public abstract double[] getBestDirectionAccuracy(PanoramicImage img, int FoV);
    //public abstract int getBestDirection(PanoramicImage img,int FoV);
//    public abstract double getMatchScore(PanoramicImage img, int direction,int FoV);
//    public abstract double calculateCatchmentArea();
//    public abstract double calculateRotationalCatchmentArea();
    public abstract boolean addToRoute(Mat mat);
    public abstract double getMatchScore(Mat img);
//    public abstract boolean addToRoute(BufferedImage mat);
    //public abstract List<BufferedImage> getPerfectMemoryRoute();
    //public abstract List<Mat> getPerfectMemoryRouteDescriptors();
    //public abstract ArrayList<BufferedImage> getRoute();

     //this method return an array of size 360-FoV with the accuracy measure of each
    //degree of the panoramic image
//    public double[] getPanoramaAccuracyArray(PanoramicImage img, int FoV) {
//        //this accuracy shows the best possible accuracy for each deg of rotation
//        double[] IDFarray = new double[360];
//        //for every degree of roattion of the panoramic image
//        for (int i=0;i<IDFarray.length;i++){
//            //call to subclass to calculate image accuracy
//            //for NN this would be 0 for match, 1 for miss
//            //i-180 because the given panoramic image starts from the back
//            //more readable
//            IDFarray[i] = this.getMatchScore(img, i-180, FoV);
////            BufferedImage temp = img.getCurrentView(i-180, FoV);
////            JFrame frame7 = new JFrame();
////            frame7.getContentPane().setLayout(new FlowLayout());
////            frame7.getContentPane().add(new JLabel(new ImageIcon(temp)));
////            frame7.pack();
////            frame7.setVisible(true);
//        }return IDFarray;
//    }

    //for best results FoV should match the actual field of view of the input image
//    public int getBestDirection(PanoramicImage img, int FoV) {
//        int bestDirection = -1;
//        double bestAccuracy=9999999;
//        double[] res = this.getPanoramaAccuracyArray(img, FoV);
//        for(int i=0;i<res.length;i++){
//            if(res[i]<bestAccuracy){
//                bestDirection = i;
//            }
//        }
//        return bestDirection;
//    }

    public int getSize(){
        return 0;
    }


}