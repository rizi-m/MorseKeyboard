package com.rizwanmahmood.morsekeyboard.model;

import android.content.Context;
import android.util.Log;

import com.rizwanmahmood.morsekeyboard.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

//class to generate problems
public class ProblemGenerator {

    private static String lowercase = "abcdefghijglmnopqrstuvwxyz";
    private static String numbers = "0123456789";
    private static String special = ".,/@";
    private static String alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static ArrayList<String> shortWords;
    private static ArrayList<String> mediumWords;
    private static ArrayList<String> longWords;


    private static Random random = new Random();



    private static void loadShortWords(Context context) {
        if(shortWords == null) {
            shortWords = new ArrayList<>();
            InputStream shortWordsFile = context.getResources().openRawResource(R.raw.words_short);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(shortWordsFile));
                String line;
                while((line = bufferedReader.readLine())!= null) {
                    shortWords.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void loadMediumWords(Context context) {
        if(mediumWords == null) {
            mediumWords = new ArrayList<>();
            InputStream mediumWordsFile = context.getResources().openRawResource(R.raw.words_medium);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mediumWordsFile));
                String line;
                while((line = bufferedReader.readLine())!= null) {
                    mediumWords.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void loadLongWords(Context context) {
        if(longWords == null) {
            longWords = new ArrayList<>();
            InputStream longWordsFile = context.getResources().openRawResource(R.raw.word_long);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(longWordsFile));
                String line;
                while((line = bufferedReader.readLine())!= null) {
                    longWords.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public static Problem generate(Context context, int level) {
        loadShortWords(context);
        loadMediumWords(context);
        loadLongWords(context);
        switch (level) {
            case 1: return level1();
            case 2: return level2();
            case 3: return level3();
            case 4: return level4();
            case 5: return level5();
            case 6: return level6();
            case 7: return level7();
            case 8: return level8();
            case 9: return level9();
        }
        return new Problem("");
    }

    //very basic problems with just lower case letters
    private static Problem level1() {
        String word = String.valueOf(lowercase.charAt(random.nextInt(lowercase.length())));
        return new Problem(word);
    }

    //very basic problems with just upper case letters
    private static Problem level2() {
        String word = String.valueOf(lowercase.toUpperCase().charAt(random.nextInt(lowercase.length())));
        return new Problem(word);
    }

    //simple problems with 2 character lower case problems
    private static Problem level3() {
        String word = "";
        for(int i = 0; i < 2; i++) {
            word += String.valueOf(lowercase.charAt(random.nextInt(lowercase.length())));
        }
        return new Problem(word);
    }

    //problems containing 1 digit number
    private static Problem level4() {
        String word = String.valueOf(numbers.charAt(random.nextInt(numbers.length())));
        return new Problem(word);
    }

    //problems containing 1 special character
    private static Problem level5() {
        String word = String.valueOf(special.charAt(random.nextInt(special.length())));
        return new Problem(word);
    }

    //problems with upper and lowercase characters ranging from 2 - 3 characters
    private static Problem level6() {
        String word = "";
        int length = (random.nextBoolean()) ? 2 : 3;
        for(int i = 0; i < length; i++) {
            word += String.valueOf( lowercase.concat( lowercase.toUpperCase() ).charAt( random.nextInt( lowercase.length()*2 ) ) );
        }
        return new Problem(word);
    }

    //problems with short words
    private static Problem level7() {
        return new Problem( shortWords.get( random.nextInt( shortWords.size() ) ) );
    }


    //problems with alphanumeric characters ranging from 3-5 characters
    private static Problem level8() {
        String word = "";
        int length = 3 + random.nextInt(3);
        for(int i = 0; i < length; i++) {
            word += String.valueOf( alphanumeric.charAt( random.nextInt( alphanumeric.length() ) ) );
        }
        return new Problem(word);
    }

    //problems with medium length words
    private static Problem level9() {
        return new Problem( mediumWords.get( random.nextInt( mediumWords.size() ) ) );
    }




}
