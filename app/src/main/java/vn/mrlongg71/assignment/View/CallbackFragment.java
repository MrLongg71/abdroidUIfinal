package vn.mrlongg71.assignment.View;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import vn.mrlongg71.assignment.R;

import android.widget.Toast;

import vn.mrlongg71.assignment.Fragment.HomeFragment;
import vn.mrlongg71.assignment.Fragment.RevenueFragment;

public class CallbackFragment {
    public static void callBackFragment(View view, final FragmentManager fragmentManager) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        HomeFragment fragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment, fragment.getTag()).commit();

                        return true;
                    }
                }
                return false;
            }
        });

    }
}
