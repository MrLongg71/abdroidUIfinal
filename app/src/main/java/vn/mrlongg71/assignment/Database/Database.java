package vn.mrlongg71.assignment.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Database extends SQLiteOpenHelper {


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //insert loai thu
    public void INSERT_Loai(String table, String tenloai, int deleteflag, byte[] imgIcon, String iduser) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "INSERT INTO '" + table + "' VALUES(null, ? ,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenloai);
        statement.bindString(2, deleteflag + "");
        statement.bindBlob(3, imgIcon);
        statement.bindString(4, iduser);
        statement.executeInsert();
    }

    //insert Cv
    public void INSERT_Cv(String table, String nameCV, String money, String donviThu, int danhGia, int deleteFlag, String date, String ghiChu, byte[] img, int idLoai,String idUser) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO '" + table + "' VALUES(null, ?, ? ,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nameCV);
        statement.bindString(2, money);
        statement.bindString(3, donviThu);
        statement.bindString(4, danhGia + "");
        statement.bindString(5, deleteFlag + "");
        statement.bindString(6, date);
        statement.bindString(7, ghiChu);
        statement.bindBlob(8, img);
        statement.bindString(9, idLoai + "");
        statement.bindString(10, idUser);


        statement.executeInsert();
    }


    //Truy vấn database
    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Lấy database
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


}