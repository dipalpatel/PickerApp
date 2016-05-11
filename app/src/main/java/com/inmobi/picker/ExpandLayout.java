package com.inmobi.picker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiBanner.BannerAdListener;
import com.inmobi.ads.InMobiInterstitial;

import java.util.Map;

public class ExpandLayout extends Activity {

    private static Button loadBanner;
    InMobiBanner banner, banner1, banner2, banner3;
    LinearLayout parentLayout;
    InMobiInterstitial intAd;
    InMobiInterstitial.InterstitialAdListener intAdListener = new InMobiInterstitial.InterstitialAdListener() {
        @Override
        public void onAdRewardActionCompleted(InMobiInterstitial ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "Int: onAdRewardActionCompleted" + rewards.toString());
        }

        @Override
        public void onAdDisplayed(InMobiInterstitial ad) {
            Log.v("Dipal", "Int: onAdDisplayed");
        }

        @Override
        public void onAdDismissed(InMobiInterstitial ad) {
            Log.v("Dipal", "Int: onAdDismissed");
        }

        @Override
        public void onAdInteraction(InMobiInterstitial ad, Map<Object, Object> params) {
            Log.v("Dipal", "Int: onAdInteraction" + params.toString());
        }

        @Override
        public void onAdLoadSucceeded(InMobiInterstitial ad) {
            Log.v("Dipal", "Int: onAdLoadSucceeded, ad can be shown now. ");
            if (ad.isReady())
                ad.show();
        }

        @Override
        public void onAdLoadFailed(InMobiInterstitial ad, InMobiAdRequestStatus requestStatus) {
            Log.v("Dipal", "Int: onAdLoadFailed: " + requestStatus.getStatusCode());
        }

        @Override
        public void onUserLeftApplication(InMobiInterstitial ad) {
            Log.v("Dipal", "Int: onUserLeftApplication");
        }
    };
    BannerAdListener bannerListener = new BannerAdListener() {

        @Override
        public void onAdLoadSucceeded(InMobiBanner ad) {
            Log.v("Dipal", "Banner: onAdLoadSucceeded");
        }

        @Override
        public void onAdLoadFailed(InMobiBanner ad, InMobiAdRequestStatus statusCode) {
            Log.v("Dipal", "Banner: onAdLoadFailed: " + statusCode.getStatusCode());
        }

        @Override
        public void onAdDisplayed(InMobiBanner ad) {
            Log.v("Dipal", "Banner: onAdDisplayed");
        }

        @Override
        public void onAdDismissed(InMobiBanner ad) {
            Log.v("Dipal", "Banner: onAdDismissed");
        }

        @Override
        public void onAdInteraction(InMobiBanner ad, Map<Object, Object> params) {
            Log.v("Dipal", "Banner: onAdInteraction, params: " + params.toString());
        }

        @Override
        public void onUserLeftApplication(InMobiBanner ad) {
            Log.v("Dipal", "Banner: onUserLeftApplication");
        }

        @Override
        public void onAdRewardActionCompleted(InMobiBanner ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "Banner: onAdRewardActionCompleted, params: " + rewards.toString());
        }
    };
    BannerAdListener bannerListener1 = new BannerAdListener() {

        @Override
        public void onAdLoadSucceeded(InMobiBanner ad) {
            Log.v("Dipal", "onAdLoadSucceeded1");
        }

        @Override
        public void onAdLoadFailed(InMobiBanner ad, InMobiAdRequestStatus statusCode) {
            Log.v("Dipal", "onAdLoadFailed1: " + statusCode.getStatusCode());
        }

        @Override
        public void onAdDisplayed(InMobiBanner ad) {
            Log.v("Dipal", "onAdDisplayed1");
        }

        @Override
        public void onAdDismissed(InMobiBanner ad) {
            Log.v("Dipal", "onAdDismissed1");
        }

        @Override
        public void onAdInteraction(InMobiBanner ad, Map<Object, Object> params) {
            Log.v("Dipal", "onAdInteraction1, params: " + params.toString());
        }

        @Override
        public void onUserLeftApplication(InMobiBanner ad) {
            Log.v("Dipal", "onUserLeftApplication1");
        }

        @Override
        public void onAdRewardActionCompleted(InMobiBanner ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "onAdRewardActionCompleted1, params: " + rewards.toString());
        }
    };
    private EditText mSlotEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expandable_layout);

        loadBanner = (Button) findViewById(R.id.Load);

//        String adServer = "https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=placementphprequest&time=9809819999&";
//        String adServer = "https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=matrixadencryptrequest&time=9809819999988822&";
//        String adServer = "https://sdk-test-setup.inmobi.com:8080/mockserver/request?request=placementencryptrequest&time=9809819999988822&";
//        String adServer = "http://10.14.118.41:8080/phoenix/phoenix";
//        AdNetworkRequest.setAdServerUrl(adServer);

        android.util.Log.v("Dipal", "onAdInit");
        banner = (InMobiBanner) findViewById(R.id.bannerView);

        banner.setListener(bannerListener);

        banner.setEnableAutoRefresh(true);
//        banner.setRefreshInterval(20);

        banner.load();


//        banner1 = (InMobiBanner)findViewById(R.id.bannerView1);
//
//        banner1.setListener(bannerListener1);
//
//        banner1.setEnableAutoRefresh(false);
////        banner1.setRefreshInterval(20);
//
//        banner1.load();

//        banner2 = (InMobiBanner)findViewById(R.id.bannerView2);
//
//        banner2.setListener(bannerListener);
//
//        banner2.setEnableAutoRefresh(true);
//        banner2.setRefreshInterval(20);
//
//        banner2.load();
//
//        banner3 = (InMobiBanner)findViewById(R.id.bannerView3);
//
//        banner3.setListener(bannerListener);
//
//        banner3.setEnableAutoRefresh(true);
//        banner3.setRefreshInterval(20);
//
//        banner3.load();
//
//        intAd = new InMobiInterstitial(this, 16030001L, intAdListener);
//        intAd = new InMobiInterstitial(this, 1203380001L, intAdListener);
//        intAd.load();


    }

    public void loadBanner(View v) {
        banner.load();
    }

    public void loadBanner1(View v) {
        banner1.load();
    }
}