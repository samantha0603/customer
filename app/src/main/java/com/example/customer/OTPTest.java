package com.example.customer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPTest extends AppCompatActivity {
    EditText phno;
    Button  b;
    static int otp_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otptest);
        b=findViewById(R.id.button_otp);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                phno =findViewById(R.id.phno_id_in_otp);
                String phnoo=phno.getText().toString();
                Random random=new Random();
                otp_main =random.nextInt(10000);
                //Toast.makeText(Otptest.this, "msg"+otp_main, Toast.LENGTH_SHORT).show();
                String un="sasicollege",pass="SITE2002",from="INSITE",to="9100784817",type="1";
                ApiInterface apiCall=Retrofit_Otp.getService().create(ApiInterface.class);
                Call<String> retro  = apiCall.getOTP(un,pass,from,phnoo,otp_main,type);
                retro.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //     Toast.makeText(Otptest.this, "done", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //Toast.makeText(Otptest.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getApplication(),"phno sent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OTPTest.this,CheckingOtp.class));
                finish();
            }
        });
    }
}
