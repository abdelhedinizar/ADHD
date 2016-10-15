package com.nizar.abdelhedi.adhd;

/**
 * Created by Ghassen on 14/10/2016.
 */

public class Data {
    int attention,blink,mediation;

    public Data(int attention, int blink, int mediation) {
        this.attention = attention;
        this.blink = blink;
        this.mediation = mediation;
    }

    public Data() {
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getBlink() {
        return blink;
    }

    public void setBlink(int blink) {
        this.blink = blink;
    }

    public int getMediation() {
        return mediation;
    }

    public void setMediation(int mediation) {
        this.mediation = mediation;
    }
}
