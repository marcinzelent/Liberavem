package org.marcinzelent.liberavem;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Observation implements Serializable {
    @SerializedName("BirdId")
    private int birdId;
    @SerializedName("Comment")
    private String comment;
    @SerializedName("Created")
    private String created;
    @SerializedName("Id")
    private int id;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Placename")
    private String placeName;
    @SerializedName("Population")
    private int population;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("NameDanish")
    private String nameDanish;
    @SerializedName("NameEnglish")
    private String nameEnglish;

    public Observation(int id, String created, double latitude, double longitude, String placeName,
                       String comment, String userId, int birdId, String nameEnglish,
                       String nameDanish, int population) {
        this.birdId = birdId;
        this.comment = comment;
        this.created = created;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.population = population;
        this.userId = userId;
        this.nameDanish = nameDanish;
        this.nameEnglish = nameEnglish;
    }

    public int getBirdId() {
        return birdId;
    }

    public void setBirdId(int birdId) {
        this.birdId = birdId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameDanish() {
        return nameDanish;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }
}
