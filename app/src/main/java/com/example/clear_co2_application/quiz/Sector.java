package com.example.clear_co2_application.quiz;

public class Sector
{

    int Image;
    String Title;

    public Sector(int image, String title) {
        Image = image;
        Title = title;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
