package vn.mrlongg71.assignment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import vn.mrlongg71.assignment.Model.User;

public class Info_RecyclerView extends RecyclerView.Adapter<Info_RecyclerView.viewHolder> {
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
