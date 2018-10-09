package services;

import java.io.*;
import java.util.Properties;

public class WeightStore {
    public static void setOrigoWeight(int origoWeight) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("weight.properties");
        props.load(fis);
        fis.close();

        FileOutputStream fOs = new FileOutputStream("weight.properties");
        props.setProperty("origoWeight", String.valueOf(origoWeight));
        props.store(fOs, "weight");
        fOs.close();

    }
    public static int readOrigoWeight() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("weight.properties");
        props.load(fis);
        fis.close();
        return Integer.parseInt(props.getProperty("origoWeight"));
    }

    public static void setLastWeight(int origoWeight) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("weight.properties");
        props.load(fis);
        fis.close();

        FileOutputStream fOs = new FileOutputStream("weight.properties");
        props.setProperty("lastWeight", String.valueOf(origoWeight));
        props.store(fOs, "weight");
        fOs.close();

    }

    public static int readLastWeight() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("weight.properties");
        props.load(fis);
        fis.close();
        return Integer.parseInt(props.getProperty("lastWeight"));
    }
    public static void setActualWeight(int origoWeight) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("weight.properties");
        props.load(fis);
        fis.close();

        FileOutputStream fOs = new FileOutputStream("weight.properties");
        props.setProperty("actualWeight", String.valueOf(origoWeight));
        props.store(fOs, "weight");
        fOs.close();

    }
    public static int readActualWeight() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("weight.properties");
        props.load(fis);
        fis.close();
        return Integer.parseInt(props.getProperty("actualWeight"));
    }
}
