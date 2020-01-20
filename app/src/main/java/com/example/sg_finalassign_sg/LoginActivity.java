package com.example.sg_finalassign_sg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //main login
    EditText name, pass;
    TextView register, membersView;
    Button login;

    //register
    TextView Err,loginErr;
    EditText regName, regEmail, regPhone, regPass;
    Button regNext, regBack;

    //interests
    RadioGroup group1, group2;
    Button intBack, intNext;
    TextView intErr;

    //user to be added
    User sampleUser = new User();
    String interesedIn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login();

        resouce.data = Volley.newRequestQueue(this);
        resouce.notificationManager = NotificationManagerCompat.from(this);

        if(!resouce.messaged1) {
            setUpNotification();
            resouce.messaged1 = true;
        }
    }

    public void setUpNotification(){
        //setting up notification channel
        NotificationChannel channel = new NotificationChannel(
                resouce.CHANN,
                "Chann1",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setLightColor(Color.rgb(22,23,43));
        channel.setDescription("");
        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;
        manager.createNotificationChannel(channel);
        resouce.notification("welcome to a more informed future :)", this);
    }

    @Override
    public void onClick(View view) {

        //login
        if(view.getId() == login.getId()){
            if(validation()) {
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
            }

        }else if(view.getId() == register.getId()){
            register();

        //Register
        } else if(view.getId() == regBack.getId()){
            printUsers();
            name.setText(getString(R.string.sampleName));
            pass.setText(getString(R.string.samplePassword));
            login();

        } else if (view.getId() == regNext.getId()){
            if(checkReg()){
                sampleUser.setName(regName.getText().toString());
                sampleUser.setEmail(regEmail.getText().toString());
                sampleUser.setPhone(regPhone.getText().toString());
                sampleUser.setPassword(regPass.getText().toString());
                interests();  } }

        //interests
        else if (view.getId() == intBack.getId()){
            register();

        } else if(view.getId() == intNext.getId()){
            if(checkInt()){
                sampleUser.setInterests(interesedIn);
                resouce.user = sampleUser;
                resouce.db.addPlayer(sampleUser);
                resouce.loggedIn = true;
                Intent in = new Intent(this, ProfileActivity.class);
                startActivity(in);
            }  }
    }

    public boolean validation(){
        if(name.getText().toString().equals("") || pass.getText().toString().equals("")){
            loginErr.append("Empty feild\n");
            return false;

        }else {
            User test = resouce.db.getPlayerByName(name.getText().toString());

            try {
                if (test != null) {
                    if (test.getPassword().equals(pass.getText().toString())) {
                        resouce.user = test;
                        resouce.loggedIn = true;
                        loginErr.setText(getString(R.string.access));
                        return true;

                    } else {
                        loginErr.setText(getString(R.string.incorrect));
                        return false; }

                }else{ loginErr.setText(getString(R.string.notFound)); }

            }catch(Exception E){ return false; }

            return false;
        }
    }

    public void register(){
        setContentView(R.layout.activity_register);
        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPhone = findViewById(R.id.regPhone);
        regPass = findViewById(R.id.regPass);
        Err = findViewById(R.id.regErr);
        regNext = findViewById(R.id.regSign);
        regBack = findViewById(R.id.regBack);
        regBack.setOnClickListener(this);
        regNext.setOnClickListener(this);
    }

    public void interests(){
        setContentView(R.layout.activity_intrestes);
        group1 = findViewById(R.id.radioGroup);
        group2 = findViewById(R.id.radioGroup2);
        intErr = findViewById(R.id.intErr);

        intBack = findViewById(R.id.intBack);
        intNext = findViewById(R.id.intNext);

        intBack.setOnClickListener(this);
        intNext.setOnClickListener(this);
    }

    public void login(){
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.mainLogin);
        loginErr = findViewById(R.id.loginErr);
        membersView = findViewById(R.id.membersView);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    public boolean checkReg(){
        Err.setText("");
        boolean pass = true;

        if(regName.getText().toString().equals("") || regEmail.getText().toString().equals("") || regPhone.getText().toString().equals("") || regPass.getText().toString().equals("")){
            Err.append("Empty Feild\n");
            pass = false;
        }

        if (regName.getText().length() <= 3 && regName.getText().length() > 0){
            Err.append("Name Feild too short\n");
            pass = false;
        }

        if (regEmail.getText().length() <= 4 && regEmail.getText().length() > 0){
            Err.append("Email Feild too short\n");
            pass = false;
        }

        if (regPhone.getText().length() != 10){
            Err.append("Phone Feild is wrong\n");
            pass = false;
        }

        if (regPass.getText().length() <= 5 && regPass.getText().length() > 0){
            Err.append("Password Feild too short\n");
            pass = false;
        }
        return pass;
    }

    public boolean checkInt(){

        boolean pass = true;

        int selectedId = group1.getCheckedRadioButtonId();
        RadioButton btn = (RadioButton) findViewById(selectedId);

        int selectedId2 = group2.getCheckedRadioButtonId();
        RadioButton btn2 = (RadioButton) findViewById(selectedId2);

        intErr.append(btn.getText().toString() + "\n");
        intErr.append(btn2.getText().toString() + "\n");

        if(btn.getText().toString().equals("") || btn2.getText().toString().equals("")){
            pass = false;
        }else{
            interesedIn += btn.getText().toString() + "," + btn2.getText().toString();
        }

        return pass;
    }

    public void printUsers(){
        List<User> users = resouce.db.getAllUsers();
        membersView.setText("");

        for (User x: users){
            membersView.append("\n" + x.getName() + ", " + x.getPassword() + "," + x.getInterests());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resouce.setUpData(this);
        printUsers();

        resouce.getNewsData();
        name.setText(getString(R.string.sampleName));
        pass.setText(getString(R.string.samplePassword));
    }
}
