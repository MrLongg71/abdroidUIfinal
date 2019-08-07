package vn.mrlongg71.assignment.View;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.mrlongg71.assignment.R;

public class CustomToast extends Toast {
    public static int SUCCESS = 1;
    public static int ERROR = 2;

    private static long SHORT = 4000;
    private static long LONG = 7000;

    public CustomToast(Context context) {
        super(context);
    }
    public static Toast makeText(Context context, String message, int duration, int type, boolean androidicon) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        View layout = LayoutInflater.from(context).inflate(R.layout.customtoast_layout, null, false);
        TextView l1 =  layout.findViewById(R.id.toast_text);
        LinearLayout linearLayout =  layout.findViewById(R.id.toast_type);
        ImageView img =  layout.findViewById(R.id.toast_icon);
        ImageView img1 =  layout.findViewById(R.id.imageView4);
        l1.setText(message);
        if (androidicon == true)
            img1.setVisibility(View.VISIBLE);
        else if (androidicon == false)
            img1.setVisibility(View.GONE);
        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.success_shape);
            img.setImageResource(R.drawable.ic_check_black_24dp);
        } else if (type == 2) {
            linearLayout.setBackgroundResource(R.drawable.warning_shape);
            img.setImageResource(R.drawable.ic_close_black_24dp);
        }
        toast.setView(layout);
        return toast;
    }


}

