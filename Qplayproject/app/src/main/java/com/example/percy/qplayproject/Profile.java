package com.example.percy.qplayproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    Button bProfile, bActivities;
    TextView tvSetting, tvLogout,tvName,tvInfo;
    LinearLayout llActivites;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = (TextView) findViewById(R.id.tvName);
        bProfile = (Button) findViewById(R.id.bProfile);
        bActivities = (Button) findViewById(R.id.bActivities);
        tvSetting = (TextView) findViewById(R.id.tvSetting);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(this);
        llActivites = (LinearLayout) findViewById(R.id.llActivities);
        llActivites.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        bProfile.setOnClickListener(this);
        bProfile.setTransformationMethod(null);
        bActivities.setOnClickListener(this);
        bActivities.setTransformationMethod(null);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        tvName.setText(username);
        read("userintro.txt");
    }
    public String changeBack (String intro){
        intro = intro.replaceAll( "     ", System.getProperty("line.separator"));
        return intro;
    }
    // a method that displays user's name and intro based on the content of userintro.txt file
    public void read(String file){
        try{
            String filePath = getFilesDir().getPath().toString() + "/" + file;
            File myFile = new File(filePath);
            if(!myFile.exists()) {
                return;
            }
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList[0].equals(username)){
                    tvInfo = (TextView) findViewById(R.id.tvInfo);
                    if (arrayList.length > 1){
                        String intro = changeBack(arrayList[1]);
                        tvInfo.setText(intro);
                    }

                }
            }
            in.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void alert(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.llActivities:
                Intent iii = new Intent(this, UserActivitylist.class);
                Bundle bundleee = new Bundle();
                bundleee.putString("username", username);
                iii.putExtras(bundleee);
                startActivity(iii);
                break;
            case R.id.tvSetting:
                Intent i = new Intent(this, Settings.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.bProfile:
                break;
            // have to transfer the bundle so it will be transfered back later
            case R.id.bActivities:
                Intent ii = new Intent(this, ActivityList.class);
                Bundle bundlee = new Bundle();
                bundlee.putString("username", username);
                ii.putExtras(bundlee);
                startActivity(ii);
                finish();
                break;
            case R.id.tvLogout:
                finish();
                break;
        }
    }

}
