package rg293.routenavigation.Route;///*
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import java.awt.FlowLayout;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DMatch;

import rg293.routenavigation.Processing.ImageProcessor;

/**
 *
 * @author Raimonds Grismanausks
 */
public class PerfectMemoryRouteMatKeypoints extends PerfectMemoryRouteMat{

    ImageProcessor imProc;
    public PerfectMemoryRouteMatKeypoints(int width, int height, int type, ArrayList<Mat> im) {
        super(width, height, type, im);
        imProc = new ImageProcessor();

    }

//    @Override
//    public double getMatchScore(PanoramicImage img, int direction, int FoV) {
//        //first need to extract descriptors from panoramic image
//        //get view
////        JFrame frame1 = new JFrame();
////            frame1.getContentPane().setLayout(new FlowLayout());
////            frame1.getContentPane().add(new JLabel(new ImageIcon(img.getImage())));
////            frame1.pack();
////            frame1.setVisible(true);
//
////        JFrame frame = new JFrame();
////        frame.getContentPane().setLayout(new FlowLayout());
////        frame.getContentPane().add(new JLabel(new ImageIcon(temp)));
////        frame.pack();
////        frame.setVisible(true);
////
////        JFrame frame1 = new JFrame();
////        frame1.getContentPane().setLayout(new FlowLayout());
////        BufferedImage imgg=null;
////        try {
////            imgg = ImageIO.read(new File("images/indoorsRoute/panoramaTest/route/portrait_p1.png"));
////        } catch (IOException ex) {
////            Logger.getLogger(PerfectMemoryRouteMat.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        try {
////            imgg=this.imProc.scaleImage(imgg,500,500 , false);
////        } catch (ImageSizeError ex) {
////            Logger.getLogger(PerfectMemoryRouteMat.class.getName()).log(Level.SEVERE, null, ex);
////        }
////            frame1.getContentPane().add(new JLabel(new ImageIcon(imgg)));
////        frame1.pack();
////        frame1.setVisible(true);
//        BufferedImage currentView = img.getCurrentView(direction, FoV);
////        JFrame frame3 = new JFrame();
////        frame3.getContentPane().setLayout(new FlowLayout());
////        frame3.getContentPane().add(new JLabel(new ImageIcon(currentView)));//this.imgc.convertMatToBufferedImage(temp1))));
////        frame3.pack();
////        frame3.setVisible(true);
//        Mat image = this.imCon.convertBufferedImageToMat(currentView);
////        JFrame frame4 = new JFrame();
////        frame4.getContentPane().setLayout(new FlowLayout());
////        try {
////            frame4.getContentPane().add(new JLabel(new ImageIcon(this.imCon.convertMatToBufferedImage(image))));//this.imgc.convertMatToBufferedImage(temp1))));
////        } catch (Exception ex) {
////            Logger.getLogger(PerfectMemoryRouteMatKeypoints.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        frame4.pack();
////        frame4.setVisible(true);
//
//        Mat descriptors = this.imCon.convertMatToDescriptors(image);
////        System.out.println("rows 1 :"+descriptors.rows());
////        System.out.println("colst 1 :"+descriptors.cols());
////        System.out.println("rows 2 :"+this.route.get(0).rows());
////        System.out.println("colst 2 :"+this.route.get(0).cols());
////        System.out.println(image.type());
////        System.out.println(this.route.get(0).type());
//        //get matches
//        double tempDouble = 9999999;
//            //for every snapshot in the route
//            for (Mat comp:this.route){
////                if(image.width()!=comp.width()||image.height()!=comp.height()){
////                    image=this.imProc.scaleImage(image,comp.width(), comp.height(), true);
////                    System.out.println(image.rows());
////                    System.out.println(image.cols());
////                    System.out.println(comp.rows());
////                    System.out.println(comp.cols());
////                }
////                System.out.println("image cols: "+descriptors.rows());
////                System.out.println("image cols: "+descriptors.cols());
////                System.out.println(comp.type());
////                System.out.println(descriptors.type());
////                System.out.println(comp.cols());
////                System.out.println(descriptors.cols());
//                //here uses IDF1 but could use 2 or another one if created
//                double tempValue =  getMatch(descriptors,comp);
//                if(tempValue<tempDouble){
//                    tempDouble=tempValue;
//                }
//                image.release();
//            }
//            return tempDouble;
//    }

    //both mats must be descriptors of type sometype not sure
    @Override
    public double getMatchScore(Mat image) {
        //convert everything to mat matches
        System.out.println("/////-------new comparison------///////");
//        System.out.println(currentView.type());
//        System.out.println(image.type());
//        System.out.println(image.width());
//        System.out.println(image.height());
//        System.out.println(currentView.width());
//        System.out.println(currentView.height());

//    CvType.
//        System.out.println("---------------matched cols: "+matches.rows()+"-------------------");
//        System.out.println("---------------matched cols: "+matches.cols()+"-------------------");
//        System.out.println(matches.toList().size());
//        double max_dist = 0; double min_dist = 100;
        //-- Quick calculation of max and min distances between keypoints
//        for( int k = 0; k < currentView.rows(); k++ ){
//            double dist = matches.toArray()[k].distance;
//            if( dist < min_dist ) min_dist = dist;
//            if( dist > max_dist ) max_dist = dist;
//        }
//        Mat img_matches;

        //check if image is the correct size
        if (image.width() != image.width() || image.height() != image.height()) {
            image = this.imProc.scaleImage(image, image.width(), image.height(), true);
        }
        double result = 9999999;
            //for every snapshot in the route
            for (Mat img:this.route) {
                //get matches
                MatOfDMatch matches = new MatOfDMatch();
                this.imProc.getMatcher().match(img, image, matches);
                double distance=0;
                //calculate distance
                for (DMatch m : matches.toList()) {
                    distance += m.distance;
                }
                //average the distance
                distance=distance/matches.toList().size();
                //check if better match than previous one
                if(distance<result){
                    result=distance;
                }
            }
        return result;
    }
    @Override
    public boolean addToRoute(Mat image){
        if(image.type()!=this.type){
            System.out.println("the supplied Mat type does not matcch the route Mat type");
            return false;
        }
        else this.route.add(image);
        return true;
    }


    @Override
    public void setRouteAt(int index,Mat image){
        if(image.type()!=this.type){
            System.out.println("the supplied Mat type does not match the route Mat type");
        }
        else this.route.add(index, image);
    }

    @Override
    public Mat getRouteAt(int index){
        return this.route.get(index);
    }

    @Override
    public int getSize(){
        return this.route.size();
    }
}
