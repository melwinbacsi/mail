package services;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Still implements Runnable {
    private boolean instant;
    private BufferedImage pic;

    public Still(boolean instant) {
        this.instant = instant;
    }

    public Still(boolean instant, BufferedImage pic) {
        this.instant = instant;
        this.pic = pic;
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
            directory.mkdirs();
        }
        if (instant) {
            pic = MotionDetector.getPic();
        }
        try {
            ImageIO.write(pic, "jpg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}