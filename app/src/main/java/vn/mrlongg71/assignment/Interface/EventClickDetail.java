package vn.mrlongg71.assignment.Interface;

import android.app.Dialog;
import android.view.View;

import vn.mrlongg71.assignment.Model.Thu_Chi_Model;

public interface EventClickDetail {
    void onClickEditCv(int i,Thu_Chi_Model model);
    void onClickDeleteCv(int i,Thu_Chi_Model model);
    void onClickItemCv(int i,Thu_Chi_Model model);



}

