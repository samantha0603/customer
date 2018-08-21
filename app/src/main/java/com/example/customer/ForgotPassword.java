package com.example.customer;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText user,pwd,cpwd;
    String pass,usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        final ProgressBar pb=findViewById(R.id.progressBar_forget);
        pb.setVisibility(View.GONE);
        Button b=findViewById(R.id.btopwd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                pb.setVisibility(View.VISIBLE);
                user=findViewById(R.id.usr_id);
                usr=user.getText().toString();
                pwd=findViewById(R.id.pwd_id_change);
                pass= pwd.getText().toString();
                cpwd=findViewById(R.id.pwd_id_change);
                String currentpwd=cpwd.getText().toString();
                if(pass.equals(currentpwd)) {
                    String q = "{\"username\":\"" + usr + "\"}";
                    JSONObject j = new JSONObject();
                    try {
                        j.put("username", usr);
                        j.put("password", pass);

                        ApiInterfacePut apiPut = MongoPutClass.getService().create(ApiInterfacePut.class);
                        Call<ResponseBody> body = apiPut.SaveUpdate(q, j.toString());
                        body.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                pb.setVisibility(View.GONE);
                                Toast.makeText(getApplication(), "password changed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassword.this,Login.class));
                                //Toast.makeText(v.getContext(), "done ", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(v.getContext(), "error while Assigning", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getApplication(),"please enter pwd correctly ",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}
