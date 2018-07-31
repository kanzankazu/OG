package com.gandsoft.openguide.support;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

public class DialogUtil {

    public static void generateCustomDialogInfo(Context context, View view) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();
    }

    /*public static void generateCustomAlertDialog(Context context, View view, String title, Boolean twoButton, @Nullable DialogInterface.OnClickListener positiveListener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setTitle(title);
        if(twoButton) {
            builder.setPositiveButton(context.getText(R.string.dialog_confirm_positive), positiveListener != null ? positiveListener : new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        builder.setNegativeButton(context.getText(R.string.dialog_confirm_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }*/
}
