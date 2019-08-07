package vn.mrlongg71.assignment.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.mrlongg71.assignment.Activity.MainActivity;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.View.DialogDate;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Sta_Month_Fragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    static TextView txtTongChiMonth;
    TextView txtTongThuMonth;
    static TextView txtMonth;
    DatabaseReference mData;
    FirebaseAuth firebaseAuth;
    static String UserID;
    static String thangHientai;
    Spinner spinnerThang;
    static int tongThuVND = 0;
    static int tongChiVND = 0;
    static DecimalFormat formatter = new DecimalFormat("#,###,###");
    String tongThuVNDFormat = "0";
    static String tongChiVNDFormat = "0";
    public static PieChart pieChart;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sta_month, container, false);
        anhxa(view);
        SpinnerThang();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDataThu() {
        tongThuVND = 0;
        Cursor dataCV = MainActivity.database.GetData("SELECT * FROM DoanhThu WHERE idUser = '" + UserID + "' AND deleteFlag = 0");
        while (dataCV.moveToNext()) {
            String money = dataCV.getString(2);
            String donviThu = dataCV.getString(3);
            String date = dataCV.getString(6);
            UserID = dataCV.getString(10);
            if (date.contains(thangHientai)) {
                if (donviThu.equals("VND")) {
                    tongThuVND += Integer.parseInt(money);
                }
            }
        }
        tongThuVNDFormat = formatter.format(tongThuVND);
        txtTongThuMonth.setText(tongThuVNDFormat);
        txtMonth.setText("Tháng "+thangHientai);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void getDataChi() {
        tongChiVND = 0;
        Cursor dataCV = MainActivity.database.GetData("SELECT * FROM KhoangChi WHERE idUser = '" + UserID + "' AND deleteFlag = 0");
        while (dataCV.moveToNext()) {
            String money = dataCV.getString(2);
            String donviThu = dataCV.getString(3);
            String date = dataCV.getString(6);
            UserID = dataCV.getString(10);
            if (date.contains(thangHientai)) {
                if (donviThu.equals("VND")) {
                    tongChiVND += Integer.parseInt(money);
                }
            }
        }
        tongChiVNDFormat = formatter.format(tongChiVND);
        txtTongChiMonth.setText(tongChiVNDFormat);
        vn.mrlongg71.assignment.View.PieChart.addDataSet(pieChart,tongChiVND,tongThuVND,2);

        txtMonth.setText("Tháng "+thangHientai);

    }

    private void DayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        thangHientai = (month + 1) + "/" + year;
    }

    private void SpinnerThang() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Chọn tháng");
        for (int i = 1; i < 13; i++) {
            String thang = "Tháng " + i;
            arrayList.add(thang);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        spinnerThang.setAdapter(arrayAdapter);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    thangHientai = position + "/" + 2019;
                    Log.d("date", thangHientai);
                    getDataThu();
                    getDataChi();
                    vn.mrlongg71.assignment.View.PieChart.addDataSet(pieChart,tongChiVND, tongThuVND,2);

                } else {
                    DayOfMonth();
                    getDataThu();
                    getDataChi();
                    vn.mrlongg71.assignment.View.PieChart.addDataSet(pieChart,tongChiVND, tongThuVND,2);
                }
//                if((tongChiVND  == 0 && tongThuVND == 0)){
//                    pieChart.setCenterText("Chưa có dữ liệu");
//                }else {
//                    pieChart.setCenterText("Tháng");
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void anhxa(View view) {
        txtMonth = view.findViewById(R.id.txtThang);
        txtTongChiMonth = view.findViewById(R.id.txtChiMonth);
        txtTongThuMonth = view.findViewById(R.id.txtThuMonth);
        spinnerThang = view.findViewById(R.id.spinerThang);
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        UserID = firebaseAuth.getCurrentUser().getUid();
        pieChart = (PieChart) view.findViewById(R.id.piechartMonth);


    }
}
