package vn.mrlongg71.assignment.View;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParseByteArr {
    public static void DrawableToByte(CircleImageView imgIcon){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgIcon.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        final ByteArrayOutputStream arr_byte = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, arr_byte);
        imgIconbyte =   arr_byte.toByteArray();
    }
    public static void DrawableToByte(ImageView imgCv){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgCv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        final ByteArrayOutputStream arr_byte = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, arr_byte);
        imgCvbyte =   arr_byte.toByteArray();
    }
    public static byte[] imgIconbyte;
    public static  byte[] imgCvbyte;

}
