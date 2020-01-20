package com.example.sg_finalassign_sg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.sg_finalassign_sg.resouce.notificationManager;
import static com.example.sg_finalassign_sg.resouce.topic;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    BottomNavigationView bottom;
    Spinner sort;
    int currSortBy = 0;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resouce.imgs[0] = findViewById(R.id.img1);
        resouce.imgs[1] = findViewById(R.id.img2);
        resouce.imgs[2] = findViewById(R.id.img3);
        resouce.imgs[3] = findViewById(R.id.img4);
        resouce.imgs[4] = findViewById(R.id.img5);
        resouce.imgs[5] = findViewById(R.id.img6);
        resouce.imgs[6] = findViewById(R.id.img7);
        resouce.imgs[7] = findViewById(R.id.img8);
        resouce.atricleNames[0] = findViewById(R.id.img1Text);
        resouce.atricleNames[1] = findViewById(R.id.img2Text);
        resouce.atricleNames[2] = findViewById(R.id.img3Text);
        resouce.atricleNames[3] = findViewById(R.id.img4Text);
        resouce.atricleNames[4] = findViewById(R.id.img5Text);
        resouce.atricleNames[5] = findViewById(R.id.img6Text);
        resouce.atricleNames[6] = findViewById(R.id.img7Text);
        resouce.atricleNames[7] = findViewById(R.id.img8Text);

        resouce.headerText = findViewById(R.id.headerText);

        for (int i = 0; i <= 7; i++) {
            resouce.imgs[i].setBackgroundColor(Color.rgb(0, 0, 0));
            resouce.imgs[i].setOnClickListener(this);
            resouce.atricleNames[i].setOnClickListener(this);
            resouce.atricleNames[i].setTextColor(Color.rgb(255, 255, 255));
        }

        resouce.data = Volley.newRequestQueue(this);
        spinnerActions();
        navigation();

        if(!resouce.messaged2) {
            setUpNotification();
            resouce.messaged2 = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resouce.data = Volley.newRequestQueue(this);

        int spinnerPosition = adapter.getPosition(resouce.topic);
        sort.setSelection(spinnerPosition);

        if(resouce.searchChangedPage){
            resouce.searchChangedPage = false;
            resouce.topic = "*";
            resouce.getNewsData();
            resouce.populate();

        }else{
            resouce.populate();
        }
    }
    public void onPause(){
        super.onPause();
    }

    public void setUpNotification(){
        //setting up notification channel
        NotificationChannel channe2 = new NotificationChannel(
                resouce.CHANN2,
                "Chann2",
                NotificationManager.IMPORTANCE_HIGH);
        channe2.setLightColor(Color.rgb(0,0,0));
        channe2.setDescription("");
        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;
        manager.createNotificationChannel(channe2);
        resouce.notification("welcome back, " + resouce.user.getName(), this);
    }

    public void spinnerActions(){

        //sort tool in spinner
        sort = findViewById(R.id.sort);
        String[] items = new String[]{"Everything", "Trump", "Food", "Bitcoin", "Music"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sort.setAdapter(adapter);

        //on click for spinner values to change the sorting
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position == 0){
                    resouce.topic = "*";
                }
                else if(position == 1){
                    resouce.topic = "Trump";

                }else if(position == 2){
                    resouce.topic = "Food";

                }else if(position == 3){
                    resouce.topic = "Bitcoin";

                }else if(position == 4){
                    resouce.topic = "Music";
                }

                resouce.getNewsData();
                resouce.populate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }});

            int spinnerPosition = adapter.getPosition(resouce.topic);
            sort.setSelection(spinnerPosition);
    }

    public void  navigation(){
        //navigation bar
        final Context c = this;
        bottom = findViewById(R.id.navId);
        bottom.setSelectedItemId(R.id.nav1);
        bottom.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nav2:
                                Intent in2 = new Intent(c, SearchActivity.class);
                                startActivity(in2);
                                break;
                            case R.id.nav3:
                                Intent in3 = new Intent(c, ProfileActivity.class);
                                startActivity(in3);
                                break;
                        }
                        return false;
                    }
                });
    }
    @Override
    public void onClick(View view) {
        for (int i = 0; i <= 7; i++) {
            if (view.getId() == resouce.imgs[i].getId() || view.getId() == resouce.atricleNames[i].getId()) {
                resouce.Art = resouce.news[i];
                Intent in = new Intent(this, ArticleActivity.class);
                startActivity(in);
                break;
            }
        }
    }

}


