package com.example.rangoo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Food implements Parcelable {

    private String idFood;
    private String name;
    private String resume;
    private String description;
    private String imageUrl;

    public Food() {}

    public Food(String id, String name, String resume, String description, String imageUrl){
        setIdFood(id);
        setName(name);
        setResume(resume);
        setDescription(description);
        setImageUrl(imageUrl);
    }

    protected Food(Parcel in) {
        idFood = in.readString();
        name = in.readString();
        resume = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idFood);
        dest.writeString(name);
        dest.writeString(resume);
        dest.writeString(description);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return getIdFood() + " | " + getName();
    }
}

