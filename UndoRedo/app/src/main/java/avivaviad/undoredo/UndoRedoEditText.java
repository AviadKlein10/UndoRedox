package avivaviad.undoredo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Aviad on 16/10/2017.
 */

public class UndoRedoEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private ArrayList<CharSequence> charsList;
    private int actionCounter;
    private static boolean init = false;

    public UndoRedoEditText(Context context) {
        super(context);
    }

    public UndoRedoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public UndoRedoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (init) {
            Log.d("newValue", s + "");
            actionCounter++;
            charsList.add(actionCounter,s);
            for (int i = 0; i < actionCounter; i++) {
                Log.d("lastValues", charsList.get(i) + "");
            }
        } else {
            Log.d("error", "notInit");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public CharSequence getLastUndo() {
        if (actionCounter > 0) {
            CharSequence lastUndoChar = charsList.get(actionCounter);
            actionCounter--;
            return lastUndoChar;
        }
        return "error";
    }

    public CharSequence getLastRedo() {
        if (actionCounter > 0) {
            if(charsList.size()>actionCounter) {
                actionCounter++;
                return charsList.get(actionCounter);
            }
        }
        return "error";
    }

    public void initialFirstInput(String firstValue) {
        charsList = new ArrayList<>();
        charsList.add(0,firstValue);
        actionCounter = 0;
        init = true;
    }

}
