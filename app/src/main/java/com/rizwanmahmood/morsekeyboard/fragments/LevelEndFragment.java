package com.rizwanmahmood.morsekeyboard.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rizwanmahmood.morsekeyboard.R;

public class LevelEndFragment extends DialogFragment {

    private String scoreText;
    private String highscoreText;

    private TextView scoreTextView;
    private TextView highscoreTextView;
    private TextView newHighscoreTextView;

    private ImageButton resetImageButton;
    private ImageButton exitImageButton;

    private boolean isNewHighscore;

    public static interface Resetter {
        public void onReset();
    }

    private Resetter resetter;
    private Activity activity;

    @Override
    public void onAttach(Context context) {
        resetter = (Resetter) context;
        activity = (Activity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        resetter = null;
        resetter = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_levelend, null);
        initialise(view);
        setup();
        return view;
    }

    private void initialise(View view) {
        String score = getArguments().getString("score");
        String highscore = getArguments().getString("highscore");
        isNewHighscore = (Integer.parseInt(score) > Integer.parseInt(highscore));
        scoreText = "Score: " + score;
        highscoreText = "High Score: " + highscore;
        scoreTextView = (TextView) view.findViewById( R.id.levelend_textview_score );
        highscoreTextView = (TextView) view.findViewById( R.id.levelend_textview_highscore );
        newHighscoreTextView = (TextView) view.findViewById( R.id.levelend_textview_newhigh );
        resetImageButton = (ImageButton) view.findViewById( R.id.levelend_imagebutton_reset );
        exitImageButton = (ImageButton) view.findViewById( R.id.levelend_imagebutton_exit );
    }

    private void setup() {
        scoreTextView.setText(scoreText);
        highscoreTextView.setText(highscoreText);
        if(isNewHighscore) {
            newHighscoreTextView.setVisibility(View.VISIBLE);
        } else {
            newHighscoreTextView.setVisibility(View.GONE);
        }
        resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetter.onReset();
                dismiss();
            }
        });

        exitImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                dismiss();
            }
        });
    }

}
