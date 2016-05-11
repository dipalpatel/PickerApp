package com.inmobi.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.String.valueOf;

public class InterstitialMenu extends Activity {
    Intent intent = null;
    String adServer = null;
    String appId = null;
    private Spinner spinner1, spinner2, spinner3;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_menu);

        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addListenerOnButton();
    }


    //add items into spinner dynamically
    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        final String[] application = getResources().getStringArray(
                R.array.adserverUrls);
        ArrayList<String> slotList = new ArrayList<String>();
        slotList.addAll(Arrays.asList(application));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);

        final String[] application = getResources().getStringArray(
                R.array.intSlots);
        ArrayList<String> slotList = new ArrayList<String>();
        slotList.addAll(Arrays.asList(application));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }


    //get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(InterstitialMenu.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : " + valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                if (spinner1.getSelectedItem().equals("SleepServer"))
                    adServer = TestConstants.AdSleepServer;
                else if (spinner1.getSelectedItem().equals("ErrorServer"))
                    adServer = TestConstants.AdErrorServer;
                else if (spinner1.getSelectedItem().equals("PlacementServer"))
                    adServer = TestConstants.AdSlotServer;
                else if (spinner1.getSelectedItem().equals("MatrixServer"))
                    adServer = TestConstants.AdSlotServer;
                else if (spinner1.getSelectedItem().equals("E2EServer"))
                    adServer = TestConstants.AdE2EServer;
                else if (spinner1.getSelectedItem().equals("KEGServer"))
                    adServer = TestConstants.AdKEGServer;
                else
                    adServer = TestConstants.AdSlotServer;

                appId = (String) spinner2.getSelectedItem();

                intent = new Intent(InterstitialMenu.this, Interstitial.class);
                intent.putExtra("appid", appId);
                intent.putExtra("adServer", adServer);

                startActivity(intent);

            }

        });

    }
}
