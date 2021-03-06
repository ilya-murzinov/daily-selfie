package com.github.ilyamurzinov.dailyselfie;

import android.app.Application;

import com.github.ilyamurzinov.dailyselfie.component.DailySelfieModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class DailySelfieApplication extends Application {
    private static ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                (Object) new DailySelfieModule(this)
        );
    }

    public static void inject(Object object) {
        graph.inject(object);
    }
}
