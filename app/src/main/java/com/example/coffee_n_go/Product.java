package com.example.coffee_n_go;


public class Product {
    private String id="";
    private String name="";
    private double price;
    private int stocks;

    public Product(){

    }
    public Product(String id, String name, double price, int stocks) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public String toString(){
        return this.getName();
    }
}
