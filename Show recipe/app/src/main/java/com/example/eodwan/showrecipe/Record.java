package com.example.eodwan.showrecipe;

/**
 * Created by eodwan on 3‏/9‏/2016.
 */
public class Record {
    String name;
    String image;
    int id;
    int noid;
    String rating;

    public Record()
    {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Record(String name , int noid, String image,String rating)
    {
        this.name=name;
        this.rating=rating;
        this.noid=noid;
        this.image=image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getNoid() {
        return noid;
    }

    public void setNoid(int noid) {
        this.noid = noid;
    }
}
