package vn.mrlongg71.assignment.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.mrlongg71.assignment.Activity.MainActivity;
import vn.mrlongg71.assignment.Adapter.GridViewIconAdapter;
import vn.mrlongg71.assignment.Adapter.RecyclerThuChiAdapter;
import vn.mrlongg71.assignment.Adapter.SpinerAdapter;
import vn.mrlongg71.assignment.Interface.EventClickDetail;
import vn.mrlongg71.assignment.Model.Spiner;
import vn.mrlongg71.assignment.Model.Thu_Chi_Model;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.View.CustomToast;
import vn.mrlongg71.assignment.View.DialogDate;
import vn.mrlongg71.assignment.View.FormatMoney;
import vn.mrlongg71.assignment.View.ParseByteArr;
import vn.mrlongg71.assignment.View.Show_Hide_Dialog;

import static android.app.Activity.RESULT_OK;

public class ExpenditureFragment extends Fragment{
    FloatingActionButton fab;
    Spinner spinner, spinerDonViThu;
    ArrayList<Spiner> spinnerArrayList;
    ArrayList<Thu_Chi_Model> thu_chi_modelArrayList;
    ArrayList<String> arrDonViThu;
    SpinerAdapter spinerAdapter;
    RecyclerThuChiAdapter thuChiAdapter;
    RecyclerView recyclerViewThuChi;
    Dialog dialog;
    ProgressDialog progressDialog;
    DatabaseReference mData;
    FirebaseAuth firebaseAuth;
    String UserID, donviThu;
    byte[] imgIconbyte, imgCVbyte;
    ImageView imgCamera, imgFile, imgCV;
    int idLoai;
    final int REQUSE_CODE_CAMERA = 123;
    final int REQUES_CODE_FILE = 456;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        anhxa(view);
        setRecyclerView();
        GetDataCV();
        eventFab();
        eventClickCV();
        return view;
    }

    //sửa và xóa CV
    private void eventClickCV() {
        thuChiAdapter.setOnclickEvent(new EventClickDetail() {
            @Override
            public void onClickEditCv(int i, final Thu_Chi_Model model) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_input_reve_expen);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                spinner = dialog.findViewById(R.id.spiner);
                spinerDonViThu = dialog.findViewById(R.id.spinnerDonViThu);
                ImageView imgAddLoai = dialog.findViewById(R.id.imgaddLoai);
                final EditText edtNamejob, edtMoney, edtNote;
                final TextView edtDate;
                edtNamejob = dialog.findViewById(R.id.edtnamejob);
                edtMoney = dialog.findViewById(R.id.edtmoney);
                edtDate = dialog.findViewById(R.id.edtdate);
                edtNote = dialog.findViewById(R.id.edtNote);
                imgCV = dialog.findViewById(R.id.imgCV);
                imgCV.setImageResource(R.drawable.ic_revenue);
                //đổ dữ liệu
                DialogDate.getTime(edtDate);
                addDonViThu();
                GetDataLoaiThu();
//                int a = thu_chi_modelArrayList.get(i).getIdLoai();
//                spinner.setSelection(a);
                idLoai = model.getIdLoai();
                edtNamejob.setText(model.getNameCV());
                edtMoney.setText(model.getMoney());
                edtDate.setText(model.getDate());
                edtNote.setText(model.getGhiChu());
                spinerDonViThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        donviThu = arrDonViThu.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idLoai = spinnerArrayList.get(position).getIdSp();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Button buttonEdtCv = dialog.findViewById(R.id.btnaddCv_dialog);
                buttonEdtCv.setText("Sửa");
                edtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogDate.dialogDate(edtDate, getActivity());
                    }
                });
                buttonEdtCv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int id = model.getId();
                        final String nameJob = edtNamejob.getText().toString().trim();
                        final String money = edtMoney.getText().toString().trim();
                        final String date = edtDate.getText().toString();
                        final String note = edtNote.getText().toString().trim();
//                        ParseByteArr.DrawableToByte(imgCV);
//                        imgCVbyte = ParseByteArr.imgCvbyte;
                        if (TextUtils.isEmpty(nameJob) || TextUtils.isEmpty(money) || TextUtils.isEmpty(date) || imgCVbyte == null) {
                            CustomToast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", CustomToast.LENGTH_LONG, CustomToast.ERROR, true).show();
                        } else {
                            Show_Hide_Dialog.showProgressDialogWithTitle("Đang cập nhật!", progressDialog);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity.database.QueryData("UPDATE KhoangChi SET id = '" + id + "', nameCV = '" + nameJob + "', money = '" + money + "', donviThu = '" + donviThu + "', danhGia = 0,deleteFlag = 0,date = '" + date + "', ghiChu = '" + note + "', idLoai = '" + idLoai + "',idUser = '" + UserID + "' WHERE id = '" + id + "' AND idUser = '" + UserID + "'");
                                    CustomToast.makeText(getActivity(), "Cập nhật thành công!", CustomToast.LENGTH_LONG, CustomToast.SUCCESS,false).show();
                                    GetDataCV();
                                    Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);
                                }
                            }, 1000);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }

            @Override
            public void onClickDeleteCv(final int i, final Thu_Chi_Model model) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có muốn xóa công việc " + model.getNameCV());
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.database.QueryData("UPDATE KhoangChi SET deleteFlag = 1 WHERE id = '" + model.getId() + "' AND idUser = '" + model.getIdUser() + "'");
                        CustomToast.makeText(getActivity(), "Xóa thành công!", CustomToast.LENGTH_LONG, CustomToast.SUCCESS,false).show();
                        thu_chi_modelArrayList.remove(i);

                        Sta_Day_Fragment.GetDataChi();
                        Sta_Month_Fragment.getDataChi();
                        Sta_Year_Fragment.getDataChi();
                        thuChiAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }

            //click từng item
            @Override
            public void onClickItemCv(final int i, final Thu_Chi_Model model) {
                final Dialog dialogdetail = new Dialog(getActivity());
                dialogdetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogdetail.setContentView(R.layout.custom_dialog_detail_thu_chi);
                dialogdetail.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView txtTitle,txttenCvdialogDetail,txtmoneyCvdialogDetail,txtDateCvdialogDetail,txtghichuCvdialogDetail,txtDanhmucCvdialogDetail;
                Button btnEdit_dialog_detail,btndelete_dialog_detail;
                ImageView imgCvDetail = dialogdetail.findViewById(R.id.imgCvDetails);
                txtTitle = dialogdetail.findViewById(R.id.txttitle);
                txttenCvdialogDetail = dialogdetail.findViewById(R.id.txttenCvdialogDetail);
                txtmoneyCvdialogDetail = dialogdetail.findViewById(R.id.txtmoneyCvdialogDetail);
                txtDateCvdialogDetail = dialogdetail.findViewById(R.id.txtDateCvdialogDetail);
                txtghichuCvdialogDetail = dialogdetail.findViewById(R.id.txtghichuCvdialogDetail);
                txtDanhmucCvdialogDetail = dialogdetail.findViewById(R.id.txtDanhmucCvdialogDetail);
                btnEdit_dialog_detail = dialogdetail.findViewById(R.id.btnEdit_dialog_detail);
                btndelete_dialog_detail = dialogdetail.findViewById(R.id.btndelete_dialog_detail);
                // gán dữ liệu
                txtTitle.setText("Chi tiết Khoản chi");
                txttenCvdialogDetail.setText(model.getNameCV());
                //format money
                txtmoneyCvdialogDetail.setTextColor(Color.RED);
                java.text.DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
                String moneyFormat = decimalFormat.format(Double.parseDouble(model.getMoney()));
                txtmoneyCvdialogDetail.setText(moneyFormat + " " + model.getDonviThu());
                txtDateCvdialogDetail.setText(model.getDate());
                txtghichuCvdialogDetail.setText(model.getGhiChu());
                byte[] imgSV = model.getImg();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgSV , 0, imgSV.length);
                imgCvDetail.setImageBitmap(bitmap);
                int idLoai = model.getIdLoai();
                Cursor cursor = MainActivity.database.GetData("SELECT LoaiChi.tenloai FROM LoaiChi INNER JOIN KhoangChi WHERE '"+idLoai+"' = LoaiChi.id");

                while (cursor.moveToNext()){
                    String tenLoai = cursor.getString(0);
                    txtDanhmucCvdialogDetail.setText(tenLoai);
                }

                // bắt sự kiện edit
                btnEdit_dialog_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickEditCv(i, model);
                        dialogdetail.dismiss();


                    }
                });
                btndelete_dialog_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDeleteCv(i,model);
                        //clickDetail.onClickDeleteCv(i,model);
                        dialogdetail.dismiss();
                    }
                });




                dialogdetail.show();
            }
        });

    }


    private void setRecyclerView() {
        recyclerViewThuChi.setHasFixedSize(true);
        thuChiAdapter = new RecyclerThuChiAdapter(thu_chi_modelArrayList, getActivity());
        recyclerViewThuChi.setAdapter(thuChiAdapter);
    }


    private void eventFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddCVThu();
            }
        });
    }

    //thêm công việc doanh thu
    private void dialogAddCVThu() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_input_reve_expen);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        spinner = dialog.findViewById(R.id.spiner);
        spinerDonViThu = dialog.findViewById(R.id.spinnerDonViThu);
        ImageView imgAddLoai = dialog.findViewById(R.id.imgaddLoai);
        final EditText edtNamejob, edtMoney, edtNote;
        final TextView edtDate;

        edtNamejob = dialog.findViewById(R.id.edtnamejob);
        edtMoney = dialog.findViewById(R.id.edtmoney);
        edtDate = dialog.findViewById(R.id.edtdate);
        edtNote = dialog.findViewById(R.id.edtNote);
        imgCamera = dialog.findViewById(R.id.imgCamera);
        imgFile = dialog.findViewById(R.id.imgFile);
        imgCV = dialog.findViewById(R.id.imgCV);
        imgCV.setImageResource(R.drawable.ic_revenue);
        Button buttonAddCv = dialog.findViewById(R.id.btnaddCv_dialog);
        DialogDate.getTime(edtDate);
        addDonViThu();
        edtMoney.setTextColor(Color.BLUE);
        //xử lý nummberformat
        edtMoney.addTextChangedListener(FormatMoney.onTextChangedListener(edtMoney));

        spinerDonViThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                donviThu = arrDonViThu.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idLoai = spinnerArrayList.get(position).getIdSp();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAddCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameJob = edtNamejob.getText().toString().trim();
                final String money = edtMoney.getText().toString().trim();
                String a[] = money.split(",");
                String b = "";
                for(int i = 0 ; i < a.length ; i++){
                    b = b + a[i];
                }
                final String date = edtDate.getText().toString();
                final String note = edtNote.getText().toString().trim();
                //parse img sang mảng byte
                ParseByteArr.DrawableToByte(imgCV);
                imgCVbyte = ParseByteArr.imgCvbyte;
                if (TextUtils.isEmpty(nameJob) || TextUtils.isEmpty(money) || TextUtils.isEmpty(date) || imgCVbyte == null) {
                    CustomToast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", CustomToast.LENGTH_LONG, CustomToast.ERROR, false).show();
                } else {
                    MainActivity.database.INSERT_Cv("KhoangChi", nameJob, b, donviThu, 0, 0, date, note, imgCVbyte, idLoai, UserID);
                    CustomToast.makeText(getActivity(), "Thêm thành công!", CustomToast.LENGTH_LONG, CustomToast.SUCCESS, false).show();
                    GetDataCV();
                    Sta_Day_Fragment.GetDataChi();
                    Sta_Month_Fragment.getDataChi();
                    Sta_Year_Fragment.getDataChi();

                }
                dialog.dismiss();


            }
        });
        imgAddLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddLoai();
                dialog.dismiss();
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUSE_CODE_CAMERA);
            }
        });
        imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUES_CODE_FILE);
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDate.dialogDate(edtDate, getActivity());
            }
        });


        GetDataLoaiThu();
        dialog.show();
    }


    //thêm đơn vị thu
    private void addDonViThu() {
        arrDonViThu = new ArrayList<>();
        arrDonViThu.add("VND");
        arrDonViThu.add("USD");
        ArrayAdapter<String> adapterDonViThu = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrDonViThu);
        spinerDonViThu.setAdapter(adapterDonViThu);
    }

    // dialog thêm loại thu
    private void dialogAddLoai() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_addloai);
        final EditText edtLoainew = dialog.findViewById(R.id.edtLoainew);
        final CircleImageView img_iconLoai = dialog.findViewById(R.id.img_iconLoai);
        img_iconLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseIcon(img_iconLoai);

            }
        });
        Button btnAddLoai = dialog.findViewById(R.id.btnAddLoai);

        btnAddLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loaiNew = edtLoainew.getText().toString().trim();
                imgIconbyte = ParseByteArr.imgIconbyte;
                if (imgIconbyte == null) {
                    img_iconLoai.setImageResource(R.drawable.ic_edit);
                    ParseByteArr.DrawableToByte(img_iconLoai);
                    imgIconbyte = ParseByteArr.imgIconbyte;
                }
                MainActivity.database.INSERT_Loai("LoaiChi", loaiNew, 0, imgIconbyte, UserID);
                Toast.makeText(getActivity(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    //girdview chọn icon
    private void dialogChooseIcon(final CircleImageView imgIcon) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_choose_icon);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        GridView gridViewIcon = dialog.findViewById(R.id.girdIcon);
        final ArrayList<Integer> arrIcon = new ArrayList<>();
        arrIcon.add(R.drawable.ic_home);
        arrIcon.add(R.drawable.ic_gift);
        arrIcon.add(R.drawable.ic_health);
        arrIcon.add(R.drawable.ic_cafe);
        arrIcon.add(R.drawable.ic_beer);
        arrIcon.add(R.drawable.ic_market);
        arrIcon.add(R.drawable.ic_school);
        arrIcon.add(R.drawable.ic_moto);
        GridViewIconAdapter gridViewIconAdapter = new GridViewIconAdapter(getActivity(), R.layout.custom_gridview, arrIcon);
        gridViewIcon.setAdapter(gridViewIconAdapter);
        gridViewIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgIcon.setImageResource(arrIcon.get(position));
                ParseByteArr.DrawableToByte(imgIcon);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //get data danh sách công việc
    //LÀm băng sqlite (y)ầ
    //mỏ activity chưa mấy fragment này lên a
    public void GetDataCV() {
        Cursor dataCV = MainActivity.database.GetData("SELECT * FROM KhoangChi WHERE idUser = '" + UserID + "' AND deleteFlag = 0");
        thu_chi_modelArrayList.clear();
        while (dataCV.moveToNext()) {
            int id = dataCV.getInt(0);
            String tenCV = dataCV.getString(1);
            String money = dataCV.getString(2);
            String donviThu = dataCV.getString(3);
            int danhGia = dataCV.getInt(4);
            int deleteflag = dataCV.getInt(5);
            String date = dataCV.getString(6);
            String ghiChu = dataCV.getString(7);
            int idLoai = dataCV.getInt(9);
            UserID = dataCV.getString(10);
            thu_chi_modelArrayList.add(new Thu_Chi_Model(id, tenCV, money, donviThu, danhGia, deleteflag, date, ghiChu, dataCV.getBlob(8), idLoai, UserID));
        }
        thuChiAdapter.notifyDataSetChanged();
    }


    //getdata loai thu
    private void GetDataLoaiThu() {
        Cursor dataLoai = MainActivity.database.GetData("SELECT * FROM LoaiChi WHERE iduser = '" + UserID + "' AND deleteflag = 0 ");
        spinnerArrayList.clear();
        while (dataLoai.moveToNext()) {
            int id = dataLoai.getInt(0);
            String nameLoai = dataLoai.getString(1);
            int deleteflag = dataLoai.getInt(2);
            UserID = dataLoai.getString(4);
            spinnerArrayList.add(new Spiner(id, nameLoai, deleteflag, dataLoai.getBlob(3), UserID));
        }

        spinerAdapter = new SpinerAdapter(getActivity(), R.layout.custom_spiner, spinnerArrayList);
        spinner.setAdapter(spinerAdapter);
        spinerAdapter.notifyDataSetChanged();
    }

    //xin quyền truy cập camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUSE_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUSE_CODE_CAMERA);
                } else {

                    Toast.makeText(getActivity(), "Bạn chưa cấp quyền Camera!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUES_CODE_FILE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUES_CODE_FILE);
                } else {
                    Toast.makeText(getActivity(), "Bạn chưa cấp quyền truy cập thư viện!", Toast.LENGTH_SHORT).show();
                }
                break;

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //đổ ảnh ra imageview
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUSE_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCV.setImageBitmap(bitmap);
        }
        if (requestCode == REQUES_CODE_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgCV.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhxa(View view) {
        thu_chi_modelArrayList = new ArrayList<>();
        fab = view.findViewById(R.id.fabExpen);
        recyclerViewThuChi = view.findViewById(R.id.recyclerKhoangChi);
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        UserID = firebaseAuth.getCurrentUser().getUid();
        spinnerArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewThuChi.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(getActivity());
    }

}