package vn.mrlongg71.assignment.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.mrlongg71.assignment.Activity.MainActivity;
import vn.mrlongg71.assignment.Fragment.RevenueFragment;
import vn.mrlongg71.assignment.Interface.EventClickDetail;
import vn.mrlongg71.assignment.Model.Spiner;
import vn.mrlongg71.assignment.Model.Thu_Chi_Model;
import vn.mrlongg71.assignment.R;

public class RecyclerThuChiAdapter extends RecyclerView.Adapter<RecyclerThuChiAdapter.viewHolder> {
    ArrayList<Thu_Chi_Model> list;
    Context context;

    public RecyclerThuChiAdapter(ArrayList<Thu_Chi_Model> list ,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerThuChiAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_recycler_thu_chi, viewGroup, false);
        return new RecyclerThuChiAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerThuChiAdapter.viewHolder viewHolder, final int i) {

        final Thu_Chi_Model model = list.get(i);

        viewHolder.txtTenCV.setText(model.getNameCV());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                clickDetail.onClickItemCv(i, model);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txtTenCV;
        ImageView img;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenCV = itemView.findViewById(R.id.txtTenCV);
            img = itemView.findViewById(R.id.test);

        }
    }
    public EventClickDetail clickDetail;
    public void setOnclickEvent(EventClickDetail eventClickDetail){
        this.clickDetail = eventClickDetail;
    }
}
