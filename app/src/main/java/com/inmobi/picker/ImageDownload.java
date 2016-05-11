package com.inmobi.picker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inmobi.ads.InMobiCustomNative;
import com.inmobi.ads.InMobiNative;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageDownload extends Activity implements OnClickListener {
    static Button install;
    static ImageView iv;
    static TextView title;
    static TextView desc;
    static ImageView thumbnail;
    static ImageView screenshot;
    static String parseTitle;
    static String parseDesc;
    static String thumbnailurl;
    static String imageurl;
    static String landingPage;
    static String cta_install;
    static Map<String, String> params;
    Bitmap image1;
    Bitmap image2;
    ProgressDialog pd;
    LinearLayout layoutView = null;
    LinearLayout impressionView = null;

    public static void parseData(String pubContent) {
        Log.v("Dipal", "Pubcontent to be parsed: " + pubContent);


        JSONObject jObject = null;
        try {
            jObject = new JSONObject(pubContent);

            Log.v("Dipal", "Pubcontent JSON: " + jObject);

            if (jObject.has("image_xhdpi")) {
                JSONObject imageObj = jObject.getJSONObject("image_xhdpi");
                imageurl = imageObj.getString("url");
            } else if (jObject.has("image")) {
                JSONObject imageObj = jObject.getJSONObject("image");
                imageurl = imageObj.getString("url");
            } else
                imageurl = null;

            if (jObject.has("icon_xhdpi")) {
                JSONObject iconObj = jObject.getJSONObject("icon_xhdpi");
                thumbnailurl = iconObj.getString("url");
            } else if (jObject.has("icon")) {
                JSONObject iconObj = jObject.getJSONObject("icon");
                thumbnailurl = iconObj.getString("url");
            } else
                thumbnailurl = null;

            parseTitle = jObject.getString("title");

            if (jObject.has("subtitle")) {
                parseDesc = jObject.getString("subtitle");
            } else
                parseDesc = null;

            if (jObject.has("click_url")) {
                landingPage = jObject.getString("click_url");
            } else if (jObject.has("landingURL")) {
                landingPage = jObject.getString("landingURL");
            } else
                landingPage = null;

            if (jObject.has("cta_install")) {
                cta_install = jObject.getString("cta_install");
            } else if (jObject.has("cta")) {
                cta_install = jObject.getString("cta");
            } else
                cta_install = null;

            Log.v("Dipal", "The imageurl parsed is: " + imageurl);
            Log.v("Dipal", "The thumbnailurl parsed is: " + thumbnailurl);
            Log.v("Dipal", "The Landing url parsed is: " + landingPage);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        title = (TextView) findViewById(R.id.testTitle);
        desc = (TextView) findViewById(R.id.testDesc);
        thumbnail = (ImageView) findViewById(R.id.testThumbnail);
        screenshot = (ImageView) findViewById(R.id.testImage);

        install = (Button) findViewById(R.id.install);
        install.setOnClickListener(this);
        install.setVisibility(View.GONE);


        pd = new ProgressDialog(this);
        pd.setMessage("Loading..");
        if (getIntent().getStringExtra("AppId").equalsIgnoreCase("first")) {
            if ((Native.pubContent == null)) {
                Log.v("Dipal", "PubContent Not Received - try loading the ad");
            } else {
                parseData(Native.pubContent);
                Log.v("Dipal", "pubContent Received in ImageDownload Activity is: " + Native.pubContent);
                try {
                    new TheTask().execute();
                } catch (Exception e) {
                    Log.v("Dipal", "Exception in setting the image view: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            if ((NativeCustom.pubContentCustom == null)) {
                Log.v("Dipal", "no  publisher content - try loading the ad");
            } else {
                parseData(NativeCustom.pubContentCustom);
                Log.v("Dipal", "pubContent Received in ImageDownload Activity is" + Native.pubContent);
                try {
                    new TheTask().execute();
                } catch (Exception e) {
                    Log.v("Dipal", "Exception in setting the image view" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        //MainActivity.nativead.detachFromView();
        super.onDestroy();
    }

    private Bitmap downloadBitmap(String urlStr) {
        // initilize the default HTTP client object
        Log.v("Dipal", "Image to be downloaded here: " + urlStr);
        Bitmap image = null;
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            image = BitmapFactory.decodeStream(input);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        params = new HashMap<String, String>();
//        params.put("key1", "value1");
//        params.put("", "");
//        params.put("key2", "value2");
//        params.put("", "");
//        params.put("key3", "value3");
//        params.put("", "");
        params.put("key", "doubleClick");
        Log.v("Dipal", "============Click Intiated");
        //layoutView.setVisibility(View.VISIBLE);


//            Native.nativead.reportAdClick(null);
        if (getIntent().getStringExtra("AppId").equalsIgnoreCase("first")) {
            Log.v("Dipal", "============Click Intiated for first app");
            Native.nativead.reportAdClickAndOpenLandingPage(params);
            Native.nativead.reportAdClick(null);
        } else if (getIntent().getStringExtra("Click").equalsIgnoreCase("ClickJs")) {
            Log.v("Dipal", "===========Click processing in JS");
            String bcn = "im_1827_recordEvent(8)";
            NativeCustom.nativeadCustom.reportAdClickAndOpenLandingPage(bcn, null);
        } else if (getIntent().getStringExtra("Click").equalsIgnoreCase("ClickUrl")) {
            Log.v("Dipal", "===========Click processing in Url");
            URL url = null;
            try {
                url = new URL("http://inmobi.com/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            NativeCustom.nativeadCustom.reportAdClickAndOpenLandingPage(url, null);
        }

//		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//				Uri.parse(landingPage));
//				startActivity(browserIntent);

    }

    class TheTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Log.v("Dipal", "fetching images in background!");
            try {
                //Thread.sleep(6000);
                if (imageurl != null)
                    image1 = downloadBitmap(imageurl);
                if (thumbnailurl != null)
                    image2 = downloadBitmap(thumbnailurl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v("Dipal", "fetching images in done!");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();

            if (image2 != null) {
                thumbnail.setImageBitmap(image2);
                Log.v("Dipal", "thumbnail is set");
            }
            else
                Log.v("Dipal", "thumbnail bitmap is null");
            if (image1 != null) {
                screenshot.setImageBitmap(image1);
                Log.v("Dipal", "Image is set");
            }
            else
                Log.v("Dipal", "image bitmap is null");

            layoutView = (LinearLayout) screenshot.getParent();
            //layoutView.setVisibility(View.INVISIBLE);

            impressionView = (LinearLayout) findViewById(R.id.imageView2);

            if (getIntent().getStringExtra("AppId").equalsIgnoreCase("first")) {
                InMobiNative.bind((ViewGroup) screenshot.getParent(), Native.nativead);
            } else if (getIntent().getStringExtra("Impression").equalsIgnoreCase("ImpressionJs")) {
                Log.v("Dipal", "===========Impression processing in JS");
                String bcn = "abc";
                InMobiCustomNative.bind((ViewGroup) screenshot.getParent(), NativeCustom.nativeadCustom, bcn);
            } else if (getIntent().getStringExtra("Impression").equalsIgnoreCase("Impressionurl")) {
                Log.v("Dipal", "===========Impression processing in URl");
                URL url = null;
                try {
                    url = new URL("http://inmobi.com/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InMobiCustomNative.bind((ViewGroup) screenshot.getParent(), NativeCustom.nativeadCustom, url);
            }

            title.setText("TITLE:     " + parseTitle);
            desc.setText("DESCRIPTION:    " + parseDesc);
            install.setText(cta_install);
            install.setVisibility(View.VISIBLE);
            install.setOnClickListener(ImageDownload.this);

        }
    }
}
