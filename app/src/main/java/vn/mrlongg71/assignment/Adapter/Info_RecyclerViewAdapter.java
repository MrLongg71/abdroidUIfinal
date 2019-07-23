package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.mrlongg71.assignment.Model.User;
import vn.mrlongg71.assignment.R;

public class Info_RecyclerViewAdapter extends RecyclerView.Adapter<Info_RecyclerViewAdapter.viewHolder> {
    List<User> list;
    Context context;

    public Info_RecyclerViewAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_info_recyclerview, viewGroup, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        User user = list.get(i);
        viewHolder.txt.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txt;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);

        }
    }
}
