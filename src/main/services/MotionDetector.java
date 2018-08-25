package services;

import org.bytedeco.javacv.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.findContours;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MotionDetector
        implements Runnable
{
    public MotionDetector() {}

    public void run()
    {
        try
        {
            motionDetect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void motionDetect() throws Exception {
        long time = 0L;
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(-1);
        grabber.start();
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        Mat frame = converter.convert(grabber.grab());
        Mat image = null;
        Mat prevImage = null;
        Mat diff = null;
        MatVector contour = new MatVector();

        // CanvasFrame canvasFrame = new CanvasFrame("Some Title");
        // canvasFrame.setCanvasSize(frame.rows(), frame.cols());

        while ((frame = converter.convert(grabber.grab())) != null) {
            contour.clear();
            GaussianBlur(frame, frame, new Size(3, 3), 0);
            if (image == null) {
                image = new Mat(frame.rows(), frame.cols(),CV_8UC1);
                cvtColor(frame, image, CV_BGR2GRAY);
            } else {
                prevImage = image;
                image = new Mat(frame.rows(), frame.cols(),CV_8UC1);
                cvtColor(frame, image, CV_BGR2GRAY);
            }
            if (diff == null) {
                diff = new Mat(frame.rows(), frame.cols(),CV_8UC1);
            }
            if (prevImage != null) {
                absdiff(image, prevImage, diff);
                threshold(diff, diff, 20, 255, CV_THRESH_BINARY);

               // canvasFrame.showImage(converter.convert(diff));

                findContours(diff, contour, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
                for(int i=0; i < contour.size(); i++) {
                    long now = System.currentTimeMillis() / 1000L;
                    if (now - time > 30L) {
                        grabber.stop();
                        Thread.sleep(3000L);
                        grabber.start();
                        Still still = new Still(frame);
                        Thread t1 = new Thread(still);
                        t1.start();
                        time = now;
                    }
                }
            }
        }
    }
}

class Still implements Runnable {
    Mat mat;
    public Still(Mat mat){
        this.mat = mat;
    }


    @Override
    public void run(){

        String path = dtf();
        new MailServices().mailSend(path);
        Thread.interrupted();
    }

    public String dtf() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("yyyyMMdd");
        String t = LocalTime.now().format(dtf);
        String d = LocalDate.now().format(day);
        String path = ("/home/pi/camera/" + d + "/" + t + ".jpg");
        File directory = new File(String.valueOf("/home/pi/camera/" + d));
        if (!directory.exists()) {
            directory.mkdir();
        }
        imwrite(path, mat);
        return path;
    }
}



