package com.example.sg_finalassign_sg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, EditText.OnEditorActionListener{

    BottomNavigationView bottom;
    EditText searchValue;
    boolean searched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchValue = findViewById(R.id.search);
        resouce.S_Text[0] = findViewById(R.id.Simg1Text);
        resouce.S_Text[1] = findViewById(R.id.Simg2Text);
        resouce.S_Text[2] = findViewById(R.id.Simg3Text);
        resouce.S_Text[3] = findViewById(R.id.Simg4Text);
        resouce.S_Text[4] = findViewById(R.id.Simg5Text);
        resouce.S_Text[5] = findViewById(R.id.Simg6Text);
        resouce.S_Text[6] = findViewById(R.id.Simg7Text);
        resouce.S_Text[7] = findViewById(R.id.Simg8Text);
        resouce.titles[0] = findViewById(R.id.img1Tit);
        resouce.titles[1] = findViewById(R.id.img2Tit);
        resouce.titles[2] = findViewById(R.id.img3Tit);
        resouce.titles[3] = findViewById(R.id.img4Tit);
        resouce.titles[4] = findViewById(R.id.img5Tit);
        resouce.titles[5] = findViewById(R.id.img6Tit);
        resouce.titles[6] = findViewById(R.id.img7Tit);
        resouce.titles[7] = findViewById(R.id.img8Tit);
        resouce.S_imgs[0] = findViewById(R.id.Simg1);
        resouce.S_imgs[1] = findViewById(R.id.Simg2);
        resouce.S_imgs[2] = findViewById(R.id.Simg3);
        resouce.S_imgs[3] = findViewById(R.id.Simg4);
        resouce.S_imgs[4] = findViewById(R.id.Simg5);
        resouce.S_imgs[5] = findViewById(R.id.Simg6);
        resouce.S_imgs[6] = findViewById(R.id.Simg7);
        resouce.S_imgs[7] = findViewById(R.id.Simg8);



        final int color = getColor(R.color.darker);
        final Drawable drawable = new ColorDrawable(color);
        resouce.S_imgs[0].setForeground(drawable);

        for (int i=0; i<= 7; i++){
            resouce.S_Text[i].setOnClickListener(this);
            resouce.S_imgs[i].setOnClickListener(this);
        }

        searchValue.requestFocus();
        searchValue.setOnEditorActionListener(this);




        //navigation bar
        bottom = findViewById(R.id.navId);
        final Context c = this;
        bottom.setSelectedItemId(R.id.nav2);
        bottom.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav1:
                                Intent in = new Intent(c, MainActivity.class);
                                startActivity(in);
                                break;
                            case R.id.nav3:
                                Intent in3 = new Intent(c, ProfileActivity.class);
                                startActivity(in3);
                                break;
                        }
                        return false; } });

        resouce.data = Volley.newRequestQueue(this);
        resouce.notificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public void onClick(View view) {
        if(searched) {
            for (int i = 0; i <= 7; i++) {
                if (view.getId() == resouce.S_imgs[i].getId() || view.getId() == resouce.S_Text[i].getId()) {
                    resouce.Art = resouce.news[i];
                    Intent in = new Intent(this, ArticleActivity.class);
                    startActivity(in);
                    break;
                }
            }
        }
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        searchValue.clearFocus();

        if (!searchValue.getText().toString().equals("") && searchValue.getText().toString().length() >= 1 && searchValue.getText().toString().length() <= 10) {
            resouce.topic = searchValue.getText().toString();
            searched = true;
            resouce.getNewsData();
            resouce.populate2();
            resouce.searchChangedPage = true;
            resouce.S_imgs[0].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[1].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[2].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[3].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[4].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[5].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[6].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
            resouce.S_imgs[7].setForeground(ContextCompat.getDrawable(this, R.drawable.round));
        }
        return true;
    }
}
