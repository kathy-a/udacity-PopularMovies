package com.udacity.popularmovies;

import android.app.Application;
import android.content.res.Resources;

/** This class allows to access resource file anywhere in the App. Mainly used for API key string access in local configuration.xml
 * @return resources of application
 */
public class App extends Application {

    private static Resources resources;

    @Override
    public void onCreate(){
        super.onCreate();

        resources = getResources();
    }

    public static Resources getAppResources(){
        return resources;
    }

}
