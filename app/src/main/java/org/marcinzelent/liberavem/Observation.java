package org.marcinzelent.liberavem;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Observation implements Serializable {
    @SerializedName("Id")
    private int id;
    @SerializedName("Created")
    private String created;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Placename")
    private String placeName;
    @SerializedName("Comment")
    private String comment;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("BirdId")
    private int birdId;
    @SerializedName("NameEnglish")
    private String nameEnglish;
    @SerializedName("NameDanish")
    private String nameDanish;
    @SerializedName("Population")
    private int population;

    public Observation(int id, String created, double latitude, double longitude, String placeName,
                       String comment, String userId, int birdId, String nameEnglish,
                       String nameDanish, int population) {
        this.id = id;
        this.created = created;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.comment = comment;
        this.userId = userId;
        this.birdId = birdId;
        this.nameEnglish = nameEnglish;
        this.nameDanish = nameDanish;
        this.population = population;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getComment() {
        return comment;
    }

    public String getUserId() {
        return userId;
    }

    public int getBirdId() {
        return birdId;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public String getNameDanish() {
        return nameDanish;
    }

    public int getPopulation() {
        return population;
    }
}
