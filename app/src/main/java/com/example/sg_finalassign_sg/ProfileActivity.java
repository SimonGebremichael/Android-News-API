package com.example.sg_finalassign_sg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    BottomNavigationView bottom;
    TextView name, email, phone, interests;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.proName);
        email = findViewById(R.id.proEmail);
        phone = findViewById(R.id.proPhone);
        interests = findViewById(R.id.proInterests);

        String nam = "Hello, " + resouce.user.getName();
        name.setText(nam);

        email.setText(resouce.user.getEmail());
        phone.setText(resouce.user.getPhone());
        phone.setText(resouce.user.getPhone());

        String[] inter = resouce.user.getInterests().split(",");
        for (String x: inter){
            interests.append("-" + x + "\n");
        }

        //navigation bar
        bottom = findViewById(R.id.navId);
        final Context c = this;
        bottom.setSelectedItemId(R.id.nav3);

        bottom.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nav1:
                                Intent in = new Intent(c, MainActivity.class);
                                startActivity(in);
                                break;
                            case R.id.nav2:
                                Intent in2 = new Intent(c, SearchActivity.class);
                                startActivity(in2);
                                break;
                        }
                        return false; } });

        logOut = findViewById(R.id.proLogOut);
        logOut.setOnClickListener(this);

        if(!resouce.messaged3) {
            resouce.notification("welcome to your account, " + resouce.user.getName(), this);
            resouce.messaged3 = true;
        }
    }

    public void onClick(View view){
        if(view.getId() == logOut.getId()){
            resouce.loggedIn = false;
            resouce.user = null;
            resouce.notification("Goodbye friend :)", this);
            Intent in = new Intent(this, LoginActivity.class);
            startActivity(in);
        }
    }
}
