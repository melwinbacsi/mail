package db;

public class Measurement {
    private String measTime;
    private int actualWeight;
    private String origoTime;
    private int origoWeight;


    public Measurement(String measTime, String origoTime, int actualWeight, int origoWeight) {
        this.measTime = measTime;
        this.origoTime = origoTime;
        this.actualWeight = actualWeight;
        this.origoWeight = origoWeight;
    }

    public String getMeasTime() {
        return measTime;
    }

    public void setMeasTime(String measTime) {
        this.measTime = measTime;
    }

    public int getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(int actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getOrigoTime() {
        return origoTime;
    }

    public void setOrigoTime(String origoTime) {
        this.origoTime = origoTime;
    }

    public int getOrigoWeight() {
        return origoWeight;
    }

    public void setOrigoWeight(int origoWeight) {
        this.origoWeight = origoWeight;
    }
}
