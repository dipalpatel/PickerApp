package com.inmobi.picker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiBanner.BannerAdListener;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiNative;
import com.inmobi.commons.core.configs.ConfigComponent;
import com.inmobi.commons.core.configs.ConfigNetworkRequest;

import java.util.Map;

public class Configurable extends Activity {

    //    private EditText mSlotEdit;
    private static Button loadBanner, loadBanner1, loadInt, loadInt1, loadNative, loadNative1;
    InMobiBanner banner, banner1;
    LinearLayout parentLayout;
    InMobiInterstitial intAd, intAd1;
    InMobiNative nativeAd, nativeAd1;
    BannerAdListener bannerListener = new BannerAdListener() {

        @Override
        public void onAdLoadSucceeded(InMobiBanner ad) {
            Log.v("Dipal", "onAdLoadSucceeded");
        }

        @Override
        public void onAdLoadFailed(InMobiBanner ad, InMobiAdRequestStatus statusCode) {
            Log.v("Dipal", "onAdLoadFailed: " + statusCode.getStatusCode());
        }

        @Override
        public void onAdDisplayed(InMobiBanner ad) {
            Log.v("Dipal", "onAdDisplayed");
        }

        @Override
        public void onAdDismissed(InMobiBanner ad) {
            Log.v("Dipal", "onAdDisplayed");
        }

        @Override
        public void onAdInteraction(InMobiBanner ad, Map<Object, Object> params) {
            Log.v("Dipal", "onAdInteraction");
        }

        @Override
        public void onUserLeftApplication(InMobiBanner ad) {
            Log.v("Dipal", "onUserLeftApplication");
        }

        @Override
        public void onAdRewardActionCompleted(InMobiBanner ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "onAdRewardActionCompleted");
        }
    };
    InMobiInterstitial.InterstitialAdListener intAdListener = new InMobiInterstitial.InterstitialAdListener() {
        @Override
        public void onAdRewardActionCompleted(InMobiInterstitial ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "onAdRewardActionCompleted" + rewards.toString());
        }

        @Override
        public void onAdDisplayed(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdDisplayed");
        }

        @Override
        public void onAdDismissed(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdDismissed");
        }

        @Override
        public void onAdInteraction(InMobiInterstitial ad, Map<Object, Object> params) {
            Log.v("Dipal", "onAdInteraction" + params.toString());
        }

        @Override
        public void onAdLoadSucceeded(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdLoadSucceeded, ad can be shown now. ");
//            if (ad.isReady())
//                ad.show();
        }

        @Override
        public void onAdLoadFailed(InMobiInterstitial ad, InMobiAdRequestStatus requestStatus) {
            Log.v("Dipal", "onAdLoadFailed: " + requestStatus.getStatusCode());
        }

        @Override
        public void onUserLeftApplication(InMobiInterstitial ad) {
            Log.v("Dipal", "onUserLeftApplication");
        }
    };
    private InMobiNative.NativeAdListener nativeAdListener = new InMobiNative.NativeAdListener() {
        @Override
        public void onAdLoadSucceeded(InMobiNative ad) {
            Log.v("Dipal", "Ad request succeeded");
        }

        @Override
        public void onAdLoadFailed(InMobiNative ad, InMobiAdRequestStatus status) {
            Log.v("Dipal", "onAdLoadFailed: " + status.getStatusCode());
        }

        @Override
        public void onAdDismissed(InMobiNative ad) {
            Log.v("Dipal", "Ad Dismissed");
        }

        @Override
        public void onAdDisplayed(InMobiNative ad) {
            Log.v("Dipal", "Ad Displayed");
        }

        @Override
        public void onUserLeftApplication(InMobiNative ad) {
            Log.v("Dipal", "User left application");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configurable);

        loadBanner = (Button) findViewById(R.id.LoadBanner);
        loadBanner1 = (Button) findViewById(R.id.LoadBanner1);
        loadInt = (Button) findViewById(R.id.LoadInt);
        loadInt1 = (Button) findViewById(R.id.LoadInt1);
        loadNative = (Button) findViewById(R.id.LoadNative);
        loadNative1 = (Button) findViewById(R.id.LoadNative1);

//        mSlotEdit = (EditText) findViewById(R.id.editText1);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//        String appid = this.getIntent().getExtras().getString("appid");
//        Long slotId = Long.valueOf(appid).longValue();
//        mSlotEdit.setText(appid);

        AdNetworkRequest.setAdServerUrl(TestConstants.AdSlotServer);

        banner = new InMobiBanner(this, 13030001L);
        banner1 = new InMobiBanner(this, 12030002L);

        parentLayout = (LinearLayout) findViewById(R.id.listNews);
        float density = getResources().getDisplayMetrics().density;
        int width = (int) (320 * density);
        int height = (int) (50 * density);
        parentLayout.addView(banner, new LinearLayout.LayoutParams(width, height));
        parentLayout.addView(banner1, new LinearLayout.LayoutParams(width, height));

        banner.setListener(bannerListener);
        banner1.setListener(bannerListener);

        intAd = new InMobiInterstitial(this, 13030001L, intAdListener);
        intAd1 = new InMobiInterstitial(this, 12030002L, intAdListener);

        nativeAd = new InMobiNative(16020001L, nativeAdListener);
        nativeAd1 = new InMobiNative(16020002L, nativeAdListener);


//        banner.disableHardwareAcceleration();

//        Map<String, String> rParams = new HashMap<String, String>();
//        rParams.put("mk-carrier", "3.0.119.0");
//        banner.setExtras(rParams);
//        banner.setEnableAutoRefresh(false);
//        banner.setRefreshInterval(30);
        banner.load();
//        banner1.load();

    }

    public void loadBanner(View v) {
//        long slotId = Long.parseLong(mSlotEdit.getText().toString());
//        android.util.Log.v("Dipal", "Text changed to: " + slotId);
//
//        banner = new InMobiBanner(this, slotId);
//
//        parentLayout.removeAllViews();
//        float density = getResources().getDisplayMetrics().density;
//        int width = (int) (320 * density);
//        int height = (int) (50 * density);
//        parentLayout.addView(banner, new LinearLayout.LayoutParams(width, height));
//
//        banner.setListener(bannerListener);

        banner.load();
    }

    public void loadBanner1(View v) {
        banner1.load();

        ConfigNetworkRequest.setDefaultConfigUrl("https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=configencryptrequest&time=9809819999&test_id=rootconf_1_adsmisc_0003&");

        forceRemoveAllPref(this);

        ConfigComponent.getInstance().stop();

        waitThread(2000);

        ConfigComponent.getInstance().start();

        waitThread(20000);

    }

    public void loadInt(View v) {
//        long slotId = Long.parseLong(mSlotEdit.getText().toString());
//        android.util.Log.v("Dipal", "Text changed to: " + slotId);

//        intAd = new InMobiInterstitial(this, slotId, intAdListener);
        intAd.load();
    }

    public void loadInt1(View v) {
        intAd1.load();
    }

    public void loadNative(View v) {
        nativeAd.load();
    }

    public void loadNative1(View v) {
        nativeAd1.load();
    }


    public static void waitThread(int interval) {
        try {
            synchronized (Looper.getMainLooper().getThread()) {
                Looper.getMainLooper().getThread().wait(interval);
            }
        } catch (InterruptedException e) {

        }
    }

    public static void forceRemoveAllPref(Context pContext) {
        Log.v("Dipal", "Removing cached shared prefs here!");

        String PreferencesName = "com.im.keyValueStore.config_store";
        SharedPreferences settings = pContext.getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        waitThread(1000);
    }
}