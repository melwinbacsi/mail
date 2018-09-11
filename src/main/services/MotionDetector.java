package services;

import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.findContours;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MotionDetector
        implements Runnable {
    public MotionDetector() {
    }

    private static boolean mdStop = true;
    private static BufferedImage pic = null;
    private static BufferedImage capturedPic = null;


    static BufferedImage getCapturedPic() {
        return capturedPic;
    }

    static void setCapturedPic(BufferedImage capturedPic) {
        MotionDetector.capturedPic = capturedPic;
    }

    static BufferedImage getPic() {
        return pic;
    }

    public static boolean isMdStop() {
        return mdStop;
    }

    public static void setMdStop(boolean mdStop) {
        MotionDetector.mdStop = mdStop;
    }

    public void run() {
        try {
            motionDetect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void motionDetect() throws Exception {
        long time = 0;
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(-1);
        grabber.start();
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        Mat frame = converter.convert(grabber.grab());
        Mat image = null;
        Mat prevImage = null;
        Mat diff = null;
        MatVector contour = new MatVector();
        int motionCounter = 0;
        long lastMotion;
        boolean first;
        boolean captured = false;

        // CanvasFrame canvasFrame = new CanvasFrame("Some Title");
        // canvasFrame.setCanvasSize(frame.rows(), frame.cols());

        while (!isMdStop()) {
            GaussianBlur(frame, frame, new Size(3, 3), 0);
            pic = new Java2DFrameConverter().getBufferedImage(grabber.grab());
            if (image == null) {
                image = new Mat(frame.rows(), frame.cols(), CV_8UC1);
                cvtColor(frame, image, CV_BGR2GRAY);
            } else {
                prevImage = image;
                image = new Mat(frame.rows(), frame.cols(), CV_8UC1);
                cvtColor(frame, image, CV_BGR2GRAY);
            }
            if (diff == null) {
                diff = new Mat(frame.rows(), frame.cols(), CV_8UC1);
            }
            if (prevImage != null) {
                absdiff(image, prevImage, diff);
                threshold(diff, diff, 50, 255, CV_THRESH_BINARY);

                // canvasFrame.showImage(converter.convert(diff));

                findContours(diff, contour, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
                if (contour.size() > 0 && (System.currentTimeMillis() / 1000) - time > 30) {
                    first = (System.currentTimeMillis() / 1000) - time > 50;
                    ++motionCounter;
                    lastMotion = System.currentTimeMillis() / 1000;
                    if ((System.currentTimeMillis() / 1000) - lastMotion > 5) {
                        motionCounter = 0;
                    }
                    if (motionCounter > 5) {
                        time = System.currentTimeMillis() / 1000;
                        if (!first) {
                            capturedPic = pic;
                            if (captured) {
                                Thread t1 = new Thread(new Still(false));
                                t1.start();
                                motionCounter = 0;
                            } else {
                                captured = true;
                            }
                        }
                    }
                }
            }
        }
        grabber.stop();
    }
}

