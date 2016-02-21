package rg293.routenavigation.Route;///*
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import Processing.ImageConverter;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import rg293.routenavigation.Processing.ImageProcessor;
import rg293.routenavigation.interfaces.PerfectMemoryAccuracyMeasures;

/**
 *
 * @author Raimonds Grismanausks
 */
public class PerfectMemoryRouteMat extends Route implements PerfectMemoryAccuracyMeasures {

    final int type;
    ArrayList<Mat> route;
    private static int counter;
    //private final int FoV
    private ImageProcessor imgProc;
    private static final int catchmentArea=90;
    ImageProcessor imProc;
//    ImageConverter imCon;

    public PerfectMemoryRouteMat(int width,int height,int type,ArrayList<Mat>im) {
        super(width, height);
        this.type = type;
        imProc = new ImageProcessor();
        route = new ArrayList<>();
        for(Mat mat:im){
            addToRoute(mat);
        }
        route = im;
        //this is used to convert bufferedImage to OpenCV Mat format so histogram
        //differences could be calculated
//        imCon = new ImageConverter();
        imProc = new ImageProcessor();
    }

    public Mat getRouteAt(int index){
        return this.route.get(index);
    }

    public void setRouteAt(int index, Mat image){
        //CvType.
        if(image.type()!=this.type){
            System.out.println("the supplied Mat type does not match the route Mat type");
        }
        else this.route.add(index, image);
    }

    @Override
    public boolean addToRoute(Mat image){
        if(image.type()!=this.type||image ==null){
            System.out.println("the supplied Mat type does not matcch the route Mat type");
            return false;
        }
        else if(image.rows()!=this.getHeight()||image.cols()!=this.getWidth()){
            this.route.add(this.imProc.scaleImage(image, this.getWidth(), this.getHeight(), false));
            return true;
        }
        else this.route.add(image);
        return true;
    }

    @Override
    public double getMatchScore(Mat image){
        if (image==null){
            System.out.println("no image given to add to route");
        }
        //check input
        //scale this image to the right size for the route if needed
        if(image.width()!=this.getWidth()||image.height()!=this.getHeight()){
            image = this.imgProc.scaleImage(image, this.getWidth(), this.getHeight(), false);
        }
        if(image.type()== CvType.CV_8UC3||image.type()==CvType.CV_8UC4){
//        convert to CV_8U1
            image = this.getImProc().removeColour(image);
//      convert to CV_32F
            image.convertTo(image, CvType.CV_32F);
        }
        //debug
        if(image.type()!=CvType.CV_32F){
            System.out.println("sonething wrong with converting to 32f! ");
        }
        //check if input is of the correct format
        if (image.width() != image.width() || image.height() != image.height()) {
            image = this.imProc.scaleImage(image, image.width(), image.height(), true);
        }
        //this should be more than the largest output
        double score=999999;
        for (Mat m:this.route){
            double difference=this.getIDF1(image,m);
            if(difference<score){
                score = difference;
            }
        }
        return score;
    }

    private ArrayList<Mat> getRoute(){
        return this.route;
    }

    @Override
    public double getIDF1(Mat im1, Mat im2) {
        return 0;
    }

    @Override
    public double getIDF2(Mat im1, Mat im2) {
        return 0;
    }

    @Override
    public double getIDF3(Mat im1, Mat im2) {
        return 0;
    }
    }