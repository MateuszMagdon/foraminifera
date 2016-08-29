package com.example.mateu_000.foraminifera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import Helpers.SettingsContainer;

public class Main2Activity extends ActionBarActivity {

    private static final double DEG = Math.PI / 180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GoToView(View view) {
        EditText numberOfChambersEditText = (EditText) findViewById(R.id.number_of_chambers);
        EditText translationFactorEditText = (EditText) findViewById(R.id.translation_factor);
        EditText growthFactorEditText = (EditText) findViewById(R.id.growth_factor);
        EditText thicknessGrowthFactorEditText = (EditText) findViewById(R.id.thickness_growth_factor);
        EditText deviationAngleEditText = (EditText) findViewById(R.id.deviation_angle);
        EditText rotationAngleEditText = (EditText) findViewById(R.id.rotation_angle);

        SettingsContainer.numberOfChambers = getValueInt(numberOfChambersEditText, 4);
        SettingsContainer.translationFactor = getValue(translationFactorEditText, 0.8d);
        SettingsContainer.growthFactor = getValue(growthFactorEditText, 1.4d);
        SettingsContainer.thicknessGrowthFactor = getValue(thicknessGrowthFactorEditText, 1.1d);

        SettingsContainer.deviationAngle = getValue(deviationAngleEditText, 30) * DEG;
        SettingsContainer.rotationAngle = getValue(rotationAngleEditText, 50) * DEG;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private double getValue(EditText editText, double defaultValue) {
        double result = defaultValue;
        try {
            result = Double.parseDouble(editText.getText().toString());
        } catch (NumberFormatException e){}
        return result;
    }


    private int getValueInt(EditText editText, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e){}
        return result;
    }
}
