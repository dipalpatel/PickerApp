package com.inmobi.picker;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiInterstitial.InterstitialAdListener;
import com.inmobi.sdk.InMobiSdk;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import com.inmobi.ads.Placement;
//import com.inmobi.ads.PlacementDao;

public class Interstitial extends Activity {
    private static Button load;
    //    InMobiInterstitial intAd, intAd1;
    InMobiInterstitial intAd1;
    InterstitialAdListener intAdListener = new InterstitialAdListener() {
        @Override
        public void onAdRewardActionCompleted(InMobiInterstitial ad, Map<Object, Object> rewards) {
            Log.v("Dipal", "onAdRewardActionCompleted: " + rewards);
        }

        @Override
        public void onAdDisplayed(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdDisplayed");
//            ad.load();
//            intAd.load();
//            intAd1.show();
//            Helper.readMediaFolder(Interstitial.this);
//            Helper.readPlacementIds();
        }

        @Override
        public void onAdDismissed(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdDismissed");
//            Helper.readMediaFolder(Interstitial.this);
//            Helper.readPlacementIds();
        }

        @Override
        public void onAdInteraction(InMobiInterstitial ad, Map<Object, Object> params) {
            Log.v("Dipal", "onAdInteraction: " + params);
        }

        @Override
        public void onAdLoadSucceeded(InMobiInterstitial ad) {
            Log.v("Dipal", "onAdLoadSucceeded, ad can be shown now. ");
            ad.show();
//            intAd.load();
//            if (ad.isReady())
//                ad.show();
//                ad.show(R.anim.right_in_slide_up, R.anim.left_out_slide_down);
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

    private Button mBtnGetIntAd1;
    //    private Button mBtnGetIntAd,mBtnGetIntAd1;
    private Button mBtnShowIntAd1;
    //    private Button mBtnShowIntAd,mBtnShowIntAd1;
    private EditText mSlotEdit;
    private long slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

//        Helper.readMediaFolder(this);
//        Helper.readPlacementIds();

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

        load = (Button) findViewById(R.id.Load);
        mSlotEdit = (EditText) findViewById(R.id.editText1);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        String appid = this.getIntent().getExtras().getString("appid");

        try {
            slotId = Long.valueOf(appid).longValue();
        }
        catch (NumberFormatException e) {
            Log.v("dipal","try again!");
        }
        mSlotEdit.setText(appid);

        String adServer = this.getIntent().getExtras().getString("adServer");
        AdNetworkRequest.setAdServerUrl(adServer);

//        mBtnGetIntAd = (Button) findViewById(R.id.btnGetIntAd);
//        mBtnShowIntAd = (Button) findViewById(R.id.btnShowIntAd);
//		mBtnShowIntAd.setEnabled(false);

        mBtnGetIntAd1 = (Button) findViewById(R.id.btnGetIntAd1);
        mBtnShowIntAd1 = (Button) findViewById(R.id.btnShowIntAd1);

//        intAd = new InMobiInterstitial(this, 1203410006L, intAdListener);
//        intAd1 = new InMobiInterstitial(this, 1203380001L, intAdListener);


//        intAd = new InMobiInterstitial(this, slotId, intAdListener);
        intAd1 = new InMobiInterstitial(this, slotId, intAdListener);
//        intAd = new InMobiInterstitial(this, slotId, intAdListener);
//        intAd = new InMobiInterstitial(null, null,intAdListener);

        Map<String, String> rParams = new HashMap<String, String>();
//        rParams.put("mk-carrier", "3.0.119.0");
//        rParams.put("plaform", "android");
        rParams.put("tp", "c_admob");
        rParams.put("tp_version", "1.0");
        intAd1.setExtras(rParams);

//        InMobiSdk.setEducation(Educa);fe
        intAd1.setKeywords("xyz");
//        intAd.load();
//        intAd1.show();
//        intAd.load();
//        intAd1 = new InMobiInterstitial(this, slotId, intAdListener);
        intAd1.load();
        intAd1.load();

//        Helper.readMediaFolder(this);
//        Helper.readPlacementIds();

    }

    public void onGetInAd1(View view) {
//        intAd1 = new InMobiInterstitial(this, 1203380001L, intAdListener);
        intAd1.load();
    }

    public void onShowInAd1(View view) {
        intAd1.show();
        if (intAd1.isReady()) {
            intAd1.show();
        }
    }

//    public void onGetInAd(View view) {
//        intAd = new InMobiInterstitial(this, 1203410014L, intAdListener);
//        intAd.load();
//    }
//
//    public void onShowInAd(View view) {
//        if (intAd.isReady()) {
//            intAd.show();
////			mBtnGetIntAd.setEnabled(true);
////			mBtnShowIntAd.setEnabled(false);
//        }
//    }

    public void load(View v) {
//        Helper.readMediaFolder(Interstitial.this);
//        Helper.readPlacementIds();
//
//
//        long slotId = Long.parseLong(mSlotEdit.getText().toString());
//        android.util.Log.v("Dipal", "Text changed to: " + slotId);
//        intAd1 = new InMobiInterstitial(this, slotId, intAdListener);
//
//        Map<String, String> rParams = new HashMap<String, String>();
////        rParams.put("mk-carrier", "3.0.119.0");
////        rParams.put("plaform", "android");
//        rParams.put("tp", "c_mopub");
//        rParams.put("tp_version", "1.0");
//        intAd1.setExtras(rParams);

        intAd1.load();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//         if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            // do something on back.
//             android.util.Log.v("Dipal", "Back key pressed first time");
//             Interstitial.this.finish();
//             if (intAd.isReady())
//                intAd.show();
////            return true;
//        }
//        android.util.Log.v("Dipal", "Key pressed");
//        return super.onKeyDown(keyCode, event);
//    }
//    @Override
//    public void onBackPressed() {
//        if (intAd.isReady())
//            intAd.show();
//        super.onBackPressed();
//    }

}
