package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import vn.mrlongg71.assignment.Model.Spiner;
import vn.mrlongg71.assignment.R;

public class SpinerAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Spiner> list;

    public SpinerAdapter(Context context, int layout, List<Spiner> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class ViewHolder{
        TextView txtNameSp;
        ImageView imgSp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtNameSp = convertView.findViewById(R.id.txtNameSp);
            viewHolder.imgSp = convertView.findViewById(R.id.imgSp);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Spiner spinner = list.get(position);
        viewHolder.txtNameSp.setText(spinner.getNameLoai());
        //chuyển byte -> bitmap
        byte[] imgSV = spinner.getImgIcon();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgSV , 0, imgSV.length);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        viewHolder.imgSp.setImageDrawable(drawable);



        return convertView;
    }
}
