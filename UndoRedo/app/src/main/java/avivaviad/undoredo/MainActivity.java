package avivaviad.undoredo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    UndoRedoEditText editText;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private ClipboardManager.OnPrimaryClipChangedListener clipListener = new ClipboardManager.OnPrimaryClipChangedListener(){
        @Override
        public void onPrimaryClipChanged(){
            ClipData.Item item = myClipboard.getPrimaryClip().getItemAt(0);

            CharSequence ptext = item.getText();
            editText.setText(ptext);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        editText = new UndoRedoEditText(this);
        editText.initialFirstInput("a");
        editText = (UndoRedoEditText) findViewById(R.id.editText3);

        editText.setTextIsSelectable(true);
        ActionMode.Callback actionMode = new ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                menu.removeItem(android.R.id.shareText);
                menu.removeItem(android.R.id.cut);

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu, menu);
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
// Gets the ID of the "paste" menu item
             /*   MenuItem mPasteItem = menu.findItem(R.id.action_copy);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// If the clipboard doesn't contain data, disable the paste menu item.
// If it does contain data, decide if you can handle the data.
                if (!(clipboard.hasPrimaryClip())) {

                    mPasteItem.setEnabled(false);

                } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {

                    // This disables the paste menu item, since the clipboard has data but it is not plain text
                    mPasteItem.setEnabled(false);
                } else {

                    // This enables the paste menu item, since the clipboard contains plain text.
                    mPasteItem.setEnabled(true);
                }*/
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {
                    case 1:
                     /*   int min = 0;
                        int max = editText.getText().length();
                        if (editText.isFocused()) {
                            final int selStart = editText.getSelectionStart();
                            final int selEnd = editText.getSelectionEnd();

                            min = Math.max(0, Math.min(selStart, selEnd));
                            max = Math.max(0, Math.max(selStart, selEnd));
                        }
                        // Perform your definition lookup with the selected text
                        final CharSequence selectedText = editText.getText()
                                .subSequence(min, max);
                        String text = selectedText.toString();

                        myClip = ClipData.newPlainText("text", text);
                        myClipboard.setPrimaryClip(myClip);
                        Toast.makeText(getApplicationContext(), "Text Copied",
                                Toast.LENGTH_SHORT).show();
                        // Finish and close the ActionMode*/
                        mode.finish();
                        return true;
                    case R.id.action_paste:
                        pasteText();
                        mode.finish();
                        return true;
                    case R.id.action_copy:
                        copyText();
                        mode.finish();
                        return true;
                    case R.id.action_undo:
                        undoEditText();
                        mode.finish();
                        return true;
                    case R.id.action_redo:
                        redoEditText();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }
        };
        editText.setCustomSelectionActionModeCallback(actionMode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setCustomInsertionActionModeCallback(actionMode);
        }

    }

    private void redoEditText() {
        Log.d("afterRedo",editText.getLastRedo()+"");
    }

    private void undoEditText() {
        Log.d("afterUndo",editText.getLastUndo()+"");
    }

    private void copyText() {
        ClipboardManager clipboardManager = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        CharSequence selectedTxt =  editText.getText().subSequence(editText.getSelectionStart(), editText.getSelectionEnd());
        ClipData clipData = ClipData.newPlainText("T", selectedTxt);
        clipboardManager.setPrimaryClip(clipData);
    }

    private void pasteText() {
        ClipboardManager clipboardManager = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        if(clipboardManager.hasPrimaryClip()) {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            CharSequence ptext = item.getText();
           CharSequence previewsText = editText.getText().toString();
            int startIndicator = editText.getSelectionStart();
            int endIndicator = editText.getSelectionEnd();
            String before = (String) previewsText.subSequence(0,startIndicator);
            String after = (String) previewsText.subSequence(endIndicator,previewsText.length());
            editText.setText(before+ptext+after);
        }
    }



}