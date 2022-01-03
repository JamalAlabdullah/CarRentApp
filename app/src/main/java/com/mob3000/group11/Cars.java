package com.mob3000.group11;

public class Cars {


    String brand,gir,imgurl,regNr;
    float price;
    int seats;
    Cars(){}

    public Cars(float price, String brand, String gir, int seats,String imgurl,String regNr) {
        this.price = price;
        this.brand = brand;
        this.gir = gir;
        this.seats = seats;
        this.imgurl=imgurl;
        this.regNr=regNr;
    }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }

    public String getBrand() {
        return "Brand: "+ brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGir() {
        return   "Gear  : "+ gir;
    }

    public void setGir(String gir) {
        this.gir = gir;
    }

    public String getImgurl() { return imgurl; }

    public void setImgurl(String imgurl) { this.imgurl = imgurl; }

    public int getSeats() { return seats; }

    public void setSeats(int seats) { this.seats = seats; }

    public String getRegNr() { return regNr; }

    public void setRegNr(String regNr) { this.regNr = regNr; }


    public  String getPriceWithLabel() {
        return "Price: " + price ;
    }

    public  String getSeatsWithLabel() {
        return "Seats: " + seats ;
    }

}
