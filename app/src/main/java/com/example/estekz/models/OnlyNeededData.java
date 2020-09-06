package com.example.estekz.models;

public class OnlyNeededData {
    private String name;
    private String price;
    private String urlToImage;
    private long id;

    public OnlyNeededData(long id, String name, String price,  String urlToImage) {
        this.id=id;
        this.name = name;
        this.price = price;

        this.urlToImage = urlToImage;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }



    public String getUrlToImage() {
        return urlToImage;
    }
}
