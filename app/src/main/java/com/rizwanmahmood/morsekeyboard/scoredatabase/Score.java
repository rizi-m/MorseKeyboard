package com.rizwanmahmood.morsekeyboard.scoredatabase;

public class Score {

    private int _level;
    private int _score;

    public Score(int _level, int _score) {
        this._level = _level;
        this._score = _score;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public void set_score(int _score) {
        this._score = _score;
    }

    public int get_level() {
        return _level;
    }

    public int get_score() {
        return _score;
    }
}
