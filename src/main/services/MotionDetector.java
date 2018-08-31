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

    private static Mat pic = null;

    public static Mat getPic() {
        return pic;
    }

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
            GaussianBlur(frame, frame, new Size(3, 3), 0);
            pic = frame;
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
                threshold(diff, diff, 45, 255, CV_THRESH_BINARY);

               // canvasFrame.showImage(converter.convert(diff));

                findContours(diff, contour, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
                if(contour.size()>0 && (System.currentTimeMillis() / 1000)-time > 30){
                        Still still = new Still();
                        Thread t1 = new Thread(still);
                        t1.start();
                        time = System.currentTimeMillis() / 1000;
                    }
                }
            }
        }
    }

class Still implements Runnable {
    Mat mat;
    public Still(){
    }


    @Override
    public void run(){

        String path = null;
        try {
            path = dtf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MailServices().mailSend(path);
        Thread.interrupted();
    }

    public String dtf(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("yyyyMMdd");
        String t = LocalTime.now().format(dtf);
        String d = LocalDate.now().format(day);
        String path = ("/home/pi/camera/" + d + "/" + t + ".jpg");
        File directory = new File(String.valueOf("/home/pi/camera/" + d));
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imwrite(path,new MotionDetector().getPic());
        return path;
    }
}



