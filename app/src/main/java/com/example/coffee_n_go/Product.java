package com.example.coffee_n_go;

public class Product {
    private String id="";
    private String name="";
    private String price="";
    private String stocks="";

    public Product(){

    }
    public Product(String id, String name, String price, String stocks) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stocks = stocks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }
}
