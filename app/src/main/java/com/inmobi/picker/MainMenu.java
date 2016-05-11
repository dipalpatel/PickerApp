package com.inmobi.picker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.inmobi.commons.core.configs.ConfigNetworkRequest;
import com.inmobi.commons.core.utilities.Logger;
import com.inmobi.sdk.InMobiSdk;

import java.util.ArrayList;
import java.util.Properties;

@TargetApi(Build.VERSION_CODES.M)
public class MainMenu extends Activity {
    Intent intent = null;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private Properties properties;
//    public static InMobiNative nativead;

    private static class SampleDumperPluginsProvider implements DumperPluginsProvider {
        private final Context mContext;

        public SampleDumperPluginsProvider(Context context) {
            mContext = context;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            ArrayList<DumperPlugin> plugins = new ArrayList<DumperPlugin>();
            for (DumperPlugin defaultPlugin : Stetho.defaultDumperPluginsProvider(mContext).get()) {
                plugins.add(defaultPlugin);
            }
            plugins.add(new HelloWorldDumperPlugin());
            return plugins;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectAll()
//                    .detectDiskReads()
//                    .detectDiskWrites().detectCustomSlowCalls()
//                    .detectNetwork()
//                    .penaltyLog()
//                    .build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .build());
//        }


        Thread.setDefaultUncaughtExceptionHandler(new CrashReporter(Thread.getDefaultUncaughtExceptionHandler()));

        long startTime = SystemClock.elapsedRealtime();
        final Context context = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(new SampleDumperPluginsProvider(context))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build());
        long elapsed = SystemClock.elapsedRealtime() - startTime;
        Log.i("Stetho", "Stetho initialized in " + elapsed + " ms");


//        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.ERROR);
        Logger.setLogLevel(Logger.InternalLogLevel.INTERNAL);

        ConfigNetworkRequest.setDefaultConfigUrl(TestConstants.ConfigMockServer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

        InMobiSdk.init(this, "90cb4af281b14e998f6b5263659ca5ff");
//        InMobiSdk.init(this, "f0e0517a1ef444c9b4c604656abe2ec0");
//        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.NONE);

//        String adServer = "https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=placementphprequest&time=123&";
//        AdNetworkRequest.setAdServerUrl(adServer);

//        nativead = new InMobiNative(13010001L, listnr);
//        nativead.load();

        final String[] application = {"Expandable", "Interstitial", "Native", "Configurable", "Interstitial NonFunctional","Permissions", "NativeStrands"};
        listView = (ListView) findViewById(R.id.list);

        listAdapter = new ArrayAdapter<String>(this, R.layout.list_row,
                application);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (arg2) {
                    case 0:
                        intent = new Intent(MainMenu.this,
                                ExpandMenu.class);
                        break;

                    case 1:
                        intent = new Intent(MainMenu.this,
                                InterstitialMenu.class);
                        break;

                    case 2:
                        intent = new Intent(MainMenu.this,
                                NativeMenu.class);
                        break;

                    case 3:
                        intent = new Intent(MainMenu.this,
                                Configurable.class);
                        break;

                    case 4:
                        intent = new Intent(MainMenu.this,
                                InterstitialNonFunctional.class);
                        break;

                    case 5:
                        intent = new Intent(MainMenu.this,
                                permissions.class);
                        break;

                    case 6:
                        intent = new Intent(MainMenu.this,
                                NativeStrandsMenu.class);
                        break;
                }
                // intent.putExtra("appid", application[arg2]);
                startActivity(intent);
            }
        });

    }

//    private InMobiNative.NativeAdListener listnr = new InMobiNative.NativeAdListener() {
//        @Override
//        public void onAdLoadSucceeded(InMobiNative ad) {
//            Log.v("Dipal", "Ad request succeeded");
//        }
//
//        @Override
//        public void onAdLoadFailed(InMobiNative ad, InMobiAdRequestStatus status) {
//            Log.v("Dipal", "onAdLoadFailed: " + status.getStatusCode());
//        }
//
//        @Override
//        public void onAdDismissed(InMobiNative ad) {
//            Log.v("Dipal", "Ad Dismissed");
//        }
//
//        @Override
//        public void onAdDisplayed(InMobiNative ad) {
//            Log.v("Dipal", "Ad Displayed");
//        }
//
//        @Override
//        public void onUserLeftApplication(InMobiNative ad) {
//            Log.v("Dipal", "User left application");
//        }
//    };

}
