package com.rizwanmahmood.morsekeyboard.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rizwanmahmood.morsekeyboard.R;


public class BinaryConfirmFragment extends DialogFragment {

    public static interface Confirmer {
        public void onYes();
        public void onNo();
    }

    private Confirmer confirmer;

    private Button yesButton;
    private Button noButton;


    @Override
    public void onAttach(Context context) {
        confirmer = (Confirmer) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        confirmer = null;
        super.onDetach();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, null);
        initialise(view);
        setup();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        if(getTag().equals("resetLevelScore")) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.BinaryConfirmAnimation;
        }
        return dialog;
    }

    private void initialise(View view) {
        yesButton = (Button) view.findViewById(R.id.confirm_button_yes);
        noButton = (Button) view.findViewById(R.id.confirm_button_no);
    }


    private void setup() {
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmer.onYes();
                dismiss();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmer.onNo();
                dismiss();
            }
        });
    }



}
