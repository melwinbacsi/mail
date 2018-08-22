package services;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgcodecs;














































































class Still
  implements Runnable
{
  opencv_core.Mat mat;
  
  public Still(opencv_core.Mat mat)
  {
    this.mat = mat;
  }
  


  public void run()
  {
    String path = dtf();
    new MailServices().mailSend(path);
    Thread.interrupted();
  }
  
  public String dtf() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
    DateTimeFormatter day = DateTimeFormatter.ofPattern("yyyyMMdd");
    String t = LocalTime.now().format(dtf);
    String d = LocalDate.now().format(day);
    String path = "/home/pi/camera/" + d + "/" + t + ".jpg";
    File directory = new File(String.valueOf("/home/pi/camera/" + d));
    if (!directory.exists()) {
      directory.mkdir();
    }
    opencv_imgcodecs.imwrite(path, mat);
    return path;
  }
}
