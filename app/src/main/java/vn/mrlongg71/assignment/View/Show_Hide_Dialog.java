package vn.mrlongg71.assignment.View;

import android.app.ProgressDialog;

public class Show_Hide_Dialog {
    public static void showProgressDialogWithTitle(String substring, ProgressDialog progressDialog) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(substring);
        progressDialog.show();
    }

    public static void hideProgressDialogWithTitle(ProgressDialog progressDialog) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }
}
