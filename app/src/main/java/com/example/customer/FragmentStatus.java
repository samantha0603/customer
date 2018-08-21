package com.example.customer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static com.example.customer.R.id.customer_id;
import static com.example.customer.R.id.customer_phno;
import static com.example.customer.R.id.pwd_id;
import static com.example.customer.R.id.user_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStatus extends Fragment {
    ImageView imageView;
    Button button;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    EditText userid, pwd;
    String usr1, pass,mlabusr=" ",mlabpwd=" ";

    public FragmentStatus() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_status, container, false);
        imageView = v.findViewById(R.id.imageView);

        button = v.findViewById(R.id.button);
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String n = sp.getString("username","error");
        EditTextValue = "sgt56307csm"+n;

              try {
                   bitmap = TextToImageEncode(EditTextValue);

                    imageView.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

        return v;
    }


    public Bitmap TextToImageEncode(String value) throws WriterException {
        int color_black = getResources().getColor(R.color.white);
        int color_white = getResources().getColor(R.color.black);

        BitMatrix bitMatrix;
            try {
                bitMatrix = new MultiFormatWriter().encode(
                        value,
                        BarcodeFormat.DATA_MATRIX.QR_CODE,
                        QRcodeWidth, QRcodeWidth, null
                );

            } catch (IllegalArgumentException Illegalargumentexception) {

                return null;
            }
            int bitMatrixWidth = bitMatrix.getWidth();

            int bitMatrixHeight = bitMatrix.getHeight();

            int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

            for (int y = 0; y < bitMatrixHeight; y++) {
                int offset = y * bitMatrixWidth;

                for (int x = 0; x < bitMatrixWidth; x++) {

                    pixels[offset + x] = bitMatrix.get(x, y) ?
                            color_white : color_black;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

            bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
            return bitmap;
        }
        // Inflate the layout for this fragment


}
