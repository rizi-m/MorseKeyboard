package com.rizwanmahmood.morsekeyboard.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rizwanmahmood.morsekeyboard.R;
import com.rizwanmahmood.morsekeyboard.fragments.LevelEndFragment;
import com.rizwanmahmood.morsekeyboard.problem.Problem;
import com.rizwanmahmood.morsekeyboard.problem.ProblemGenerator;
import com.rizwanmahmood.morsekeyboard.scoredatabase.Score;
import com.rizwanmahmood.morsekeyboard.scoredatabase.ScoreDBHandler;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity implements LevelEndFragment.Resetter{

    private int level;
    private ArrayList<Problem> problems;
    private boolean hintEnabled;
    private int questionCount;
    private int questionNo;
    private int penaltyCount;
    private boolean givenPenalty;
    private boolean solved;

    private EditText input;
    private TextView problemTextView;
    private TextView questionsTextView;
    private TextView hintTextView;
    private TextView levelTextView;
    private Button toggleHintButton;
    private ImageButton nextImageButton;

    private Animation showFadeAnimation;
    private Animation hideFadeAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        initialise();
        setup();
    }

    private void initialise() {
        level = Integer.parseInt( getIntent().getStringExtra("level") );
        givenPenalty = false;
        solved = false;
        hintEnabled = !(level == 2);
        questionNo = 0;
        penaltyCount = 0;
        questionCount = 20;
        input = (EditText) findViewById( R.id.level_editText_input );
        problemTextView = (TextView) findViewById( R.id.level_textview_text);
        hintTextView = (TextView) findViewById( R.id.level_textview_hint );
        questionsTextView = (TextView) findViewById( R.id.level_textview_questions );
        levelTextView = (TextView) findViewById( R.id.levelend_textview_level);
        toggleHintButton = (Button) findViewById( R.id.level_button_hinttoggle );
        hideFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.level_hide_fade);
        showFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.level_show_fade);
        nextImageButton = (ImageButton) findViewById( R.id.level_imagebutton_next );
    }

    private void setup() {


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == problems.get( questionNo ).getWord().length()+1) {
                    s = s.subSequence(1, s.length());
                }
                if(isSolved( String.valueOf(s) )) {
                    //Display congratulations
                    showCongratulations();
                    //Show next button
                    showNextButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        hintTextView.setMovementMethod(new ScrollingMovementMethod());

        questionsTextView.setText(questionNo + 1 + "/" + questionCount);

        toggleHintButton.setEnabled(hintEnabled);
        toggleHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHint();
            }
        });

        loadProblem();
        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( isHintVisible() ) {
                    toggleHint();
                }
                hideNextButton();
                resetInput();
                nextProblem();
                givenPenalty = false;
            }
        });

        nextImageButton.setVisibility(View.INVISIBLE);

        levelTextView.setText("Level " + level);

    }

    public void reset() {
        questionNo = 0;
        givenPenalty = false;
        solved = false;

        questionsTextView.setText(questionNo + 1 + "/" + questionCount);

        loadProblem();

        nextImageButton.setVisibility(View.INVISIBLE);
        levelTextView.setText("Level " + level);

    }

    private void toggleHint() {
        if(isHintVisible() || (isHintVisible() && givenPenalty)) {
            hintTextView.setVisibility(View.GONE);
            hintTextView.startAnimation(hideFadeAnimation);
            toggleHintButton.setText("Show Hint");
        } else {
            hintTextView.startAnimation(showFadeAnimation);
            hintTextView.setVisibility(View.VISIBLE);
            toggleHintButton.setText("Hide Hint");
            if(!givenPenalty && !solved) {
                givenPenalty = true;
                penaltyCount++;
            }
        }
    }

    private void nextProblem() {
        ++questionNo;
        if( questionNo < questionCount ) {
            problemTextView.setText( problems.get( questionNo ).getWord() );
            questionsTextView.setText(questionNo + 1 + "/" + questionCount);
            setHint();
        } else {
            finishLevel();
        }
    }

    private void finishLevel() {
        String highScore = getHighScore();
        saveScoreToDatabase();
        FragmentManager fragmentManager = getFragmentManager();
        LevelEndFragment fragment = new LevelEndFragment();
        Bundle bundle = new Bundle();

        bundle.putString("score", "" + getScore());
        bundle.putString("highscore", highScore);

        fragment.setArguments(bundle);
        fragment.show(fragmentManager, "LevelEnd");
    }

    private int getScore() {
        return questionCount - penaltyCount;
    }

    private String getHighScore() {
        ScoreDBHandler db = new ScoreDBHandler(this);
        return db.getHighScoreForLevel(level);
    }

    private void saveScoreToDatabase() {
        ScoreDBHandler db = new ScoreDBHandler(this);
        Score score = new Score(level, getScore());
        db.addScore(score);
        db.close();
    }

    private void showNextButton() {
        nextImageButton.setEnabled(true);
        nextImageButton.startAnimation(showFadeAnimation);
        nextImageButton.setVisibility(View.VISIBLE);
    }

    private void hideNextButton() {
        nextImageButton.setEnabled(false);
        nextImageButton.setVisibility(View.INVISIBLE);
        nextImageButton.startAnimation(hideFadeAnimation);
    }

    private boolean isHintVisible() {
        return hintTextView.getVisibility() == View.VISIBLE;
    }

    private void loadProblem() {
        problems = ProblemGenerator.generateSet(this, level, questionCount);
        problemTextView.setText( problems.get( questionNo ).getWord() );
        setHint();
    }

    private void setHint() {
        String hintText = "";
        for(int i = 0; i < problems.get( questionNo ).getHint().size(); i++) {
            hintText += problems.get( questionNo ).getHint().get(i);
            if(i != problems.get( questionNo ).getHint().size() - 1) hintText += "\n";
        }
        hintTextView.setText(hintText);
    }

    private boolean isSolved(String s) {
        solved = s.equals( problems.get( questionNo ).getWord() );
        return solved;
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


    @Override
    public void onReset() {
        reset();
    }
}
