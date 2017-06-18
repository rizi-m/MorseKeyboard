package com.rizwanmahmood.morsekeyboard.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.rizwanmahmood.morsekeyboard.R;
import com.rizwanmahmood.morsekeyboard.problem.ProblemGenerator;

import java.util.ArrayList;


public class LevelChooserFragment extends DialogFragment {

    public static interface Chooser {
        public void onChoose(int level);
    }

    private Context context;
    private Chooser chooser;

    private Spinner levelsSpinner;
    private Button okButton;
    @Override
    public void onAttach(Context context) {
        this.context = context;
        chooser = (Chooser) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        chooser = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_levelchooser, null);
        initialise(view);
        setup();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.LevelChooserAnimation;
        return dialog;
    }

    private void initialise(View view) {
        levelsSpinner = (Spinner) view.findViewById(R.id.levelchooser_spinner_levels);
        okButton = (Button) view.findViewById(R.id.levelchooser_button_ok);
    }

    private void setup() {
        populateSpinner();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooser.onChoose(getSelectedLevel());
                dismiss();
            }
        });
    }

    private void populateSpinner() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 1; i < ProblemGenerator.LEVEL_COUNT; ++i) {
            list.add(i+"");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelsSpinner.setAdapter(adapter);
    }

    private int getSelectedLevel() {
        return Integer.parseInt(levelsSpinner.getSelectedItem().toString());
    }
}
