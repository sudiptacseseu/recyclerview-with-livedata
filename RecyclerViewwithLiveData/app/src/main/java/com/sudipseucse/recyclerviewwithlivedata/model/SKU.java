package com.sudipseucse.recyclerviewwithlivedata.model;

public class SKU {
    public String name;
    public int price, stock, quantity;
    public SKU(String name, int price, int stock, int quantity) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
    }
}
