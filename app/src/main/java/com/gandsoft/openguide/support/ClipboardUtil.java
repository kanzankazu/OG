package com.gandsoft.openguide.support;

import android.content.ClipData;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.gandsoft.openguide.App;

/**
 * Created by kanzan on 3/12/2018.
 */

public class ClipboardUtil {

    public static void clipboardCopy(Context context, String s) {
        android.content.ClipboardManager clipMan = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("clip", s);
        clipMan.setPrimaryClip(clipData);
        SystemUtil.showToast(App.getContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT, Gravity.TOP);
    }

    public static String clipboardPaste(Context context) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        return item.getText().toString();
    }
}
