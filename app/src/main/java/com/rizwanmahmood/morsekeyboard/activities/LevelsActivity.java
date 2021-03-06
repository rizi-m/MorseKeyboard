package com.rizwanmahmood.morsekeyboard.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rizwanmahmood.morsekeyboard.R;
import com.rizwanmahmood.morsekeyboard.problem.ProblemGenerator;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setupButtons();
    }

    private void setupButtons() {
        String buttonName = "levels_btn_";
        for(int i = 1; i <= ProblemGenerator.LEVEL_COUNT; i++) {
            final Button levelButton = (Button) findViewById( getResources().getIdentifier(buttonName + i, "id", getPackageName()) );
            final String level = String.valueOf(i);
            levelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(levelButton.getContext(), LevelActivity.class);
                    intent.putExtra("level", level);
                    startActivity(intent);
                }
            });
        }
    }
}
