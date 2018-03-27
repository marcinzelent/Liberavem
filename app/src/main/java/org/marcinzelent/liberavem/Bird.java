package org.marcinzelent.liberavem;

public class Bird {
    private int Id;
    private long Created;
    private String NameEnglish;
    private String NameDanish;
    private String PhotoUrl;

    public Bird(int id, long created, String nameEnglish, String nameDanish, String photoUrl) {
        Id = id;
        Created = created;
        NameEnglish = nameEnglish;
        NameDanish = nameDanish;
        PhotoUrl = photoUrl;
    }

    public int getId() {
        return Id;
    }

    public long getCreated() {
        return Created;
    }

    public String getNameEnglish() {
        return NameEnglish;
    }

    public String getNameDanish() {
        return NameDanish;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }
}
