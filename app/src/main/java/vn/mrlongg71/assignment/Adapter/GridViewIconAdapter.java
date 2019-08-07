package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.mrlongg71.assignment.R;

public class GridViewIconAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Integer> listIcon;

    public GridViewIconAdapter(Context context, int layout, List<Integer> listIcon) {
        this.context = context;
        this.layout = layout;
        this.listIcon = listIcon;
    }

    @Override
    public int getCount() {
        return listIcon.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        CircleImageView imgIcon;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.imgIcon = convertView.findViewById(R.id.imgcustomIconGird);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgIcon.setImageResource(listIcon.get(position));
        return convertView;
    }
}
