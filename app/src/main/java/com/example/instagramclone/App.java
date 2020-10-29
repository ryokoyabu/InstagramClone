package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("FphIlUrI93FTtLdVRJTV2NcfzEizZBIHOa1xQHeB")
                // if defined
                .clientKey("8JSy9juJuSDCoHG2vAdFJDOey5CyLv71LqPeQZiL")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
