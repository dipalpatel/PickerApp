package com.inmobi.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;

import java.util.HashMap;
import java.util.Map;


public class Native extends Activity implements View.OnClickListener {


    public static InMobiNative nativead;
    public static String pubContent;
    private static Button getContent;
    private static Button loadAd;
    private static Button pubimage;
    private static Button click;
    Map<String, String> params = null;


    private InMobiNative.NativeAdListener listnr = new InMobiNative.NativeAdListener() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        String appid = this.getIntent().getExtras().getString("appid");
        Long slotId = Long.valueOf(appid).longValue();

        String adServer = this.getIntent().getExtras().getString("adServer");
        AdNetworkRequest.setAdServerUrl(adServer);

        loadAd = (Button) findViewById(R.id.Load);
        loadAd.setOnClickListener(this);
        getContent = (Button) findViewById(R.id.Content);
        getContent.setOnClickListener(this);
        pubimage = (Button) findViewById(R.id.PubContent);
        pubimage.setOnClickListener(this);
        click = (Button) findViewById(R.id.Click);
        click.setOnClickListener(this);

        nativead = new InMobiNative(slotId, listnr);

        LinearLayout layt = (LinearLayout) findViewById(R.id.testImage);
        nativead.bind(layt, nativead);
        params = new HashMap<String, String>();
        params.put("key", "");
        nativead.setExtras(null);
        nativead.setKeywords(null);


//        params = null;
//        nativead.reportAdClick(params);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        LinearLayout layoutView = null;
        switch (i) {

            case R.id.Load:
                nativead.load();
                break;
            case R.id.Content:
                Log.v("Dipal", "pubContent Received in Native is" + nativead.getAdContent());
                if (nativead.getAdContent() != null) {
                    pubContent = nativead.getAdContent().toString();
                    Log.v("Dipal", "Content Received - Click on PubContent to Render Image");
                    break;
                } else
                    Log.v("Dipal", "Content Not Received  - Check the Ad Status");
                break;
            case R.id.PubContent:
                Intent intent1 = null;
                intent1 = new Intent(this, ImageDownload.class);
                intent1.putExtra("AppId", "first");
                startActivity(intent1);
                break;
            case R.id.Click:
                params = new HashMap<String, String>();
                if (nativead != null) {
                    if (pubContent != null) {
                        ListView listView = (ListView) findViewById(R.id.listImage);
//                        layoutView.setVisibility(View.INVISIBLE);
                        InMobiNative.bind(listView, nativead);
                        nativead.reportAdClickAndOpenLandingPage(params);

                        Log.v("Dipal",
                                "Click Beacons fired - check the logs");

                    } else
                        Log.v("Dipal", "try loading the ad");
                } else
                    Log.v("Dipal", "nativead not initialized - try initialize");

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nativead != null) {
            nativead.resume();
        }
    }

}
