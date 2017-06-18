package com.rizwanmahmood.morsekeyboard.problem;

import java.util.ArrayList;

//the problem that the user is going to solve
public class Problem {

    private String word;
    private ArrayList<String> hint;

    //constructor of problem can only be accessed by package
    Problem(String word) {
        this.word = word;
        hint = RomanToMorseConverter.convert(word);
    }

    public boolean solve(String word) {
        return word.equals(this.word);
    }

    public ArrayList<String> getHint() {
        return hint;
    }

    public String getWord() {
        return word;
    }
}
