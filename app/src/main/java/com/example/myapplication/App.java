package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.Model.Costumer;

public class App extends Application {
    private Costumer _user;

    public Costumer getUser() {
        return _user;
    }

    public void setUser(Costumer user) {
        _user = user;
    }
}
