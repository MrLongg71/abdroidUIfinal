package vn.mrlongg71.assignment.View;

import android.graphics.Color;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChart {
    public static void addDataSet(com.github.mikephil.charting.charts.PieChart pieChart, int tongChiVND, int tongThuVND, int date) {
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
        if (tongChiVND == 0 && tongThuVND == 0) {
            pieChart.setCenterText("Chưa có dữ liệu");

        } else if (date == 1) {
            pieChart.setCenterText("Ngày");
        } else if (date == 2) {
            pieChart.setCenterText("Tháng");

        } else if (date == 3) {
            pieChart.setCenterText("Năm");
        }
    }
}
