package tn.chap1;

public class Performance {
    private String playID;
    private int audience;

    // Constructors, getters, and setters

    // Constructor
    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    // Getters and setters
    public String getPlayID() {
        return playID;
    }

    public void setPlayID(String playID) {
        this.playID = playID;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }
}