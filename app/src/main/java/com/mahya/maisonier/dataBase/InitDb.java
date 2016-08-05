package com.mahya.maisonier.dataBase;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.socks.library.KLog;
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
    }


}
