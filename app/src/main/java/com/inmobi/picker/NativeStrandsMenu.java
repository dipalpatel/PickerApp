package com.inmobi.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.valueOf;

public class NativeStrandsMenu extends Activity implements
        AdapterView.OnItemSelectedListener, View.OnClickListener {
    TextView spinnerText1, spinnerText2, spinnerText3, spinnerText4;
    Intent intent = null;
    String adServer = null;
    String appId = null;
    LinearLayout layout;
    private Spinner spinner1, spinner2, spinner3, spinner4;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        layout = (LinearLayout) findViewById(R.id.ParentLayout);

        spinnerText4 = new TextView(this);
        spinnerText4.setText("AdFlow:");
        spinnerText4.setLayoutParams(new LinearLayout.LayoutParams(200,
                100));
        layout.addView(spinnerText4);

        spinner4 = new Spinner(this);
        addItemsOnSpinner4();
        layout.addView(spinner4);

        spinnerText1 = new TextView(this);
        spinnerText1.setText("Server:");
        spinnerText1.setLayoutParams(new LinearLayout.LayoutParams(200,
                100));
        layout.addView(spinnerText1);

        spinner1 = new Spinner(this);
        spinner1.setOnItemSelectedListener(this);
        addItemsOnSpinner1();
        layout.addView(spinner1);

        addSpinner2();

        spinnerText3 = new TextView(this);
        spinnerText3.setText("PlacementId:");
        spinnerText3.setLayoutParams(new LinearLayout.LayoutParams(200,
                100));
        layout.addView(spinnerText3);

        spinner3 = new Spinner(this);
        layout.addView(spinner3);

        addSubmitButton();
    }

    private void addItemsOnSpinner4() {
        List<String> list = new ArrayList<String>();
        list.add("NativeStrands");
        list.add("StrandsAdapter");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter);

    }

    //add items into spinner dynamically
    public void addItemsOnSpinner1() {

        final String[] application = getResources().getStringArray(
                R.array.adserverUrls);
        ArrayList<String> slotList = new ArrayList<String>();
        slotList.addAll(Arrays.asList(application));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addSpinner2() {

        spinnerText2 = new TextView(this);
        spinnerText2.setText("TestType:");
        spinnerText2.setLayoutParams(new LinearLayout.LayoutParams(200,
                100));
        layout.addView(spinnerText2);

        spinner2 = new Spinner(this);
        spinner2.setOnItemSelectedListener(this);

        List<String> list = new ArrayList<String>();
        list.add("AssetStyle");
        list.add("AssetValue");
        list.add("VastTag");
        list.add("WrapperTag");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        layout.addView(spinner2);

    }

    public void addSubmitButton() {
        btnSubmit = new Button(this);
        btnSubmit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnSubmit.setText("Submit");
        btnSubmit.setOnClickListener(this);
        layout.addView(btnSubmit);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sp1 = valueOf(spinner2.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();

        if (sp1.contentEquals("AssetStyle")) {
            Log.v("Dipal", "AssetStyle creative");
            final String[] application = getResources().getStringArray(
                    R.array.MVBAssetStyle);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner3.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("AssetValue")) {
            Log.v("Dipal", "AssetValue creative");
            final String[] application = getResources().getStringArray(
                    R.array.MVBAssetValue);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner3.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("VastTag")) {
            Log.v("Dipal", "VastTag creative");
            final String[] application = getResources().getStringArray(
                    R.array.MVBVastTag);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner3.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("WrapperTag")) {
            Log.v("Dipal", "WrapperTag creative");
            final String[] application = getResources().getStringArray(
                    R.array.MVBWrapperTag);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner3.setAdapter(dataAdapter);
        } else {
            final String[] application = getResources().getStringArray(
                    R.array.MVBAssetValue);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner3.setAdapter(dataAdapter);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(NativeStrandsMenu.this,
                "OnClickListener : " +
                        "\nSpinner 1 : " + valueOf(spinner1.getSelectedItem()) +
                        "\nSpinner 2 : " + valueOf(spinner2.getSelectedItem()) +
                        "\nSpinner 3 : " + valueOf(spinner3.getSelectedItem()) +
                        "\nSpinner 4 : " + valueOf(spinner4.getSelectedItem()),
                Toast.LENGTH_SHORT).show();

        if (spinner4.getSelectedItem().equals("NativeStrands")) {
            Toast.makeText(NativeStrandsMenu.this,
                    "Native strands selected!",
                    Toast.LENGTH_SHORT).show();
            intent = new Intent(NativeStrandsMenu.this, NativeListView.class);
        } else {
            Toast.makeText(NativeStrandsMenu.this,
                    "Strands adapter selected!",
                    Toast.LENGTH_SHORT).show();
            intent = new Intent(NativeStrandsMenu.this, NativeStrandsAdapterView.class);
        }

        appId = (String) spinner3.getSelectedItem();
        intent.putExtra("appid", appId);

        if (spinner1.getSelectedItem().equals("SleepServer"))
            adServer = TestConstants.AdSleepServer;
        else if (spinner1.getSelectedItem().equals("ErrorServer"))
            adServer = TestConstants.AdErrorServer;
        else if (spinner1.getSelectedItem().equals("PlacementServer"))
            adServer = TestConstants.AdSlotServer;
        else if (spinner1.getSelectedItem().equals("MatrixServer"))
            adServer = TestConstants.AdMatrixServer;
        else if (spinner1.getSelectedItem().equals("Matrix2PServer"))
            adServer = TestConstants.AdTwoPServer;
        else if (spinner1.getSelectedItem().equals("E2EServer"))
            adServer = TestConstants.AdE2EServer;
        else if (spinner1.getSelectedItem().equals("ProdServer"))
            adServer = TestConstants.AdProdServer;
        else
            adServer = TestConstants.AdSlotServer;
        intent.putExtra("adServer", adServer);

        startActivity(intent);
    }
}
