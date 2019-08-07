package vn.mrlongg71.assignment.Fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import vn.mrlongg71.assignment.Activity.MainActivity;
import vn.mrlongg71.assignment.Model.Thu_Chi_Model;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.View.DialogDate;

public class Sta_Day_Fragment extends Fragment {


    static TextView txtTongChiDay;
    TextView txtTongThuDay;
    static TextView txtNgayHienTai;
    Calendar calendar;
    DatabaseReference mData;
    FirebaseAuth firebaseAuth;
    static String UserID;
    static int tongThuVND = 0;
    static int tongChiVND = 0;
    public static PieChart mChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sta_day, container, false);

        anhxa(view);
        GetDataChi();
        GetDataThu();
        vn.mrlongg71.assignment.View.PieChart.addDataSet(mChart, tongChiVND, tongThuVND,1);
        return view;
    }


    //bên này show chi tiết màn hình này nè

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void GetDataThu() {
        tongThuVND = 0;
        DialogDate.getTime(txtNgayHienTai);
        Cursor dataCV = MainActivity.database.GetData("SELECT * FROM DoanhThu WHERE idUser = '" + UserID + "' AND deleteFlag = 0");
        while (dataCV.moveToNext()) {
            String money = dataCV.getString(2);
            String donviThu = dataCV.getString(3);
            String date = dataCV.getString(6);
            UserID = dataCV.getString(10);
            if (txtNgayHienTai.getText().equals(date)) {
                if (donviThu.equals("VND")) {
                    tongThuVND += Integer.parseInt(money);
                }
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String tongThuVNDFormat = formatter.format(tongThuVND);
                txtTongThuDay.setText(tongThuVNDFormat);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void GetDataChi() {
        tongChiVND = 0;
        DialogDate.getTime(txtNgayHienTai);
        Cursor dataCV1 = MainActivity.database.GetData("SELECT * FROM KhoangChi WHERE idUser = '" + UserID + "' AND deleteFlag = 0");
        while (dataCV1.moveToNext()) {
            String money = dataCV1.getString(2);
            String donviThu = dataCV1.getString(3);
            String date = dataCV1.getString(6);
            UserID = dataCV1.getString(10);
            if (txtNgayHienTai.getText().equals(date)) {
                if (donviThu.equals("VND")) {
                    tongChiVND += Integer.parseInt(money);
                }
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String tongChiVNDFormat = formatter.format(tongChiVND);
                txtTongChiDay.setText(tongChiVNDFormat);
            }
        }
        vn.mrlongg71.assignment.View.PieChart.addDataSet(mChart, tongChiVND, tongThuVND,1);

    }


    private void anhxa(View view) {
        calendar = Calendar.getInstance();
        txtTongChiDay = view.findViewById(R.id.txtChiDay);
        txtTongThuDay = view.findViewById(R.id.txtThuDay);
        txtNgayHienTai = view.findViewById(R.id.txtNgayHientai);
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        UserID = firebaseAuth.getCurrentUser().getUid();
        //
        mChart = (PieChart) view.findViewById(R.id.piechart);


    }


}
