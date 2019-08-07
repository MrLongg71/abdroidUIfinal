package vn.mrlongg71.assignment.Model;

public class Thu_Chi_Model  {
    private int id;
    private String nameCV;
    private String money;
    private String donviThu;
    private int danhGia;
    private int deleteFlag;
    private String date;
    private String ghiChu;
    private byte[] img;
    private int idLoai;
    private String idUser;

    public Thu_Chi_Model(int id, String nameCV, String money, String donviThu, int danhGia, int deleteFlag, String date, String ghiChu, byte[] img, int idLoai, String idUser) {
        this.id = id;
        this.nameCV = nameCV;
        this.money = money;
        this.donviThu = donviThu;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.date = date;
        this.ghiChu = ghiChu;
        this.img = img;
        this.idLoai = idLoai;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCV() {
        return nameCV;
    }

    public void setNameCV(String nameCV) {
        this.nameCV = nameCV;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDonviThu() {
        return donviThu;
    }

    public void setDonviThu(String donviThu) {
        this.donviThu = donviThu;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}