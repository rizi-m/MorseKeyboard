package com.rizwanmahmood.morsekeyboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rizwanmahmood.morsekeyboard.activities.HelpActivity;
import com.rizwanmahmood.morsekeyboard.activities.LevelsActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLearn;
    Button btnPractice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        setup();
    }

    //setup everything needed... event etc.
    private void setup() {

        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(btnLearn.getContext(), LevelsActivity.class);
                startActivity(intent);
            }
        });

        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(btnPractice.getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    //initialise all the items
    private void initialise() {
        btnLearn = (Button) findViewById(R.id.main_btn_practice);
        btnPractice = (Button) findViewById(R.id.main_btn_help);
    }
}
