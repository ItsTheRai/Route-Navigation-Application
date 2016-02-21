package rg293.routenavigation.Route;///*
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import rg293.routenavigation.Processing.ImageProcessor;
import rg293.routenavigation.Processing.infoMax.Layer;
import rg293.routenavigation.Processing.infoMax.NeuralNetwork;
import rg293.routenavigation.Processing.infoMax.Neuron;
import rg293.routenavigation.interfaces.NNaccuracyMeasures;

/**
 *
 * @author Main User
 */
public class NNRoute extends Route implements NNaccuracyMeasures {
    private static int counter;
        NeuralNetwork neuralNet;
        private int outputUnits;
    private final int nHiddenLayers;
//    private ImageConverter imgCon;
    private ImageProcessor imgProc;

        //if there ar only 2 layers we assume that there is only an input and output layer
        public NNRoute(int width, int height,int nLayers,int nHiddenUnits,int sizeHiddenUnits,int nOutputUnits){
        super(width,height);
        counter=0;
        neuralNet = new NeuralNetwork();
        int nInputUnits = width*height;
        this.imgProc = new ImageProcessor();
        //for the basic implementation that andy has proposed set nInputUnits =nNoveltyUnits=nOutputUnits = width*height !!!
        //so, a node for each pixel
        //TODO need to check if differences in accuracy between gray ar rgb/argb images
        this.nHiddenLayers = nHiddenUnits;
        this.outputUnits = nOutputUnits;
        //fill the NN with nodes
        //define size of input layer
        Layer inputLayer = new Layer();
        for (int i=0;i<nInputUnits;i++){
            inputLayer.addNeuron(new Neuron());
        }
        neuralNet.addLayer(inputLayer);
        Layer tempLayer = inputLayer;
        //add hidden layers if they exist
        for(int i=1;i<nLayers-1;i++){
            Layer noveltyLayer = new Layer(tempLayer);
            for (int j=0;j<sizeHiddenUnits;j++){
                noveltyLayer.addNeuron(new Neuron());
            }
            //connect both layers
            tempLayer.setNextLayer(noveltyLayer);
            neuralNet.addLayer(noveltyLayer);
            tempLayer = noveltyLayer;
        }

        ///create last layer
        Layer outputLayer = new Layer(tempLayer);
        for (int i=0;i<nOutputUnits;i++){
            System.out.println(i);
            outputLayer.addNeuron(new Neuron());
        }
        //add last layer
        neuralNet.addLayer(outputLayer);
        tempLayer.setNextLayer(outputLayer);
        //all done, now initialise the network randomly from a uniform distribution in the
//        range {0:5,0:5} and then normalised so that the mean of the
//        weights feeding into each novelty unit is 0 and the standard
//        deviation is 1.
        neuralNet.reset();
    }

    @Override
    public boolean addToRoute(Mat image) {
        if (image == null) {
            System.out.println("no image given to add to route");
            return false;
        }
        if (image.type() == CvType.CV_8UC3 || image.type() == CvType.CV_8UC4) {
//        convert to CV_8U1
            image = this.getImProc().removeColour(image);
//      convert to CV_32F
            image.convertTo(image, CvType.CV_32F);
        }
        if (image.type() != CvType.CV_32F) {
            System.out.println("sonething wrong with converting to 32f! ");
        }
        if (image.width() != this.getWidth() || image.height() != this.getHeight()) {
            System.out.println("image is not of the correct size for this route, rescaling...");
            this.getImProc().scaleImage(image, this.getWidth(), this.getHeight(), false);
        }
        System.out.println("im width" + image.width());
        System.out.println("im height" + image.height());
        //convert image to correct format

        Mat temp = new Mat(1, image.width() * image.height(), image.type());
        System.out.println("temp width" + temp.width());
        System.out.println("temp height" + temp.height());
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                temp.put(0, i * image.rows() + j, image.get(i, j));
//                temp.put(0, i*j, image.get(i, j));
            }
        }
        System.out.println((image.width()));


        System.out.println("temp rows: " + temp.rows());
        System.out.println("temp cols: " + temp.cols());

        System.out.println("temp type: " + temp.type());
        System.out.println(image.type());
        //train NN with the given image
        this.neuralNet.trainNetwork(temp);
        return true;
    }

    //this method converts a NxM matrix into a 1xM*N matrix for the NN to process
    private Mat getDataStream(Mat image){
        if (image==null){
            System.out.println("no image given to add to route");
        }
        //check input
        //scale this image to the right size for the route if needed
        if(image.width()!=this.getWidth()||image.height()!=this.getHeight()){
            image = this.imgProc.scaleImage(image, this.getWidth(), this.getHeight(), false);
        }
        if(image.type()==CvType.CV_8UC3||image.type()==CvType.CV_8UC4){
//        convert to CV_8U1
            image = this.getImProc().removeColour(image);
//      convert to CV_32F
            image.convertTo(image, CvType.CV_32F);
        }
        //debug
        if(image.type()!=CvType.CV_32F){
            System.out.println("sonething wrong with converting to 32f! ");
        }

        //init retur array
        Mat vector = new Mat(1,image.width()*image.height(),image.type());
        for(int i=0;i<image.rows();i++){
            for(int j=0;j<image.cols();j++){
                vector.put(0, i*image.rows()+j, image.get(i, j));
            }
        }
        return vector;
    }

    @Override
    public double getMatchScore(Mat image) {
        if(image.rows()!=1){
            image=this.getDataStream(image);
        }
        return this.neuralNet.testImage(image);
    }
}