package com.example.customer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
BottomNavigationView navigationView;
FrameLayout frameLayout;
HomeFragment fragmentHome;
FragmentIssue fragmentIssue;
FragmentStatus fragmentStatus;
FragmentFeedback fragmentFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 100);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.INTERNET}, 100);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_custom_action_bar);
        View view =getSupportActionBar().getCustomView();

        navigationView=findViewById(R.id.bottomnavigation);
        BottomNavigationViewHelper.diableShiftMode(navigationView);
        fragmentHome=new HomeFragment();
        frameLayout=findViewById(R.id.frame);
        fragmentStatus=new FragmentStatus();
        fragmentIssue=new FragmentIssue();
        fragmentFeedback=new FragmentFeedback();
        navigationView.setSelectedItemId(R.id.home);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragmentHome).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:{
                    calFrag(fragmentHome);
                    return true;
                    }
                    case R.id.issue:{
                        calFrag(fragmentIssue);
                        return true;
                    }
                    case R.id.status:{
                        calFrag(fragmentStatus);
                        return true;
                    }
                    case R.id.feedback:{
                        calFrag(fragmentFeedback);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void calFrag(Fragment fragmentHome) {
       FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.frame,fragmentHome).commit();


    }
    //more options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.drop1:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.drop2:
                SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("username");
                edit.remove("password");
                edit.remove("logined");
                edit.commit();
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
            case R.id.drop3:
                startActivity(new Intent(getApplicationContext(),Password.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
