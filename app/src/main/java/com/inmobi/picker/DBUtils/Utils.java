package com.inmobi.picker.DBUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.inmobi.ads.AdConfig;
import com.inmobi.commons.core.configs.ConfigComponent;

import java.io.File;

/**
 * Created by dipal.patel on 2/24/16.
 */
public class Utils {
    public static final String KLOG_TAG = "dipal";

    public static void clearAppCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDirectory(dir);
            }
        } catch (Exception e) {}
    }

    public static void clearCache(Context context) {
        Log.v(KLOG_TAG, "Removing cached content here!");

        File path = new File(context.getCacheDir(), "im_cached_content");
        deleteDirectory(path);
    }

    public static void deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            path.delete();
        }
    }

    public static void forceRemoveAllPref(Context pContext) {
        Log.v(KLOG_TAG, "Removing cached shared prefs here!");

        String PreferencesName = "com.im.keyValueStore.config_store";
        SharedPreferences settings = pContext.getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
        settings.edit().clear().commit();

    }

    public static void fetchAdConfig() {

        ConfigComponent.getInstance().getConfig(new AdConfig(), null);

    }

}
