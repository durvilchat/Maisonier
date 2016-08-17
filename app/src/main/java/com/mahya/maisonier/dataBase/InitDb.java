package com.mahya.maisonier.dataBase;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.socks.library.KLog;

import java.io.File;
/*
import com.socks.library.KLog;

/**
 * Created by LARUMEUR on 21/07/2016.
 */

public class InitDb extends Application {
    boolean isdebug;

    @Override
    public void onCreate() {
        super.onCreate();
        isdebug = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));

        if (isdebug) {
            KLog.init(true);
        } else {
            KLog.init(false);
        }


        FlowManager.init(new FlowConfig.Builder(this).build());

        File maisonier = new File(Environment.getExternalStorageDirectory() +
                File.separator + "TollCulator");
        boolean success = true;
        if (!maisonier.exists()) {
            success = maisonier.mkdir();
        }
        if (success) {
            System.out.println("good");
        } else {
            // Do something else on failure
        }
    }


}
