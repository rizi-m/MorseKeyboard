package com.rizwanmahmood.morsekeyboard.morsesignal;

import java.util.HashMap;

public class SignalConverter {

    private HashMap<String, String> morseToRoman = new HashMap<>();
    private String signal = "";


    public SignalConverter() {
        setup();
    }

    public String addSignal(String signal) {
        if (signal.equals(Signal.SPACE)) signal = Signal.DASH;
        this.signal += signal;
        return this.signal;
    }

    public String getCharacter(){
        return morseToRoman.get(signal);
    }

    public void reset(){
        signal = "";
    }

    private void setup() {
        //Alphabet
        morseToRoman.put("dash", "t");
        morseToRoman.put("dashdash", "m");
        morseToRoman.put("dashdashdash", "o");
        morseToRoman.put("dashdashdot", "g");
        morseToRoman.put("dashdashdotdash", "q");
        morseToRoman.put("dashdashdotdot", "z");
        morseToRoman.put("dashdot", "n");
        morseToRoman.put("dashdotdash", "k");
        morseToRoman.put("dashdotdashdash", "y");
        morseToRoman.put("dashdotdashdot", "c");
        morseToRoman.put("dashdotdot", "d");
        morseToRoman.put("dashdotdotdash", "x");
        morseToRoman.put("dashdotdotdot", "b");
        morseToRoman.put("dot", "e");
        morseToRoman.put("dotdash", "a");
        morseToRoman.put("dotdashdash", "w");
        morseToRoman.put("dotdashdashdash", "j");
        morseToRoman.put("dotdashdashdot", "p");
        morseToRoman.put("dotdashdot", "r");
        morseToRoman.put("dotdashdotdot", "l");
        morseToRoman.put("dotdot", "i");
        morseToRoman.put("dotdotdash", "u");
        morseToRoman.put("dotdotdashdot", "f");
        morseToRoman.put("dotdotdot", "s");
        morseToRoman.put("dotdotdotdash", "v");
        morseToRoman.put("dotdotdotdot", "h");

        //numbers
        morseToRoman.put("dotdashdashdashdash", "1");
        morseToRoman.put("dotdotdashdashdash", "2");
        morseToRoman.put("dotdotdotdashdash", "3");
        morseToRoman.put("dotdotdotdotdash", "4");
        morseToRoman.put("dotdotdotdotdot", "5");
        morseToRoman.put("dashdotdotdotdot", "6");
        morseToRoman.put("dashdashdotdotdot", "7");
        morseToRoman.put("dashdashdashdotdot", "8");
        morseToRoman.put("dashdashdashdashdot", "9");
        morseToRoman.put("dashdashdashdashdash", "0");

        //special characters
        morseToRoman.put("dotdashdotdashdotdash", ".");
        morseToRoman.put("dashdashdotdotdashdash", ",");
        morseToRoman.put("dotdotdashdashdotdot", "?");
        morseToRoman.put("dashdotdotdashdot", "/");
        morseToRoman.put("dotdashdashdotdashdot", "@");

    }
}
