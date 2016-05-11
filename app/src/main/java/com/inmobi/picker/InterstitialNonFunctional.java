package com.inmobi.picker;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiInterstitial.InterstitialAdListener;
import com.inmobi.sdk.InMobiSdk;

import junit.framework.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class InterstitialNonFunctional extends Activity {
    InMobiInterstitial intAd1,intAd2,intAd3;
    int Flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        InMobiSdk.setEducation(InMobiSdk.Education.COLLEGE_OR_GRADUATE);
        InMobiSdk.setEthnicity(InMobiSdk.Ethnicity.AFRICAN_AMERICAN);
        InMobiSdk.setGender(InMobiSdk.Gender.FEMALE);
        InMobiSdk.setHouseHoldIncome(InMobiSdk.HouseHoldIncome.ABOVE_USD_150K);
        InMobiSdk.setAgeGroup(InMobiSdk.AgeGroup.ABOVE_55);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        Location location = new Location("");
        location.setLatitude(34.33);
        location.setLongitude(55.55);
        location.setTime(now.getTime());
        InMobiSdk.setLocation(location);

        InMobiSdk.setAge(34);
        InMobiSdk.setIncome(34555);
        InMobiSdk.setYearOfBirth(1919);

        AdNetworkRequest.setAdServerUrl(TestConstants.AdSlotServer);

        intAd1 = new InMobiInterstitial(this, 1203550003L, intAdListener); //normal interstitial
        intAd2 = new InMobiInterstitial(this, 1203140037L, intAdListener); //video interstitial
        intAd3 = new InMobiInterstitial(this, 1203410034L, intAdListener); //rewarded video interstitial

        intAd1.load();
        Flag++;
    }

    InterstitialAdListener intAdListener = new InterstitialAdListener() {
        @Override
        public void onAdRewardActionCompleted(InMobiInterstitial ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "onAdRewardActionCompleted: " + rewards);
        }

        @Override
        public void onAdDisplayed(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdDisplayed");
        }

        @Override
        public void onAdDismissed(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdDismissed");
            if (Flag %3 ==0) {
                intAd1.load();
                Log.v("Dipal", "Ad 1 Loaded");
            }
            else if (Flag %3 ==1){
                intAd2.load();
                Log.v("Dipal", "Ad 2 Loaded");
            }
            else{
                intAd3.load();
                Log.v("Dipal", "Ad 3 Loaded");
            }
            Flag++;
        }

        @Override
        public void onAdInteraction(InMobiInterstitial ad, Map<Object, Object> params) {
            Log.v("Dipal", "onAdInteraction: " + params);
        }

        @Override
        public void onAdLoadSucceeded(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdLoadSucceeded, ad can be shown now. ");
            if (ad.isReady())
                ad.show();
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
}
