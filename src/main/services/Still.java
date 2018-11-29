package services;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Still {
    private BufferedImage pic;
    private String path;

    public Still() {
        pic = MotionDetector.getPic();
        try {
            path = dtf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MailServices().mailSend(path);
    }

    public Still(BufferedImage pic) {
        try {
            if (LoadCell.getWeight() < (WeightStore.readActualWeight() -1)) {
                this.pic = pic;
                try {
                    path = dtf();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new MailServices().mailSend(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            ImageIO.write(pic, "jpg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}