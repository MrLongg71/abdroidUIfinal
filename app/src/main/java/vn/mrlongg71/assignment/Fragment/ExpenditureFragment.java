package vn.mrlongg71.assignment.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import vn.mrlongg71.assignment.Activity.MainActivity;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.View.DialogInput;

public class ExpenditureFragment extends Fragment{
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        anhxa(view);
        eventFab();


        return view;
    }

    private void eventFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                DialogInput.dialogInput( dialog);
                Button btn1 = dialog.findViewById(R.id.btnaddclass_dialog);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });


            }
        });


    }

    private void anhxa(View view) {

        fab = view.findViewById(R.id.fabExpen);


    }
}