package com.example.sg_finalassign_sg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {


    TextView title, content, details, Info;
    ImageView image, like, dislike, saved;
    Boolean faved = false;
    BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        title = findViewById(R.id.articleHeader);
        content = findViewById(R.id.articleContent);
        dislike = findViewById(R.id.articleDislike);
        details = findViewById(R.id.articleDetails);
        image = findViewById(R.id.articleImage);
        Info = findViewById(R.id.articleInfo);
        saved = findViewById(R.id.articleSave);

        //like picture buttons
        like = findViewById(R.id.articleLike);
        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        like.setImageResource(R.drawable.like);
        dislike.setImageResource(R.drawable.dislike);
        saved.setOnClickListener(this);

        //load article data
        title.setText(resouce.Art.getTitle());
        String des = resouce.Art.getDescriptiong() + "\n\nBy: " + resouce.Art.getAuthor();
        Info.setText(des);
        String date = "Date: " + resouce.Art.getPublishDate();
        details.setText(date);
        details.setText(resouce.Art.getSource());

        if(resouce.Art.getContent().length() >= 50) {
            content.append(resouce.Art.getContent().substring(0, 50));
        }else{
            content.append(resouce.Art.getContent());
        }

        Picasso.get().load(resouce.Art.getUrl()).into(image);


        //set like on if liked
        for (int i=0; i<= 9; i++){
            if(resouce.user.liked[i] != null) {
                if (resouce.Art.getTitle().equals(resouce.user.liked[i].getTitle())) {
                    like.setImageResource(R.drawable.liked);
                    dislike.setImageResource(R.drawable.dislike);
                    break;

                }else if(resouce.Art.getTitle().equals(resouce.user.disliked[i].getTitle())){
                    dislike.setImageResource(R.drawable.disliked);
                    like.setImageResource(R.drawable.like);
                    break; }

            }else{ break; }
        }

        //set saved if saved
        for (int i=0; i<= 9; i++){
            if(resouce.user.saved[i] != null) {
                if (resouce.Art.getTitle().equals(resouce.user.saved[i].getTitle())) {
                    saved.setImageResource(R.drawable.stared);
                    faved=true;
                    break;
                }
            }
        }


        //navigation bar
        final Context c = this;
        bottom = findViewById(R.id.navId);
        bottom.setSelectedItemId(R.id.nav1);
        bottom.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nav1:
                                Intent in1 = new Intent(c, MainActivity.class);
                                startActivity(in1);
                                break;
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

    public void onClick(View v){
        if(v.getId() == like.getId()){
            like.setImageResource(R.drawable.liked);
            dislike.setImageResource(R.drawable.dislike);

            for (int i=0;i<=9;i++){
                if (resouce.user.liked[i] != null) {
                    resouce.user.liked[i] = resouce.Art;
                    break;
                }
            }

        }else if(v.getId() == dislike.getId()){
            dislike.setImageResource(R.drawable.disliked);
            like.setImageResource(R.drawable.like);

            for (int i=0;i<=9;i++){
                if (resouce.user.disliked[i] != null) {
                    resouce.user.disliked[i] = resouce.Art;
                    break;
                }
            }


        }else if(v.getId() == saved.getId()){
            if(!faved){
                saved.setImageResource(R.drawable.stared);
                faved=true;

                for (int i=0;i<=9;i++) {
                    if (resouce.user.saved[i] != null && !resouce.user.saved[i].getTitle().equals(resouce.Art.getTitle())) {
                        resouce.user.saved[i] = resouce.Art;
                        break;
                    }
                }

            }else{
                saved.setImageResource(R.drawable.star);
                faved=false;
            }
        }
    }
}
