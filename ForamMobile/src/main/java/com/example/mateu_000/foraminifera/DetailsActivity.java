package com.example.mateu_000.foraminifera;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Map;

import Helpers.SettingsContainer;
import Metrics.CalculationResult;
import Metrics.MetricsEngine;

public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetDetails(View view) {
        TextView detailsTextView = (TextView) findViewById(R.id.details);

        MetricsEngine metricsEngine = new MetricsEngine(SettingsContainer.foraminifera);
        LinkedList<CalculationResult> results = metricsEngine.CalculateMetrics();

        StringBuilder sb = new StringBuilder();
        for(CalculationResult result : results){
            sb.append(String.format("%s: %.8f \n", result.name, result.value));
            sb.append("");
        }

        detailsTextView.setText(sb.toString());
    }
}
