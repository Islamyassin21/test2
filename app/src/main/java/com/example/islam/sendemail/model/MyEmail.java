package com.example.islam.sendemail.model;

import android.util.Log;
import android.widget.CheckBox;

/**
 * Created by islam on 21/09/2016.
 */
public class MyEmail {

    public String Email;
    public String Name;
    public int itemId;


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

}
