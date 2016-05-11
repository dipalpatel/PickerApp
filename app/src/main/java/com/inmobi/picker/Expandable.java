package com.inmobi.picker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

//import com.inmobi.ads.AdDao;
import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiBanner.BannerAdListener;
import com.inmobi.commons.core.configs.ConfigNetworkRequest;
import com.inmobi.sdk.InMobiSdk;

import java.util.Map;

public class Expandable extends Activity {

    private static Button loadBanner, clearConfig;
    InMobiBanner banner;
    LinearLayout parentLayout;
    BannerAdListener bannerListener = new BannerAdListener() {

        @Override
        public void onAdLoadSucceeded(InMobiBanner ad) {
//            parentLayout.removeAllViews();
            android.util.Log.v("Dipal", "onAdLoadSucceeded");
//            banner.load();
        }

        @Override
        public void onAdLoadFailed(InMobiBanner ad, InMobiAdRequestStatus statusCode) {
            android.util.Log.v("Dipal", "onAdLoadFailed: " + statusCode.getStatusCode());
        }

        @Override
        public void onAdDisplayed(InMobiBanner ad) {
            android.util.Log.v("Dipal", "onAdDisplayed");
//            banner.load();

        }

        @Override
        public void onAdDismissed(InMobiBanner ad) {
            android.util.Log.v("Dipal", "onAdDismissed");
        }

        @Override
        public void onAdInteraction(InMobiBanner ad, Map<Object, Object> params) {
            android.util.Log.v("Dipal", "onAdInteraction, params: " + params);
        }

        @Override
        public void onUserLeftApplication(InMobiBanner ad) {
            android.util.Log.v("Dipal", "onUserLeftApplication");
        }

        @Override
        public void onAdRewardActionCompleted(InMobiBanner ad, Map<Object, Object> rewards) {
            android.util.Log.v("Dipal", "onAdRewardActionCompleted, params: " + rewards);
        }
    };

    private EditText mSlotEdit;

    public static void waitThread(int interval) {
        try {
            synchronized (Looper.getMainLooper().getThread()) {
                Looper.getMainLooper().getThread().wait(interval);
            }
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expandable);

//        Utils.clearCache(this.getApplicationContext());
//        Utils.forceRemoveAllPref(this.getApplicationContext());

        loadBanner = (Button) findViewById(R.id.Load);
//        clearConfig = (Button) findViewById(R.id.clear);
        mSlotEdit = (EditText) findViewById(R.id.editText1);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String appid = this.getIntent().getExtras().getString("appid");
        Long slotId = Long.valueOf(appid).longValue();
        mSlotEdit.setText(appid);

        String adServer = this.getIntent().getExtras().getString("adServer");
        AdNetworkRequest.setAdServerUrl(adServer);

        InMobiSdk.setEducation(InMobiSdk.Education.COLLEGE_OR_GRADUATE);
        InMobiSdk.setEthnicity(InMobiSdk.Ethnicity.AFRICAN_AMERICAN);
        InMobiSdk.setGender(InMobiSdk.Gender.FEMALE);
        InMobiSdk.setHouseHoldIncome(InMobiSdk.HouseHoldIncome.ABOVE_USD_150K);
        InMobiSdk.setAgeGroup(InMobiSdk.AgeGroup.ABOVE_55);

        InMobiSdk.setAge(34);
        InMobiSdk.setIncome(34555);
        InMobiSdk.setYearOfBirth(1919);

//        InMobiSdk.setAge(-2);
//        InMobiSdk.setIncome(-45);
//        InMobiSdk.setYearOfBirth(-7657);

        InMobiSdk.setNationality("Indian");
        InMobiSdk.setPostalCode("PostalCode");
        InMobiSdk.setInterests("books");
        InMobiSdk.setLanguage("Hindi");
        InMobiSdk.setLocationWithCityStateCountry("Indore", "MP", "India");
        InMobiSdk.setAreaCode("areaCode");

//        "a&b"
//        "a---/adsf134'"
//        "a b"
//        "  ab "
//        null
//        empty
        InMobiSdk.addIdType(InMobiSdk.ImIdType.LOGIN, "  ab ");
        InMobiSdk.addIdType(InMobiSdk.ImIdType.SESSION, "  ab ");
//
//        InMobiSdk.addIdType(InMobiSdk.InMobiIdType.LOGIN,"LoginId");
//        InMobiSdk.addIdType(InMobiSdk.InMobiIdType.SESSION,"SessionId");

//        Calendar calendar = Calendar.getInstance();
//        Date now = calendar.getTime();
//
//        Location location = new Location("");
//        location.setLatitude(34.33);
//        location.setLongitude(55.55);
//        location.setTime(now.getTime());
//        InMobiSdk.setLocation(location);

        android.util.Log.v("Dipal", "onAdInit");
//        banner = new InMobiBanner(this, 13030001L);

        banner = new InMobiBanner(this, slotId);


        parentLayout = (LinearLayout) findViewById(R.id.listNews);
        float density = getResources().getDisplayMetrics().density;
        int width = (int) (320 * density);
        int height = (int) (50 * density);
//        banner.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        parentLayout.addView(banner, new LinearLayout.LayoutParams(width, height));

//        parentLayout.addView(banner, new LinearLayout.LayoutParams(5, 5));

//        banner.disableHardwareAcceleration();
        banner.setListener(bannerListener);

        banner.load();

//        Map<String, String> rParams = new HashMap<String, String>();
//        rParams.put("mk-carrier", "3.0.119.0");
//        rParams.put("adtype", "3.0.119.0");
//        rParams.put("im-plid", "3.0.119.0");
//        rParams.put("d-netType", "3.0.119.0");
//        rParams.put("ts", "3.0.119.0");
//        rParams.put("tz", "3.0.119.0");
//        rParams.put("mk-version", "3.0.119.0");
//        rParams.put("u-id-map", "3.0.119.0");
//        rParams.put("u-rt", "3.0.119.0");
//        rParams.put("u-appbid", "3.0.119.0");
//        rParams.put("u-appver", "3.0.119.0");
//        rParams.put("u-appdnm", "3.0.119.0");
//        rParams.put("mk-ad-slot", "3.0.119.0");
//        rParams.put("format", "3.0.119.0");
//        rParams.put("mk-ads", "3.0.119.0");
//        rParams.put("u-id-adt", "3.0.119.0");
//        rParams.put("u-s-id", "3.0.119.0");
//        rParams.put("d-localization", "3.0.119.0");
//        rParams.put("d-orientation", "3.0.119.0");
//        rParams.put("d-device-screen-size", "3.0.119.0");
//        rParams.put("d-device-screen-density", "3.0.119.0");
//        rParams.put("d-density-dependent-screen-size", "3.0.119.0");
//        rParams.put("d-textsize", "3.0.119.0");
//        rParams.put("u-appIS", "3.0.119.0");
//        rParams.put("sdk-collected", "3.0.119.0");
//        rParams.put("loc-allowed", "3.0.119.0");
//        rParams.put("c-sid", "3.0.119.0");
//        rParams.put("c-ap-bssid", "3.0.119.0");
//        rParams.put("v-ap-bssid", "3.0.119.0");
//        rParams.put("v-sc", "3.0.119.0");
//        rParams.put("c-sc", "3.0.119.0");
//        rParams.put("u-latlong-accu", "3.0.119.0");
//        rParams.put("u-ll-ts", "3.0.119.0");

//        Map<String, String> rParams = new HashMap<String, String>();
//        rParams.put("abc", " def  ");
//        banner.setExtras(rParams);
//
//        banner.setKeywords(null);
//        banner.setAnimationType(AnimationType.ROTATE_HORIZONTAL_AXIS);
//        banner.disableHardwareAcceleration();
//
//        banner.setEnableAutoRefresh(true);
//        banner.setRefreshInterval(20);
//
//        banner.load();
//        banner.load();
//
//  banner.load();
    }

    public void loadBanner(View v) {

        final long slotId = Long.parseLong(mSlotEdit.getText().toString());
        android.util.Log.v("Dipal", "Text changed to: " + slotId);
//
//        InMobiSdk.removeIdType(InMobiSdk.ImIdType.LOGIN);
//        InMobiSdk.removeIdType(InMobiSdk.ImIdType.SESSION);
//
//        banner = new InMobiBanner(this, 0L);

//        parentLayout.removeAllViews();
//        float density = getResources().getDisplayMetrics().density;
//        int width = (int) (320 * density);
//        int height = (int) (50 * density);
//        parentLayout.addView(banner, new LinearLayout.LayoutParams(width, height));

//        banner.setListener(bannerListener);
//        banner.setEnableAutoRefresh(false);
//        banner.load();

//        Runnable r = new Runnable() {
//            public void run() {
//                Log.v("dipal","ad count: "+AdDao.getInstance().getAdCount(slotId, String.valueOf(0)));
//                AdDao.getInstance().deleteExpiredAds("banner",0);
//            }
//        };
//
//        for (int i=0;i<100;i++) {
//            Log.v("dipal","i: "+i);
//            new Thread(r).start();
//        }

//        for (int i=0;i<100;i++) {
////            Log.v("dipal","requesting db here");
//            Log.v("dipal","ad count: "+AdDao.getInstance().getAdCount(slotId, String.valueOf(0)));
//        }
//        throw new RuntimeException("This is a crash");
//        throw new IOException("I just wanted to throw an exception!");

    }

    public void clearConfig(View v) {
        Log.v("Dipal", "Removing cached config here!");

        com.inmobi.commons.core.configs.ConfigComponent.getInstance().stop();
        waitThread(5000);

        String PreferencesName = "com.im.keyValueStore.config_store";
        SharedPreferences settings = this.getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        waitThread(5000);

        ConfigNetworkRequest.setDefaultConfigUrl("https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=configencryptrequest&time=9809819999&test_id=rootconf_1_adsmisc_0011&");
        com.inmobi.commons.core.configs.ConfigComponent.getInstance().start();

        waitThread(15000);
        Log.v("Dipal", "Restarting!");

        com.inmobi.commons.core.configs.ConfigComponent.getInstance().stop();
        waitThread(5000);

        ConfigNetworkRequest.setDefaultConfigUrl("https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=configencryptrequest&time=9809819999&test_id=rootconf_1_adsmisc_0011&");
        com.inmobi.commons.core.configs.ConfigComponent.getInstance().start();

    }
}