package org.marcinzelent.liberavem;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bird implements Serializable {
    @SerializedName("Id")
    private int id;
    @SerializedName("Created")
    private String created;
    @SerializedName("NameEnglish")
    private String nameEnglish;
    @SerializedName("NameDanish")
    private String nameDanish;
    @SerializedName("PhotoUrl")
    private String photoUrl;

    public Bird(int id, String created, String nameEnglish, String nameDanish, String photoUrl) {
        this.id = id;
        this.created = created;
        this.nameEnglish = nameEnglish;
        this.nameDanish = nameDanish;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public String getNameDanish() {
        return nameDanish;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String toString() {
        return getNameEnglish();
    }
}
