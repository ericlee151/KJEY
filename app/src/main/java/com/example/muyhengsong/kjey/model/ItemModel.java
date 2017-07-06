package com.example.muyhengsong.kjey.model;

/**
 * Created by muyhengsong on 5/7/17.
 */

public class ItemModel {

    private String item_name, bl_person, category, date_return, note;
    private int picture;

    public ItemModel() {
    }

    public ItemModel(String item_name, String bl_person, String category, String date_return, String note, int picture) {
        this.item_name = item_name;
        this.bl_person = bl_person;
        this.category = category;
        this.date_return = date_return;
        this.note = note;
        this.picture = picture;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getBl_person() {
        return bl_person;
    }

    public void setBl_person(String bl_person) {
        this.bl_person = bl_person;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate_return() {
        return date_return;
    }

    public void setDate_return(String date_return) {
        this.date_return = date_return;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
