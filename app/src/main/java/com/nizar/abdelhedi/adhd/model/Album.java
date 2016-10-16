package com.nizar.abdelhedi.adhd.model;

/**
 * Created by abdel on 15/10/2016.
 */

public class Album {

    private String name;
    private int concentrationLevelNeeded;
    private int thumbnail;
    private int videoId;

    public Album() {
    }

    public Album(String name, int concentrationLevelNeeded, int thumbnail, int videoId) {
        this.name = name;
        this.concentrationLevelNeeded = concentrationLevelNeeded;
        this.thumbnail = thumbnail;
        this.videoId = videoId;
    }

    public Album(String name)
    {
        this.name=name;
    }

    public String getName() {
        return name;
    }


    public void setVideoId(int videoId)
    {
        this.videoId= videoId;
    }

    public int getVideoId()
    {
        return videoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConcentrationLevelNeeded() {
        return concentrationLevelNeeded;
    }

    public void setConcentrationLevelNeeded(int concentrationLevelNeeded) {
        this.concentrationLevelNeeded = concentrationLevelNeeded;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
