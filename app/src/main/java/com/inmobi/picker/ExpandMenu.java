package com.inmobi.picker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.valueOf;

public class ExpandMenu extends Activity implements
        OnItemSelectedListener, View.OnClickListener {
    Intent intent = null;
    String adServer = null;
    String appId = null;
    LinearLayout layout;
    TextView spinnerText1, spinnerText2, spinnerText3;
    private Spinner spinner1, spinner2, spinner3;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        layout = (LinearLayout) findViewById(R.id.ParentLayout);

        spinnerText1 = new TextView(this);
        spinnerText1.setText("Server:");
        spinnerText1.setLayoutParams(new LinearLayout.LayoutParams(100,
                100));
        layout.addView(spinnerText1);

        spinner1 = new Spinner(this);
        spinner1.setOnItemSelectedListener(this);
        addItemsOnSpinner1();
        layout.addView(spinner1);

        spinnerText2 = new TextView(this);
        spinnerText2.setText("PlacementId:");
        spinnerText1.setLayoutParams(new LinearLayout.LayoutParams(100,
                100));
        layout.addView(spinnerText2);

        spinner2 = new Spinner(this);
        layout.addView(spinner2);

        addSpinner3();

        addSubmitButton();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sp1 = valueOf(spinner1.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();

        if (sp1.contentEquals("SleepServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.ExpProdSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("ErrorServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.ErrorSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("PlacementServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.ExpPhpSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("MatrixServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.ExpMatrixSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("Matrix2PServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.Exp2PMatrixSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("E2EServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.ExpE2ESlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else if (sp1.contentEquals("ProdServer")) {
            final String[] application = getResources().getStringArray(
                    R.array.ExpProdSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        } else {
            final String[] application = getResources().getStringArray(
                    R.array.ExpPhpSlots);
            ArrayList<String> slotList = new ArrayList<String>();
            slotList.addAll(Arrays.asList(application));

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void addItemsOnSpinner1() {

        final String[] application = getResources().getStringArray(
                R.array.adserverUrls);
        ArrayList<String> slotList = new ArrayList<String>();
        slotList.addAll(Arrays.asList(application));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slotList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addSpinner3() {

        spinnerText3 = new TextView(this);
        spinnerText3.setText("AdFlow:");
        spinnerText1.setLayoutParams(new LinearLayout.LayoutParams(50,
                100));
        layout.addView(spinnerText3);

        spinner3 = new Spinner(this);

        List<String> list = new ArrayList<String>();
        list.add("Programmatically");
        list.add("Layout");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);

        layout.addView(spinner3);

    }

    public void addSubmitButton() {
        btnSubmit = new Button(this);
        btnSubmit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnSubmit.setText("Submit");
        btnSubmit.setOnClickListener(this);
        layout.addView(btnSubmit);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(ExpandMenu.this,
                "OnClickListener : " +
                        "\nSpinner 1 : " + valueOf(spinner1.getSelectedItem()) +
                        "\nSpinner 2 : " + valueOf(spinner2.getSelectedItem()) +
                        "\nSpinner 3 : " + valueOf(spinner3.getSelectedItem()),
                Toast.LENGTH_SHORT).show();

        if (spinner3.getSelectedItem().equals("Programmatically")) {
            Toast.makeText(ExpandMenu.this,
                    "Programmatic adview selected!",
                    Toast.LENGTH_SHORT).show();

            if (spinner1.getSelectedItem().equals("SleepServer"))
                adServer = TestConstants.AdSleepServer ;
            else if (spinner1.getSelectedItem().equals("ErrorServer"))
                adServer = TestConstants.AdErrorServer ;
            else if (spinner1.getSelectedItem().equals("PlacementServer"))
                adServer = TestConstants.AdSlotServer ;
            else if (spinner1.getSelectedItem().equals("MatrixServer"))
                adServer = TestConstants.AdMatrixServer ;
            else if (spinner1.getSelectedItem().equals("Matrix2PServer"))
                adServer = TestConstants.AdTwoPServer ;
            else if (spinner1.getSelectedItem().equals("E2EServer"))
                adServer = TestConstants.AdE2EServer ;
            else if (spinner1.getSelectedItem().equals("ProdServer"))
                adServer = TestConstants.AdProdServer ;
            else
                adServer = TestConstants.AdSlotServer ;

            appId = (String) spinner2.getSelectedItem();

            intent = new Intent(ExpandMenu.this, Expandable.class);
            intent.putExtra("appid", appId);
            intent.putExtra("adServer", adServer);
        } else {
            intent = new Intent(ExpandMenu.this, ExpandLayout.class);
        }
        startActivity(intent);
    }
}
