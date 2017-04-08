package com.rizwanmahmood.morsekeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.rizwanmahmood.morsesignal.Signal;
import com.rizwanmahmood.morsesignal.SignalConverter;
import com.rizwanmahmood.timer.TapTimer;

public class MorseKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private MorseKeyboardView keyboardView;
    private Keyboard keyboard;

    //keeps track of time for when the person touches the tap button
    private TapTimer tapStartTimer = new TapTimer();
    //keeps track of time in between taps :)
    private TapTimer tapEndTimer = new TapTimer();

    private SignalConverter signalConverter = new SignalConverter();

    //check is person has started typing
    private boolean tapped = false;
    //checks if a backsapce has been used
    private boolean backspaceUsed = false;
    //keeps track of whether we need to add another character
    private boolean addNewChar = true;

    //stores the typing speed setting of the user
    private int typingSpeed;

    @Override
    public View onCreateInputView() {
        keyboardView = (MorseKeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.telegraph);
        keyboardView.setKeyboard(new Keyboard(this, R.xml.telegraph));
        keyboardView.setOnKeyboardActionListener(this);
        typingSpeed = Signal.MEDIUM;
        return keyboardView;
    }

    //sound stuff
    private void playClick(int keyCode) {

    }

    /**
     * Called when the user presses a key. This is sent before the {@link #onKey} is called.
     * For keys that repeat, this is only called once.
     *
     * @param primaryCode the unicode of the key being pressed. If the touch is not on a valid
     *                    key, the value will be zero.
     */
    @Override
    public void onPress(int primaryCode) {
        if(primaryCode == 1){
            //what to do if previously tapped (what to do after firs tap)
            if(tapped) {
                //stop the timer that started at the end of the last tap
                long signal = tapEndTimer.stop();
                String signalString = Signal.checkSignal(signal, typingSpeed);
                //if the time between the current tap and the previous tap is equal to a dash or the backspace has been usedthen we need to add another letter to the input
                if(signalString.equals(Signal.DASH) || backspaceUsed) {
                    addNewChar = true;
                    signalConverter.reset();
                } else if (signalString.equals(Signal.SPACE)) {
                    //if it is space then set up for next character but insert a space in the input
                    addNewChar = true;
                    signalConverter.reset();
                    getCurrentInputConnection().commitText(" ", 1);
                } else addNewChar = false; //otherwise we want to carry on
            }
            //start the timer to keep track of how long we tapped for
            tapStartTimer.start();
            tapped = true;
        }
    }

    /**
     * Called when the user releases a key. This is sent after the {@link #onKey} is called.
     * For keys that repeat, this is only called once.
     *
     * @param primaryCode the code of the key that was released
     */
    @Override
    public void onRelease(int primaryCode) {
        InputConnection inputConnection = getCurrentInputConnection();
        if(primaryCode == 1) {
            //stop the timer which keeps track of the time user user touched the tap button for
            long signal = tapStartTimer.stop();
            //add the signal to the converter
            signalConverter.addSignal(Signal.checkSignal(signal, typingSpeed));
            if(addNewChar) {
                //add a character to the input
                inputConnection.commitText(signalConverter.getCharacter(), 1);
                backspaceUsed = false;
            } else if (!(signalConverter.getCharacter() == null)) {
                //if the current signals make sense show the user what character he is on
                inputConnection.deleteSurroundingText(1, 0);
                inputConnection.commitText(signalConverter.getCharacter(), 1);
            }
            //keep track of how long the user doesn't touch the tap button
            tapEndTimer.start();
        }
    }

    /**
     * Send a key press to the listener.
     *
     * @param primaryCode this is the key that was pressed
     * @param keyCodes    the codes for all the possible alternative keys
     *                    with the primary code being the first. If the primary key code is
     *                    a single character such as an alphabet or number or symbol, the alternatives
     *                    will include other characters that may be on the same key or adjacent keys.
     *                    These codes are useful to correct for accidental presses of a key adjacent to
     */
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        //what to do when backspace button is pressed
        if(primaryCode == Keyboard.KEYCODE_DELETE) {
            inputConnection.deleteSurroundingText(1, 0);
            backspaceUsed = true;
        }
        if(primaryCode == Keyboard.KEYCODE_DONE) {
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            inputConnection.performEditorAction(EditorInfo.IME_ACTION_GO);
            inputConnection.performEditorAction(EditorInfo.IME_ACTION_SEARCH);
            inputConnection.performEditorAction(EditorInfo.IME_ACTION_NEXT);
        }
    }

    /**
     * Sends a sequence of characters to the listener.
     *
     * @param text the sequence of characters to be displayed.
     */
    @Override
    public void onText(CharSequence text) {

    }

    /**
     * Called when the user quickly moves the finger from right to left.
     */
    @Override
    public void swipeLeft() {

    }

    /**
     * Called when the user quickly moves the finger from left to right.
     */
    @Override
    public void swipeRight() {

    }

    /**
     * Called when the user quickly moves the finger from up to down.
     */
    @Override
    public void swipeDown() {

    }

    /**
     * Called when the user quickly moves the finger from down to up.
     */
    @Override
    public void swipeUp() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tapEndTimer.stop();
    }
}
