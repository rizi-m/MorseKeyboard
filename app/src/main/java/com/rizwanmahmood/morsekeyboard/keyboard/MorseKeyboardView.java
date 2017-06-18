package com.rizwanmahmood.morsekeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.util.List;


public class MorseKeyboardView extends KeyboardView {

    public MorseKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPreviewEnabled(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#448aff"));
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for(Keyboard.Key key: keys) {
            if(key.label != null) {
                float textSize = 40;
                paint.setTextSize(textSize);
                canvas.drawText(key.label.toString(), key.x + (key.width/2) + (textSize/2), key.y + (textSize) + (key.height/2), paint);
            }
        }

    }
}
