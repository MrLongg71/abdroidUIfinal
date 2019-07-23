package vn.mrlongg71.assignment.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import vn.mrlongg71.assignment.Activity.SplashActivity;
import vn.mrlongg71.assignment.Adapter.Info_RecyclerViewAdapter;
import vn.mrlongg71.assignment.Model.User;
import vn.mrlongg71.assignment.R;
import vn.mrlongg71.assignment.View.CallbackFragment;
import vn.mrlongg71.assignment.View.Show_Hide_Dialog;

import static android.app.Activity.RESULT_OK;


public class IntroduceFragment extends Fragment {
    ImageView imgUser;
    TextView txtUserName;
    final int REQUSE_CODE_CAMERA = 123;
    final int REQUES_CODE_FILE = 456;
    String UserID, linkphoto;
    ProgressDialog progressDialog;
    ArrayList<User> userArrayList;
    RecyclerView recyclerView;
    Info_RecyclerViewAdapter info_recyclerViewAdapter;
    FragmentManager fragmentManager;
    DatabaseReference mData;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce, container, false);
        anhxa(view);
        setUser();
        eventImg();
        //onclick callback
        CallbackFragment.callBackFragment(view, fragmentManager);
        return view;


    }

    private void setUser() {
        imgUser.setEnabled(true);
        mData.child("users").child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);

                userArrayList.add(new User(value.getUser(), value.getEmail(), value.getImage()));

                recyclerView.setAdapter(info_recyclerViewAdapter);
                txtUserName.setText(value.getUser());


                if (value.getImage().equals("")) {
                    imgUser.setImageResource(R.drawable.user);

                } else {
                    Glide.with(getActivity())
                            .load(value.getImage())
                            .into(imgUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void updatePhoto(Bitmap bitmap, final String idUser) {
        Show_Hide_Dialog.showProgressDialogWithTitle("Đang cập nhật...", progressDialog);
        final StorageReference mountainsRef = storage.child("users").child(idUser);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        final byte[] data = baos.toByteArray();
        final UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), "Cập nhật ảnh thất bại!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        linkphoto = uri.toString();
                        updateLinkPhotoUser(UserID, linkphoto);
                    }
                });

            }
        });
    }

    public void updateLinkPhotoUser(String idUser, String linkPhotoUser) {
        mData.child("users").child(idUser).child("image").setValue(linkPhotoUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);
                    Toast.makeText(getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Show_Hide_Dialog.hideProgressDialogWithTitle(progressDialog);
                    Toast.makeText(getActivity(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eventImg() {

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ChooseAddAnhSV();
            }
        });


    }

    private void dialog_ChooseAddAnhSV() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_chooseanhsv);
        ImageView btnChooseAnhFile = dialog.findViewById(R.id.btnchooseanhFile);
        ImageView btnChooseAnhCamera = dialog.findViewById(R.id.btnchooseanhCamera);


        btnChooseAnhCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUSE_CODE_CAMERA);
                dialog.dismiss();
            }
        });
        btnChooseAnhFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUES_CODE_FILE);
                dialog.dismiss();
            }
        });


        dialog.show();

    }
    //xử lí xin quyền camera và file

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

    // đổ ảnh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUSE_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            updatePhoto(bitmap, UserID);
            imgUser.setImageBitmap(bitmap);
        }
        if (requestCode == REQUES_CODE_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                updatePhoto(bitmap, UserID);
                imgUser.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhxa(View view) {
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        fragmentManager = getActivity().getSupportFragmentManager();
        UserID = firebaseUser.getUid();
        imgUser = view.findViewById(R.id.imgUser);
        progressDialog = new ProgressDialog(getActivity());
        txtUserName = view.findViewById(R.id.txtUserName);
        userArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.Info_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        info_recyclerViewAdapter = new Info_RecyclerViewAdapter(userArrayList, getActivity());


    }


}