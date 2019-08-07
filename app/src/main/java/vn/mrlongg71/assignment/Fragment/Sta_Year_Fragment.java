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

import java.util.ArrayList;
import java.util.Calendar;

import vn.mrlongg71.assignment.Activity.MainActivity;
import vn.mrlongg71.assignment.R;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Sta_Year_Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    static TextView txtTongChiYear;
    TextView txtTongThuYear;
    static TextView txtYear;
    DatabaseReference mData;
    FirebaseAuth firebaseAuth;
    static String UserID;
    static String namHienTai;
    static Spinner spinerYear;
    static int tongThuVND = 0;
    static int tongChiVND = 0;
    static DecimalFormat formatter = new DecimalFormat("#,###,###");
    String tongThuVNDFormat = "0";
    static String tongChiVNDFormat = "0";
    static PieChart pieChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sta_year, container, false);
        anhxa(view);
        SpinnerThang();
        addDataSet(pieChart, tongChiVND,tongThuVND);
        return  view;
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
            if (date.contains(namHienTai)) {
                if (donviThu.equals("VND")) {
                    tongThuVND += Integer.parseInt(money);
                }
            }
        }
        tongThuVNDFormat = formatter.format(tongThuVND);
        txtTongThuYear.setText(tongThuVNDFormat);
        txtYear.setText("Năm "+namHienTai);

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
            if (date.contains(namHienTai)) {
                if (donviThu.equals("VND")) {
                    tongChiVND += Integer.parseInt(money);
                }
            }
        }
        tongChiVNDFormat = formatter.format(tongChiVND);
        txtTongChiYear.setText(tongChiVNDFormat);
        vn.mrlongg71.assignment.View.PieChart.addDataSet(pieChart,tongChiVND,tongThuVND,3);
        txtYear.setText("Năm "+namHienTai);

    }
    private  void SpinnerThang() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Chọn năm");
        for (int i = 2016; i < 2030; i++) {
            arrayList.add(""+i);
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        spinerYear.setAdapter(arrayAdapter);
        spinerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    namHienTai = arrayList.get(position) + "";
                    getDataThu();
                    getDataChi();
                    vn.mrlongg71.assignment.View.PieChart.addDataSet(pieChart,tongChiVND,tongThuVND,3);
                } else {
                    DayOfMonth();
                    getDataThu();
                    getDataChi();
                    vn.mrlongg71.assignment.View.PieChart.addDataSet(pieChart,tongChiVND,tongThuVND,3);
                }
//                if((tongChiVND  == 0 && tongThuVND == 0)){
//                    pieChart.setCenterText("Chưa có dữ liệu");
//                }else {
//                    pieChart.setCenterText("Năm");
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void DayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        namHienTai =  ""+ year;
    }
    public   static void addDataSet(com.github.mikephil.charting.charts.PieChart pieChart, int tongChiVND, int tongThuVND) {
        pieChart.setRotationEnabled(true);
        pieChart.setDescription(new Description());
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(30);
        pieChart.setDrawEntryLabels(true);
        pieChart.setHoleColor(Color.WHITE);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.clear();
        entries.add(new PieEntry(tongThuVND, "Tổng Thu"));
        entries.add(new PieEntry(tongChiVND, "Tổng Chi"));


        PieDataSet pieDataSet = new PieDataSet(entries, " ");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.invalidate();
    }
    private void anhxa(View view) {
        txtYear = view.findViewById(R.id.txtYear);
        txtTongChiYear = view.findViewById(R.id.txtChiYear);
        txtTongThuYear = view.findViewById(R.id.txtThuYear);
        spinerYear = view.findViewById(R.id.spinerYear);
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        UserID = firebaseAuth.getCurrentUser().getUid();
        pieChart = (PieChart) view.findViewById(R.id.piechartYear);

    }


}
