package com.example.mateu_000.foraminifera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import Helpers.SettingsContainer;

public class SettingsActivity extends ActionBarActivity {

    private static final double DEG = Math.PI / 180;

    private EditText numberOfChambersEditText;
    private EditText translationFactorEditText;
    private EditText growthFactorEditText;
    private EditText thicknessGrowthFactorEditText;
    private EditText deviationAngleEditText;
    private EditText rotationAngleEditText;
    private EditText clippingXEditText;
    private EditText clippingYEditText;
    private EditText clippingZEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        numberOfChambersEditText =  (EditText) findViewById(R.id.number_of_chambers);
        translationFactorEditText = (EditText) findViewById(R.id.translation_factor);
        growthFactorEditText = (EditText) findViewById(R.id.growth_factor);
        thicknessGrowthFactorEditText = (EditText) findViewById(R.id.thickness_growth_factor);

        deviationAngleEditText = (EditText) findViewById(R.id.deviation_angle);
        rotationAngleEditText = (EditText) findViewById(R.id.rotation_angle);

        clippingXEditText = (EditText) findViewById(R.id.clipping_x);
        clippingYEditText = (EditText) findViewById(R.id.clipping_y);
        clippingZEditText = (EditText) findViewById(R.id.clipping_z);

        setHints();
    }

    private void setHints() {
        numberOfChambersEditText.setHint(Integer.toString(SettingsContainer.numberOfChambers));
        translationFactorEditText.setHint(Double.toString(SettingsContainer.translationFactor));
        growthFactorEditText.setHint(Double.toString(SettingsContainer.growthFactor));
        thicknessGrowthFactorEditText.setHint(Double.toString(SettingsContainer.thicknessGrowthFactor));
        deviationAngleEditText.setHint(Double.toString(SettingsContainer.deviationAngle));
        rotationAngleEditText.setHint(Double.toString(SettingsContainer.rotationAngle));
    }

    public void GoToView(View view) {
        SettingsContainer.numberOfChambers = getValueInt(numberOfChambersEditText, SettingsContainer.numberOfChambers);
        SettingsContainer.translationFactor = getValue(translationFactorEditText, SettingsContainer.translationFactor);
        SettingsContainer.growthFactor = getValue(growthFactorEditText, SettingsContainer.growthFactor);
        SettingsContainer.thicknessGrowthFactor = getValue(thicknessGrowthFactorEditText, SettingsContainer.thicknessGrowthFactor);

        SettingsContainer.deviationAngle = getValue(deviationAngleEditText, SettingsContainer.deviationAngle) * DEG;
        SettingsContainer.rotationAngle = getValue(rotationAngleEditText, SettingsContainer.rotationAngle) * DEG;

        SettingsContainer.clippingX = getValue(clippingXEditText, SettingsContainer.clippingX);
        SettingsContainer.clippingY = getValue(clippingYEditText, SettingsContainer.clippingY);
        SettingsContainer.clippingZ = getValue(clippingZEditText, SettingsContainer.clippingZ);


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
