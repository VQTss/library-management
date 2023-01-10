/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author 
 */
public class Book {

    private String id;
    private String name;
    private String image;
    private String amount;
    private Category Category;
    private String day;

    public Book() {
    }


    
    public Book(String id, String name, String image, String amount, Category Category, String day) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.Category = Category;
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getAmount() {
        return amount;
    }

    public Category getCategory() {
        return Category;
    }

    public String getDay() {
        return day;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCategory(Category Category) {
        this.Category = Category;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    

}
