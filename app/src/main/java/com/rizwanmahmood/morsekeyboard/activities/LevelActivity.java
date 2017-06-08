package com.rizwanmahmood.morsekeyboard.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rizwanmahmood.morsekeyboard.R;
import com.rizwanmahmood.morsekeyboard.model.Problem;
import com.rizwanmahmood.morsekeyboard.model.ProblemGenerator;

public class LevelActivity extends AppCompatActivity {

    private int level;
    private Problem problem;

    private TextView problemTextView;
    private EditText input;
    private TextView hintTextView;
    private Button toggleHintButton;

    private Animation hintAnimationShow;
    private Animation hintAnimationHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        init();
        setup();
    }

    private void init() {
        level = Integer.parseInt( getIntent().getStringExtra("level") );
        problemTextView = (TextView) findViewById( R.id.level_textview_text);
        input = (EditText) findViewById( R.id.level_editText_input );
        hintTextView = (TextView) findViewById( R.id.level_textview_hint );
        toggleHintButton = (Button) findViewById( R.id.level_button_hinttoggle );
        hintAnimationHide = AnimationUtils.loadAnimation(this, R.anim.level_hide_hint);
        hintAnimationShow = AnimationUtils.loadAnimation(this, R.anim.level_show_hint);
    }

    private void setup() {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == problem.getWord().length()+1) {
                    s = s.subSequence(1, s.length());
                }
                if(isSolved( String.valueOf(s) )) {
                    //Display congratulations for now
                    showCongratulations();
                    resetInput();
                    loadProblem();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        hintTextView.setMovementMethod(new ScrollingMovementMethod());

        toggleHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHintVisible()) {
                    hintTextView.setVisibility(View.GONE);
                    hintTextView.startAnimation(hintAnimationHide);
                    toggleHintButton.setText("Show Hint");
                } else {
                    hintTextView.startAnimation( hintAnimationShow );
                    hintTextView.setVisibility(View.VISIBLE);
                    toggleHintButton.setText("Hide Hint");
                }
            }
        });

        loadProblem();
    }

    private boolean isHintVisible() {
        return hintTextView.getVisibility() == View.VISIBLE;
    }

    private void loadProblem() {
        problem = ProblemGenerator.generate(this, level);
        problemTextView.setText( problem.getWord() );
        setHint();
    }

    private void setHint() {
        String hintText = "";
        for(int i = 0; i < problem.getHint().size(); i++) {
            hintText += problem.getHint().get(i);
            if(i != problem.getHint().size() - 1) hintText += "\n";
        }
        hintTextView.setText(hintText);
        Log.d("Hint", hintText);
    }

    private boolean isSolved(String s) {
        return s.equals( problem.getWord() );
    }

    private void resetInput() {
        input.setText("");
    }

    private void showCongratulations() {
        Context context = getApplicationContext();
        CharSequence text = "Congratulations";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }


}
