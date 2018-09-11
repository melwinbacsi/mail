package services;

import org.bytedeco.javacpp.opencv_core;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Still implements Runnable {
    opencv_core.Mat mat;
    private boolean instant;
    public Still(boolean instant) {
        this.instant=instant;
    }


    @Override
    public void run() {

        String path = null;
        try {
            path = dtf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MailServices().mailSend(path);
    }

    private String dtf() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("yyyyMMdd");
        String t = LocalTime.now().format(dtf);
        String d = LocalDate.now().format(day);
        String path = ("/home/pi/camera/" + d + "/" + t + ".jpg");
        File directory = new File(String.valueOf("/home/pi/camera/" + d));
        if (!directory.exists()) {
            directory.mkdirs()
        }
        if(instant){MotionDetector.setCapturedPic(MotionDetector.getPic());}
        try {
            ImageIO.write(MotionDetector.getCapturedPic(), "jpg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}