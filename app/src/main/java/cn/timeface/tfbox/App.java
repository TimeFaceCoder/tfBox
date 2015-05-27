package cn.timeface.tfbox;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.facebook.stetho.Stetho;

/**
 * Created by rayboot on 15/5/22.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
