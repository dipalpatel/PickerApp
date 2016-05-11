package com.inmobi.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiCustomNative;
import com.inmobi.ads.InMobiNative;

import java.util.HashMap;
import java.util.Map;


public class NativeCustom extends Activity implements View.OnClickListener {

    public static InMobiCustomNative nativeadCustom;
    public static String pubContentCustom;

    private static Button getContentCustom;

    private static Button loadAdCustom;

    private static Button pubimageCustom;
    private static Button pubimageCustomUrl;
    private static Button clickCustom;
    private static Button clickCustomUrl;

    Map<String, String> params = null;


    private InMobiNative.NativeAdListener listnr = new InMobiNative.NativeAdListener() {
        @Override
        public void onAdLoadSucceeded(InMobiNative ad) {
            Log.v("Dipal", "Ad request succeeded");
        }

        @Override
        public void onAdLoadFailed(InMobiNative ad, InMobiAdRequestStatus status) {
            Log.v("Dipal", "Ad request FAiled the msg is = " + status.getMessage() + "and the status code is " + status.getStatusCode() + "and the actual status is" + status);

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
        setContentView(R.layout.activity_native_custom);

        String appid = this.getIntent().getExtras().getString("appid");
        Long slotId = Long.valueOf(appid).longValue();

        String adServer = this.getIntent().getExtras().getString("adServer");
        AdNetworkRequest.setAdServerUrl(adServer);

        getContentCustom = (Button) findViewById(R.id.ContentCustom);
        getContentCustom.setOnClickListener(this);

        pubimageCustom = (Button) findViewById(R.id.PubContentCustom);
        pubimageCustom.setOnClickListener(this);
        pubimageCustomUrl = (Button) findViewById(R.id.PubContentCustomUrl);
        pubimageCustomUrl.setOnClickListener(this);
        clickCustom = (Button) findViewById(R.id.ClickCustom);
        clickCustom.setOnClickListener(this);
        clickCustomUrl = (Button) findViewById(R.id.ClickCustomUrl);
        clickCustomUrl.setOnClickListener(this);
        loadAdCustom = (Button) findViewById(R.id.LoadCustom);
        loadAdCustom.setOnClickListener(this);

        nativeadCustom = new InMobiCustomNative(slotId, listnr);

        LinearLayout layt = (LinearLayout) findViewById(R.id.testImage);
        InMobiNative.bind(layt, nativeadCustom);
        params = new HashMap<String, String>();
        params.put("key", "");
        nativeadCustom.setExtras(null);
        nativeadCustom.setKeywords(null);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        LinearLayout layoutView = null;
        switch (i) {
            case R.id.LoadCustom:
                nativeadCustom.load();
                break;
            case R.id.ContentCustom:
                Log.v("Dipal", "pubContent Received in Native is" + nativeadCustom.getAdContent());
                if (nativeadCustom.getAdContent() != null) {
                    pubContentCustom = nativeadCustom.getAdContent().toString();

                    Toast.makeText(getApplicationContext(),
                            "Content Received - Click on PubContent to Render Image",
                            Toast.LENGTH_SHORT).show();
                    break;
                } else
                    Toast.makeText(getApplicationContext(),
                            "Content Not Received  - Check the Ad Status",
                            Toast.LENGTH_SHORT).show();
                break;
            case R.id.PubContentCustom:
                Intent intent3 = null;
                intent3 = new Intent(this, ImageDownload.class);
                intent3.putExtra("Impression", "ImpressionJs");
                intent3.putExtra("Click", "ClickJs");
                intent3.putExtra("AppId", "normal");
                Log.v("Dipal", "pubContent Custom called to be parsed");
                startActivity(intent3);
                break;
            case R.id.PubContentCustomUrl:
                Intent intent4 = null;
                intent4 = new Intent(this, ImageDownload.class);
                intent4.putExtra("Impression", "ImpressionUrl");
                intent4.putExtra("Click", "ClickUrl");
                intent4.putExtra("AppId", "normal");
                Log.v("Dipal", "pubContent CustomUrl called to be parsed");
                startActivity(intent4);
                break;
            case R.id.ClickCustom:
                Intent intent5 = null;
                intent5 = new Intent(this, ImageDownload.class);
                intent5.putExtra("Impression", "ImpressionJs");
                intent5.putExtra("Click", "ClickJs");
                intent5.putExtra("AppId", "normal");
                Log.v("Dipal", "Click Custom called to be parsed");
                startActivity(intent5);
                break;
            case R.id.ClickCustomUrl:
                Intent intent6 = null;
                intent6 = new Intent(this, ImageDownload.class);
                intent6.putExtra("Click", "ClickUrl");
                intent6.putExtra("Impression", "ImpressionUrl");
                intent6.putExtra("AppId", "normal");
                Log.v("Dipal", "Click Custom with url called to be parsed");
                startActivity(intent6);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nativeadCustom != null) {
            nativeadCustom.resume();
        }
    }
}
