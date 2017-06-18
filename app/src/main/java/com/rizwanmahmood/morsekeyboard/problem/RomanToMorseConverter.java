package com.rizwanmahmood.morsekeyboard.problem;


import java.util.ArrayList;
import java.util.HashMap;

class RomanToMorseConverter {
    private static HashMap<String, String> romanToMorse = new HashMap<String, String>(){{
        put("t", "-");
        put("m", "--");
        put("o", "---");
        put("g", "--.");
        put("q", "--.-");
        put("z", "--..");
        put("n", "-.");
        put("k", "-.-");
        put("y", "-.--");
        put("c", "-.-.");
        put("d", "-..");
        put("x", "-..-");
        put("b", "-...");
        put("e", ".");
        put("a", ".-");
        put("w", ".--");
        put("j", ".---");
        put("p", ".--.");
        put("r", ".-.");
        put("l", ".-..");
        put("i", "..");
        put("u", "..-");
        put("f", "..-.");
        put("s", "...");
        put("v", "...-");
        put("h", "....");
        put("1", ".----");
        put("2", "..---");
        put("3", "...--");
        put("4", "....-");
        put("5", ".....");
        put("6", "-....");
        put("7", "--...");
        put("8", "---..");
        put("9", "----.");
        put("0", "-----");
        put(".", ".-.-.-");
        put(",", "--..--");
        put("?", "..--..");
        put("/", "-..-.");
        put("@", ".--.-.");
    }};


    static ArrayList<String> convert(String word) {
        word = word.toLowerCase();
        ArrayList<String> conversion = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            String toAdd = romanToMorse.get(String.valueOf(word.charAt(i)).toLowerCase());
            toAdd = (toAdd == null) ? " " : toAdd;
            conversion.add(toAdd);
        }
        return conversion;
    }
}
