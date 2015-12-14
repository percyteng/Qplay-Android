package com.example.percy.qplayproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class otheruserprofile extends AppCompatActivity implements View.OnClickListener {
    TextView tvBack,tvName, tvInfo;
    LinearLayout llActivites;
    String username, currentuser;
    RatingBar ratingBar;
    TextView displayView;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruserprofile);
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvName = (TextView) findViewById(R.id.tvName);
        displayView = (TextView) findViewById(R.id.displayView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Drawable starDrawable = getResources().getDrawable(R.drawable.star);
        int height = starDrawable.getMinimumHeight();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ratingBar.getLayoutParams();
        params.height = height;
        ratingBar.setLayoutParams(params);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                displayView.setText("Your rating : " + rating);
                alert("Successful Rating", "The selected rating is : " + rating + ".");
            }
        });
        llActivites = (LinearLayout) findViewById(R.id.llActivities);
        llActivites.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        currentuser = bundle.getString("currentuser");
        if (currentuser.equals(username)){
            ratingBar.setVisibility(View.INVISIBLE);
            displayView.setVisibility(View.INVISIBLE);
        }
        tvName.setText(username);
        tvBack.setOnClickListener(this);
        read();
    }
    // this method changes 5 spaces back to a line separator
    public String changeBack (String intro){
        intro = intro.replaceAll( "     ", System.getProperty("line.separator"));
        return intro;
    }
    //this method sets the text for tvInfo to be the introduction of users
    public void read(){
        try{
            String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
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
                    if (arrayList.length > 1) {
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
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.llActivities:
                Intent ii = new Intent(this, otheruseractivity.class);
                Bundle bundlee = new Bundle();
                bundlee.putString("username", username);
                ii.putExtras(bundlee);
                startActivity(ii);
                break;
            case R.id.tvBack:
                finish();
                break;
        }
    }

}
