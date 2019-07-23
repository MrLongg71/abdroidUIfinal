package vn.mrlongg71.assignment.View;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;

import vn.mrlongg71.assignment.R;

public abstract class DialogInput {
    public static void dialogInput(Dialog dialog){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_input_reve_expen);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }


}
