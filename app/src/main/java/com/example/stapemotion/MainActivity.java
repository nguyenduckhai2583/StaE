package com.example.stapemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

public class MainActivity extends AppCompatActivity {
    EditText loginEdt_email, loginEdt_password;
    Button loginBtn_dangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        addContent();
        addEvent();
    }

    private void addEvent() {
        loginBtn_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEdt_email.getText().toString();
                String password = loginEdt_password.getText().toString();
                if(email.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "Invalid Information", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(MainActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }
        });


    }

    private void addContent() {
        loginEdt_email = findViewById(R.id.loginEdt_email);
        loginEdt_password = findViewById(R.id.loginEdt_password);
        loginBtn_dangNhap = findViewById(R.id.loginBtn_dangNhap);
    }
}
