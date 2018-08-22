package services;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import org.bytedeco.javacv.OpenCVFrameGrabber;











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
    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    grabber.start();
    OpenCVFrameConverter.ToMat c2Mat = new OpenCVFrameConverter.ToMat();
    opencv_core.IplImage frame = converter.convert(grabber.grab());
    opencv_core.Mat mat = c2Mat.convertToMat(grabber.grab());
    opencv_core.IplImage image = null;
    opencv_core.IplImage prevImage = null;
    opencv_core.IplImage diff = null;
    opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
    
    while ((frame = converter.convert(grabber.grab())) != null) {
      opencv_core.cvClearMemStorage(storage);
      org.bytedeco.javacpp.opencv_imgproc.cvSmooth(frame, frame, 2, 9, 9, 2.0D, 2.0D);
      if (image == null) {
        image = opencv_core.IplImage.create(frame.width(), frame.height(), 8, 1);
        org.bytedeco.javacpp.opencv_imgproc.cvCvtColor(frame, image, 7);
      } else {
        prevImage = image;
        image = opencv_core.IplImage.create(frame.width(), frame.height(), 8, 1);
        org.bytedeco.javacpp.opencv_imgproc.cvCvtColor(frame, image, 7);
      }
      if (diff == null) {
        diff = opencv_core.IplImage.create(frame.width(), frame.height(), 8, 1);
      }
      if (prevImage != null) {
        opencv_core.cvAbsDiff(image, prevImage, diff);
        org.bytedeco.javacpp.opencv_imgproc.cvThreshold(diff, diff, 40.0D, 255.0D, 0);
        opencv_core.CvSeq contour = new opencv_core.CvSeq(null);
        org.bytedeco.javacpp.helper.opencv_imgproc.cvFindContours(diff, storage, contour, Loader.sizeof(opencv_core.CvContour.class), 1, 2);
        while ((contour != null) && (!contour.isNull())) {
          long now = System.currentTimeMillis() / 1000L;
          if (now - time > 30L) {
            grabber.stop();
            opencv_core.cvClearMemStorage(storage);
            Thread.sleep(3000L);
            grabber.start();
            mat = c2Mat.convertToMat(grabber.grab());
            Still still = new Still(mat);
            Thread t1 = new Thread(still);
            t1.start();
            time = now;
          }
          contour = contour.h_next();
        }
      }
    }
  }
}
