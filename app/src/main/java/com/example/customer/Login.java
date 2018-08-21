package com.example.customer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity {
    Button b;
    TextView error,regt;
    ProgressBar progressBar;

    EditText userid, pwd;
    String usr1, pass,mlabusr=" ",mlabpwd=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        TextView forgot=findViewById(R.id.forget_id);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OTPTest.class));
            }
        });

        progressBar=findViewById(R.id.loginpb);
        progressBar.setVisibility(View.GONE);
        b = findViewById(R.id.b2);
        regt=findViewById(R.id.regtext);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                userid = findViewById(R.id.user_id);
                pwd = findViewById(R.id.pwd_id);
                usr1 = userid.getText().toString();
                pass = pwd.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                if (usr1.length()==0 || pass.length()==0)
                    progressBar.setVisibility(View.GONE);
                //login
                if(usr1.length()>0 && pass.length()>0 ) {
                    String s = "{\"username\":\"" + usr1 + "\"}" + "{\"password\":\"" + pass + "\"}";
                    ApiInterface apiCall = ApiClient.getApiClient().create(ApiInterface.class);
                    final Call<List<LoginDataBringer>> data = apiCall.Login(s);
                    data.enqueue(new Callback<List<LoginDataBringer>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<LoginDataBringer>> call, @NonNull Response<List<LoginDataBringer>> response) {
                            progressBar.setVisibility(View.GONE);

                            if(response.body().size()>0) {
                                mlabusr = response.body().get(0).getUsername();
                                mlabpwd = response.body().get(0).getPassword();
                            }

                            if (usr1.equals(mlabusr) && pass.equals(mlabpwd)) {
                                //shared preference
                                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", mlabusr);
                                editor.putString("pwd", mlabpwd);
                                editor.putString("logined", "true");
                                editor.commit();
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                error = findViewById(R.id.error);
                                progressBar.setVisibility(View.GONE);
                                error.setText("Invalid Username or Password");
                                error.setGravity(Gravity.CENTER_HORIZONTAL);
                            }




                        }

                        @Override
                        public void onFailure(Call<List<LoginDataBringer>> call, Throwable t) {
                            Toast.makeText(v.getContext(),"kk",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else if(usr1.isEmpty() && pass.isEmpty())
                {

                    error = findViewById(R.id.error);
                    error.setText("Please provide username and password");


                }


                //Toast.makeText(v.getContext(),"kk"+usr_mlabs+""+pwd_mlabs,Toast.LENGTH_SHORT).show();




                //Toast.makeText(getApplicationContext(),"error login",Toast.LENGTH_LONG).show();

            }
        });
        regt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }

}