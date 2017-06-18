package com.rizwanmahmood.morsekeyboard.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rizwanmahmood.morsekeyboard.R;

public class MainActivity extends AppCompatActivity {

    private Button practiceButton;
    private Button helpButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        setup();
    }




    //setup everything needed... event etc.
    private void setup() {

        practiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(practiceButton.getContext(), LevelsActivity.class);
                startActivity(intent);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(helpButton.getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsButton.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    //initialise all the items
    private void initialise() {
        practiceButton = (Button) findViewById(R.id.main_button_practice);
        helpButton = (Button) findViewById(R.id.main_button_help);
        settingsButton = (Button) findViewById(R.id.main_button_settings);
    }
}
