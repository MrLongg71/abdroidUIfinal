package vn.mrlongg71.assignment.Model;

import android.content.Context;

public class Spiner {
    private int IdSp;
    private String nameLoai;
    private int deleteflag;
    private byte[] imgIcon;
    private String UserID;

    public Spiner(int idSp, String nameLoai, int deleteflag, byte[] imgIcon, String userID) {
        IdSp = idSp;
        this.nameLoai = nameLoai;
        this.deleteflag = deleteflag;
        this.imgIcon = imgIcon;
        UserID = userID;
    }

    public int getIdSp() {
        return IdSp;
    }

    public void setIdSp(int idSp) {
        IdSp = idSp;
    }

    public String getNameLoai() {
        return nameLoai;
    }

    public void setNameLoai(String nameLoai) {
        this.nameLoai = nameLoai;
    }

    public int getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(int deleteflag) {
        this.deleteflag = deleteflag;
    }

    public byte[] getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(byte[] imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}