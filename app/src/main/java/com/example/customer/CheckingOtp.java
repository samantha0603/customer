package  com.example.customer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckingOtp extends AppCompatActivity {
    EditText otp;
    Button checkotp;
    int cotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_otp);


        checkotp=findViewById(R.id.check_otp);
        checkotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                otp = findViewById(R.id.otp_ed);
                String strcotp = otp.getText().toString();
                int motp = OTPTest.otp_main;
                try {
                    cotp = Integer.parseInt(strcotp);
                } catch (NumberFormatException e) {
                    // handle the exception
                }
                Toast.makeText(CheckingOtp.this, "hello " + motp + " mg" + cotp, Toast.LENGTH_SHORT).show();
                //Toast.makeText(checking_otp.this, "otp checked", Toast.LENGTH_SHORT).show();

                if (motp == cotp) {
                    Toast.makeText(CheckingOtp.this, "done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CheckingOtp.this, ForgotPassword.class));
                    finish();
                }
            }
        });
    }
}
