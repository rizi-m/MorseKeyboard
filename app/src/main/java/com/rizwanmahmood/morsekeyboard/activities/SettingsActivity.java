package com.rizwanmahmood.morsekeyboard.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rizwanmahmood.morsekeyboard.R;
import com.rizwanmahmood.morsekeyboard.fragments.BinaryConfirmFragment;
import com.rizwanmahmood.morsekeyboard.fragments.LevelChooserFragment;
import com.rizwanmahmood.morsekeyboard.scoredatabase.ScoreDBHandler;

public class SettingsActivity extends AppCompatActivity implements BinaryConfirmFragment.Confirmer, LevelChooserFragment.Chooser{

    private Button resetScoreButton;
    private Button resetLevelScoreButton;

    private int buttonId;
    private int level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialise();
        setup();
    }

    private void initialise() {
        resetScoreButton = (Button) findViewById(R.id.settings_button_resetscore);
        resetLevelScoreButton = (Button) findViewById(R.id.settings_button_resetlevelscore);
    }

    private void setup() {
        resetScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BinaryConfirmFragment confirmDialog = new BinaryConfirmFragment();
                confirmDialog.show(getFragmentManager(), "resetScore");
                buttonId = resetScoreButton.getId();
            }
        });
        resetLevelScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelChooserFragment levelChooserFragment = new LevelChooserFragment();
                levelChooserFragment.show(getFragmentManager(), "resetLevelScore");
                buttonId = resetLevelScoreButton.getId();
            }
        });
    }

    @Override
    public void onYes() {
        if(buttonId == resetScoreButton.getId()) {
            resetScore();
        } else if (buttonId == resetLevelScoreButton.getId()) {
            resetLevelScore();
        }
    }

    private void resetLevelScore() {
        ScoreDBHandler db = new ScoreDBHandler(this);
        db.removeScoreForLevel(level);
        db.close();
    }

    private void resetScore() {
        ScoreDBHandler db = new ScoreDBHandler(this);
        db.removeAll();
        db.close();
    }

    @Override
    public void onNo() {
    }

    @Override
    public void onChoose(int level) {
        this.level = level;
        BinaryConfirmFragment confirmDialog = new BinaryConfirmFragment();
        confirmDialog.show(getFragmentManager(), "resetLevelScore");
    }
}
