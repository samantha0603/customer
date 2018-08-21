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

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP extends AppCompatActivity {
    Button b;
    EditText otp;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        b=findViewById(R.id.b_otp);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                int otp_c=  Register.otp_main;
                otp=findViewById(R.id.otp_edd);
                String otpm=otp.getText().toString();
                try {
                    i = Integer.parseInt(otpm);
                }
                catch (Exception e)
                {

                }
                if(otp_c==i)
                {
                    Intent intent =getIntent();
                    String usern=intent.getStringExtra("username");
                    String phno=intent.getStringExtra("phno");
                    String pwd=intent.getStringExtra("pwd");
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("username",usern);
                        jsonObject.put("password",pwd);
                        ApiInterfacePut apiPut =MongoPutClass.getService().create(ApiInterfacePut.class);
                        Call<ResponseBody> body=apiPut.Registration(jsonObject.toString());
                        body.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(OTP.this, "successfully registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(OTP.this,Login.class));
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(OTP.this, "please check your network", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //           Toast.makeText(getApplication(), "data"+usern+"  "+phno+""+pwd+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
