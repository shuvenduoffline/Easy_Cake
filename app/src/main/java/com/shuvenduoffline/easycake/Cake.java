package com.shuvenduoffline.easycake;

public class Cake {
    String name;
    String weight;
    String price;
    String id;
    String imgurl;
    String imgbaseurl = "http://kekizadmin.com/uploads/catrgories/";

    public Cake() {
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public Cake(String name, String weight, String price, String id, String imgurl) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.id = id;
        this.imgurl = imgurl;
    }

    public String getImgurl() {
        return imgbaseurl+imgurl;
    }
}
