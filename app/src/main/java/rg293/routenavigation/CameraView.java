package rg293.routenavigation;//package rg293.navappnew;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.*;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.features2d.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rg293.routenavigation.Processing.ImageProcessor;
import rg293.routenavigation.Route.NNRoute;
import rg293.routenavigation.Route.Route;
import rg293.routenavigation.exceptions.ImageSizeError;
import rg293.routenavigation.view.PortraitCameraView;


public class CameraView extends Activity
implements CvCameraViewListener {

    private CameraBridgeViewBase openCvCameraView;
    Mat img1;
    Mat img2;
    Mat img3;
    Mat img4;
    Mat img5;
    Mat img6;
    Mat img7;
    Mat img8;
    Mat img9;
    Mat img10;
    FeatureDetector fast;
    ArrayList SIFTlist;
    Mat temp;

    //Vector of keypoints
    MatOfKeyPoint keypoints1;
    MatOfKeyPoint keypoints2;
    MatOfKeyPoint keypoints3;
    MatOfKeyPoint keypoints4;
    MatOfKeyPoint keypoints5;
    MatOfKeyPoint keypoints6;
    MatOfKeyPoint keypoints7;
    MatOfKeyPoint keypoints8;
    MatOfKeyPoint keypoints9;
    MatOfKeyPoint keypoints10;
    //use this for each frame
    MatOfKeyPoint tempPoints;
    DescriptorExtractor descriptor;
    ImageProcessor imProc;
    DescriptorMatcher matcher;
    Mat tempDescriptors;
    ImageProcessor imgProc;
//    private ArrayList<Mat> list;
    private static int counter=0;
    Route route;
    int testWidth = 20;
    int testHeight=20;
    int threshold =10;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status){
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    openCvCameraView.enableView();
                    try {
                        img1=new Mat(3120,4208,CvType.CV_8UC4);
                        img2=new Mat(3120,4208,CvType.CV_8UC4);
                        img3=new Mat(3120,4208,CvType.CV_8UC4);
                        img4=new Mat(3120,4208,CvType.CV_8UC4);
                        img5=new Mat(3120,4208,CvType.CV_8UC4);
                        img6=new Mat(3120,4208,CvType.CV_8UC4);
                        img7=new Mat(3120,4208,CvType.CV_8UC4);
                        img8=new Mat(3120,4208,CvType.CV_8UC4);
                        img9=new Mat(3120,4208,CvType.CV_8UC4);
                        img10=new Mat(3120,4208,CvType.CV_8UC4);
                        keypoints1=new MatOfKeyPoint();
                        keypoints2=new MatOfKeyPoint();
                        keypoints3=new MatOfKeyPoint();
                        keypoints4=new MatOfKeyPoint();
                        keypoints5=new MatOfKeyPoint();
                        keypoints6=new MatOfKeyPoint();
                        keypoints7=new MatOfKeyPoint();
                        keypoints8=new MatOfKeyPoint();
                        keypoints9=new MatOfKeyPoint();
                        keypoints10=new MatOfKeyPoint();
                        tempPoints= new MatOfKeyPoint();


                        img1=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p91, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img2=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p2, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img3=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p3, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img4=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p4, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img5=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p5, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img6=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p6, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img7=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p7, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img8=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p8, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img9=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p9, Highgui.CV_LOAD_IMAGE_UNCHANGED);
//                        img10=Utils.loadResource(getApplicationContext(), R.drawable.portrait_p10, Highgui.CV_LOAD_IMAGE_UNCHANGED);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Create a grayscale image
                    imgProc = new ImageProcessor();
                    Size s =new Size(255,480);
                    Imgproc.resize(img1, img1, s);
                    Imgproc.resize(img2, img2, s);
                    Imgproc.resize(img3, img3, s);
                    Imgproc.resize(img4, img4, s);
                    Imgproc.resize(img5, img5, s);
                    Imgproc.resize(img6, img6, s);
                    Imgproc.resize(img7, img7, s);
                    Imgproc.resize(img8, img8, s);
                    Imgproc.resize(img9, img9, s);
                    Imgproc.resize(img10, img10, s);
                    Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img2, img2, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img3, img3, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img4, img4, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img5, img5, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img6, img6, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img7, img7, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img8, img8, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img9, img9, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.cvtColor(img10, img10, Imgproc.COLOR_BGRA2GRAY);
                    Imgproc.equalizeHist(img1, img1);
                    Imgproc.equalizeHist(img2, img2);
                    Imgproc.equalizeHist(img3, img3);
                    Imgproc.equalizeHist(img4, img4);
                    Imgproc.equalizeHist(img5, img5);
                    Imgproc.equalizeHist(img6, img6);
                    Imgproc.equalizeHist(img7, img7);
                    Imgproc.equalizeHist(img8, img8);
                    Imgproc.equalizeHist(img9, img9);
                    Imgproc.equalizeHist(img10, img10);

                    int width=10 , height = 10;
                    route = new NNRoute(testWidth,testHeight,2,0,0,testWidth*testHeight);
//                    route = new PerfectMemoryRouteMat(testWidth, testHeight,0,new ArrayList<Mat>());
//                    list = new ArrayList<>();
                    route.addToRoute(img1);
                    route.addToRoute(img2);
                    route.addToRoute(img3);
                    route.addToRoute(img4);
                    route.addToRoute(img5);
                    route.addToRoute(img6);
                    route.addToRoute(img7);
                    route.addToRoute(img8);
                    route.addToRoute(img9);
                    route.addToRoute(img10);



//                    SIFTlist=new ArrayList<Mat>();
                    //list.add(img1);list.add(img2);list.add(img3);list.add(img4);list.add(img5);list.add(img6);list.add(img7);list.add(img8);list.add(img9);

                    //do SIFT stuff here
//                    temp = new Mat();
//                    fast = FeatureDetector.create(FeatureDetector.ORB);
//                    descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
//
//                    matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
//                    fast.detect(img1,keypoints1);
//                    fast.detect(img2,keypoints2);
//                    fast.detect(img3,keypoints3);
//                    fast.detect(img4,keypoints4);
//                    fast.detect(img5,keypoints5);
//                    fast.detect(img6,keypoints6);
//                    fast.detect(img7,keypoints7);
//                    fast.detect(img8,keypoints8);
//                    fast.detect(img9,keypoints9);
//                    fast.detect(img10,keypoints10);
//
//
//                    Mat descriptors1=new Mat();
//                    Mat descriptors2=new Mat();
//                    Mat descriptors3=new Mat();
//                    Mat descriptors4=new Mat();
//                    Mat descriptors5=new Mat();
//                    Mat descriptors6=new Mat();
//                    Mat descriptors7=new Mat();
//                    Mat descriptors8=new Mat();
//                    Mat descriptors9=new Mat();
//                    Mat descriptors10=new Mat();
//                    tempDescriptors=new Mat();
//                    descriptor.compute(img1,keypoints1,descriptors1);
//                    descriptor.compute(img2,keypoints2,descriptors2);
//                    descriptor.compute(img3,keypoints3,descriptors3);
//                    descriptor.compute(img4,keypoints4,descriptors4);
//                    descriptor.compute(img5,keypoints5,descriptors5);
//                    descriptor.compute(img6,keypoints6,descriptors6);
//                    descriptor.compute(img7,keypoints7,descriptors7);
//                    descriptor.compute(img8,keypoints8,descriptors8);
//                    descriptor.compute(img9,keypoints9,descriptors9);
//                    descriptor.compute(img9,keypoints10,descriptors10);

//                    SIFTlist.add(descriptors1);
//                    SIFTlist.add(descriptors2);
//                    SIFTlist.add(descriptors3);
//                    SIFTlist.add(descriptors4);
//                    SIFTlist.add(descriptors5);
//                    SIFTlist.add(descriptors6);
//                    SIFTlist.add(descriptors7);
//                    SIFTlist.add(descriptors8);
//                    SIFTlist.add(descriptors9);
//                    SIFTlist.add(descriptors10);


//                    add a method to add everything to a list
//                    r = new PerfectMemoryRouteProcessed(list);
            break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //openCvCameraView = new FaceMaskView(this,-1);/
        //init custom camera view for portrait orientation images
        openCvCameraView = new PortraitCameraView(this, -1);
        setContentView(openCvCameraView);
        openCvCameraView.setCvCameraViewListener(this);
        imProc = new ImageProcessor();
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
    }


    @Override
    public void onCameraViewStopped() {
    }

    //this is where all the magic hapens
    @Override
    public Mat onCameraFrame(Mat frame) {
        //check if input is present
        if(frame==null){
            System.out.println("no image in input frame");
        }
        //make a copy of the input frame for processing
        Mat clone = frame.clone();
        //check dimensions and adjust if necessary
        if(clone.width()!=this.route.getWidth()||clone.height()!=this.route.getHeight()) {
            this.imgProc.scaleImage(clone, this.route.getWidth(), this.route.getHeight(), false);
        }
        //convert to CV_8U
        this.imgProc.removeColour(clone);
        //check score compared to the threshold, draw square
            if(route.getMatchScore(clone)<threshold) {//found a match
                //draw green square
                Core.rectangle(frame, new Point(500, 800), new Point(800, 1100), new Scalar(0, 255, 0, 255), 20);
            }
            //draw red square
            else Core.rectangle(frame, new Point(500, 800), new Point(800, 1100), new Scalar(255, 0, 0, 255), 5);
            temp.release();
        return frame;
    }

//    public Mat imagePreProcess(Mat aInputFrame) {
//        //create a clone image to process
//        //Mat temp = aInputFrame.clone();
//        // Create a grayscale image
//        Imgproc.cvtColor(aInputFrame, aInputFrame, Imgproc.COLOR_BGRA2GRAY);
//        //perform histogram equalization to get more contrast for out RGBs
//        //since RGB convert to 2 channel data so our DB route needs to be in the same format as well
//        //dont know how to do it yet
//        Imgproc.equalizeHist(aInputFrame, aInputFrame);
//        //read route from database
//        //for now use a set route to get stuff rolling
//
//        ImageProcessor p = new ImageProcessor();
//        aInputFrame = p.scaleImage(aInputFrame, 255, 480, false);
//        return aInputFrame;
//    }


    public Mat useNN(Mat aInputFrame){

        //TODO all of this stuff
        //init NN
//        CvANN_MLP neuralNet = new CvANN_MLP();
        //create dimension metrix
//        Mat mat = new Mat();
//        mat.create(1,3,0);
//        mat.put(0,0,40000);
//        mat.put(0,1,40000);
//        mat.put(0,2,1);
//        //create NN
//        neuralNet.create(mat);

        return null;
    }

    public Mat useSIFT(Mat aInputFrame) throws ImageSizeError {
        if(counter++%50==0){
            System.out.println();
        }
        //create a temp mat image to use by procewsing and feature detection methods

        //pre-porcess image
//        temp=imagePreProcess(temp);
        //for now just convert all database images to (SIFT),
        //if i choose this approach, they will be stored in db as keypoint vectors, not Mats
        fast.detect(temp,tempPoints);
        descriptor.compute(temp,tempPoints,tempDescriptors);

        int minMatches=Integer.MAX_VALUE;
        float bestDistance = 55555;
        if(tempDescriptors.empty()){

        }
        else {
        for (int i = 0; i < SIFTlist.size(); i++) {
            float distance=0;
            int tempDiff = 0;
            //math descriptors
            MatOfDMatch matches = new MatOfDMatch();

//            System.out.println(tempDescriptors)
//            System.out.println(SIFTlist.get(i));

                    matcher.match(tempDescriptors, (Mat) SIFTlist.get(i), matches);


            double max_dist = 0; double min_dist = 100;

            //-- Quick calculation of max and min distances between keypoints
            for( int k = 0; k < tempDescriptors.rows(); k++ ){
                double dist = matches.toArray()[k].distance;
                if( dist < min_dist ) min_dist = dist;
                if( dist > max_dist ) max_dist = dist;
            }
            //-- Draw only "good" matches (i.e. whose distance is less than 2*min_dist,
            //-- or a small arbitary value ( 0.02 ) in the event that min_dist is very
            //-- small)
            //-- PS.- radiusMatch can also be used here.
            List<DMatch > good_matches;

//            for( int k = 0; k < tempDescriptors.rows(); k++ )
//            { if( matches.toArray()[k].distance <= Math.max(2*min_dist, 0.02) )
//            {
//                good_matches.add( matches.toArray()[k]); }
//            }

            //-- Draw only "good" matches
            Mat img_matches;
//            drawMatches( img_1, keypoints_1, img_2, keypoints_2,
//                    good_matches, img_matches, Scalar::all(-1), Scalar::all(-1),
//                    vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS );

            //-- Show detected matches
//            imshow( "Good Matches", img_matches );



                    for (DMatch m : matches.toList()) {
//
                        distance += m.distance;
                    }
                    distance=distance/matches.toList().size();
                    if (distance<1){
                    }
                    Log.d("mathces", "size " + distance);
//                    only allow if there is sufficient amount of matches in the first place
//                    this may not work depending on what ive done wrong
                    if (distance < bestDistance &&matches.toList().size()>450){//((Mat) SIFTlist.get(i)).rows()/2){
                        bestDistance = distance;
                    }
                }
        }
            //need to find a way to count number of matches and compare them to a threshold to consider them
            if(bestDistance<65){

                Core.rectangle(aInputFrame, new Point(500, 800), new Point(800, 1100), new Scalar(0, 255, 0, 255), 20);
            }
            else Core.rectangle(aInputFrame, new Point(500, 800), new Point(800, 1100), new Scalar(255, 0, 0, 255), 5);
        temp.release();
    return aInputFrame;

    }

    public Mat useIDF1(Mat aInputFrame){
        //create a clone image to process
        Mat temp = aInputFrame.clone();
        // Create a grayscale image
        Imgproc.cvtColor(temp, temp, Imgproc.COLOR_BGRA2GRAY);
        //perform histogram equalization to get more contrast for out RGBs
        //since RGB convert to 2 channel data so our DB route needs to be in the same format as well
        //dont know how to do it yet
        Imgproc.equalizeHist(temp, temp);
        //read route from database
        //for now use a set route to get stuff rolling

//        ImagePreprocessor p = new ImagePreprocessor();
        System.out.println(temp.width() + "  " + temp.height());
        //playing around with how may frames need to be used to get good accuracy
        if(CameraView.counter++%1==0) {
            double diff = 2000;
//            only my first test image has 4 channels, the other 2 have 3, so when doing an image diff
//            an exception is thrown, pretty annoying
            //need to find a way to only allow routes with 4 channel images,
            //not a problem with NNs
//            for (int i = 0; i < list.size(); i++) {
//                double tempDiff = 0;
//                    tempDiff = ((PerfectMemoryRouteMat)route).getIDF1(temp, (Mat) list.get(i));
//                if (tempDiff < diff) {
//                    diff = tempDiff;
//                }
//            }

//            System.out.println(diff);


            // MatOfRect faces = new MatOfRect();
//
//        Use the classifier to detect faces
            //if (cascadeClassifier != null) {
//                cascadeClassifier.detectMultiScale(grayscaleImage, faces, 1.1, 2, 2,
//                        new Size(absoluteFaceSize, absoluteFaceSize), new Size());
//            }


            // If there are any faces found, draw a rectangle around it

            //display a red shape of threshold not met, a green one if  is met

            if (diff < 7) {
//                Rect[] facesArray = faces.toArray();
//                for (int i = 0; i <facesArray.length; i++)
//                    Core.rectangle(aInputFrame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
                Core.rectangle(aInputFrame, new Point(300, 300), new Point(500, 500), new Scalar(0, 255, 0, 255), 10);
            }
            else {
                Core.rectangle(aInputFrame, new Point(300, 300), new Point(500, 500), new Scalar(255, 0, 0, 255), 5);
            }
        }
        temp.release();
        return aInputFrame;
    }


    @Override
    public void onResume() {
        super.onResume();

        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
    }
}