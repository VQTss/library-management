/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.models;

/**
 *
 * @author 
 */
public class Reader {

    private String id;
    private String name;
    private String book_id;
    private String identify;
    private Book book;
    private String status;
    private String start_day;
    private String end_day;

    public Reader(String id, String name, String book_id, String identify, Book book, String status, String start_day, String end_day) {
        this.id = id;
        this.name = name;
        this.book_id = book_id;
        this.identify = identify;
        this.book = book;
        this.status = status;
        this.start_day = start_day;
        this.end_day = end_day;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getIdentify() {
        return identify;
    }

    public Book getBook() {
        return book;
    }

    public String getStatus() {
        return status;
    }

    public String getStart_day() {
        return start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    
    
    
}
