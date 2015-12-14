package com.example.percy.qplayproject;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityList extends AppCompatActivity implements View.OnClickListener {
    Button bProfile,bActivities;
    ImageView imgPlus;
    LinearLayout overallLayout;
    String username;
    int subtract = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);
        overallLayout = (LinearLayout) findViewById(R.id.overallLayout);
        bActivities = (Button) findViewById(R.id.bActivities);
        bProfile = (Button) findViewById(R.id.bProfile);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        if (width < 2000 && height < 3000)
            subtract = 10;
        bProfile.setOnClickListener(this);
        bProfile.setTransformationMethod(null); //so that the text on the button wouldn't be capitalized
        bActivities.setTransformationMethod(null);
        imgPlus = (ImageView) findViewById(R.id.imgPlus);
        imgPlus.setOnClickListener(this);
        //A bundle passed from profile to keep tract of username
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        read("activities.txt");        // read the format and display it on the screen
    }
    // this method creates all the activities in the activities.txt in a fixed format
    public void delete(String file){
        String filePath = getFilesDir().getPath().toString() + "/" + file;
        File myFile = new File(filePath);
        myFile.delete();
    }
    //This method delete user information in the JoinInfo.txt file when an activity which this user joined is delected
    public void deleteJoin(String actName){
        String filePath = getFilesDir().getPath().toString() + "/JoinInfo.txt";
        File file = new File(filePath);
        ArrayList<String> store = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (!(arrayList[0].equals(actName))){
                    store.add(str);
                }
            }
            in.close();
            file.delete();
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
        BufferedWriter bw = null;
        try {
            File file1 = new File(filePath);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileWriter fw = new FileWriter(file1, true);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < store.size(); i++){
                bw.write(store.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    //This method removes the information related to the given activity from the activities.txt file
    public void deleteFile(String name,String actName, String date, String time, String location, String intro){
        String filePath = getFilesDir().getPath().toString() + "/activities.txt";
        File file = new File(filePath);
        ArrayList<String> store = new ArrayList<String>();
        intro = changeIntro(intro);
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (!(arrayList[0].equals(name) && arrayList[1].equals(actName) && arrayList[2].equals(date) && arrayList[3].equals(time) && arrayList[4].equals(location) && arrayList[5].equals(intro))) {
                    store.add(str);
                }
            }
            in.close();
            file.delete();
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
        BufferedWriter bw = null;
        try {
            File file1 = new File(filePath);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileWriter fw = new FileWriter(file1, true);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < store.size(); i++){
                bw.write(store.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // This method creats the layout for each activity created
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createLayout (final String name,final String actName,final String date,final String time,final String location,final String intro){
        TextView tvName = new TextView(this);
        tvName.setText(name);
        tvName.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams layouttvName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvName.setMargins(10, 10, 0, 0);
        tvName.setLayoutParams(layouttvName);
        tvName.setTextSize(30-subtract);
        tvName.setTextColor(Color.parseColor("#1569C7"));
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ActivityList.this, otheruserprofile.class);
                Bundle bundlee = new Bundle();
                bundlee.putString("username", name);
                bundlee.putString("currentuser", username);
                ii.putExtras(bundlee);
                startActivity(ii);
            }
        });{

        }

        TextView tvActName = new TextView(this);
        tvActName.setText("\"" + actName + "\"");
        tvActName.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams layouttvActName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvActName.setMargins(10, 10, 0, 0);
        tvActName.setLayoutParams(layouttvActName);
        tvActName.setTextSize(30 - subtract);
        tvActName.setTextColor(Color.parseColor("#CD7F32"));

        TextView tvDate = new TextView(this);
        tvDate.setText("Date: " + date);
        LinearLayout.LayoutParams layouttvDate = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvDate.setMargins(10, 10, 0, 0);
        tvDate.setLayoutParams(layouttvDate);
        tvDate.setTextSize(25 - subtract);
        tvDate.setTextColor(Color.parseColor("#9DC209"));

        TextView tvTime = new TextView(this);
        tvTime.setText("Time: " + time);
        LinearLayout.LayoutParams layouttvTime = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvTime.setMargins(10, 10, 0, 0);
        tvTime.setLayoutParams(layouttvTime);
        tvTime.setTextSize(25 - subtract);
        tvTime.setTextColor(Color.parseColor("#3399FF"));

        TextView tvLocation = new TextView(this);
        tvLocation.setText("Location: " + location);
        LinearLayout.LayoutParams layouttvLocation = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvLocation.setMargins(10, 10, 0, 0);
        tvLocation.setLayoutParams(layouttvLocation);
        tvLocation.setTextSize(25 - subtract);
        tvLocation.setTextColor(Color.parseColor("#342D7E"));

        TextView tvIntro = new TextView(this);
        tvIntro.setText("\""+ intro +  "\"");
        LinearLayout.LayoutParams layouttvIntro = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvIntro.setMargins(30, 30, 30, 30);
        layouttvIntro.gravity = Gravity.CENTER_HORIZONTAL;
        tvIntro.setLayoutParams(layouttvIntro);
        tvIntro.setTextSize(30-subtract);


        final Button button = new Button(this);
        button.setText("Join");
        button.setTransformationMethod(null);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        RelativeLayout.LayoutParams layoutButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutButton.setMargins(0, 0, 30, 20);
        layoutButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        button.setTextSize(20);
        button.setLayoutParams(layoutButton);
//        button.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                writeInfo(actName);
                finish();
                Intent ii = new Intent(ActivityList.this, ActivityList.class);
                Bundle bundlee = new Bundle();
                bundlee.putString("username", username);
                ii.putExtras(bundlee);
                startActivity(ii);
            }
        });


        RelativeLayout reLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams Rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        reLayout.setLayoutParams(Rp);
        reLayout.addView(button);

        if (username.equals("Percy Teng") || username.equals(name)) {
            Button deButton = new Button(this);
            deButton.setText("Delete");
            deButton.setTransformationMethod(null);
            deButton.setTypeface(Typeface.DEFAULT_BOLD);
            RelativeLayout.LayoutParams layoutDeButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutDeButton.setMargins(30, 0, 20, 0);
            layoutDeButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            deButton.setTextSize(20);
            deButton.setLayoutParams(layoutDeButton);
            deButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteFile(name, actName, date, time, location, intro);
                    deleteJoin(actName);
                    finish();
                    Intent ii = new Intent(ActivityList.this, ActivityList.class);
                    Bundle bundlee = new Bundle();
                    bundlee.putString("username", username);
                    ii.putExtras(bundlee);
                    startActivity(ii);
                }
            });
            reLayout.addView(deButton);
        }

        TextView tvJoining = new TextView(this);
        String joining = readInfo(actName);
        tvJoining.setTypeface(Typeface.DEFAULT_BOLD);
        tvJoining.setText("Joined: " + joining);
        LinearLayout.LayoutParams layouttvJoining = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layouttvJoining.setMargins(10, 0, 0, 50);
        tvJoining.setLayoutParams(layouttvJoining);
        tvJoining.setTextColor(Color.parseColor("#6F4E37"));
        tvJoining.setBackgroundColor(Color.parseColor("#d1d0ce"));
        tvJoining.setTextSize(25-subtract);

        View line = new View(this);
        LinearLayout.LayoutParams layoutLine = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        layoutLine.setMargins(0, 0, 0, 0);
        line.setLayoutParams(layoutLine);
        line.setBackgroundColor(Color.parseColor("#808080"));

        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LL.setLayoutParams(lp);
        LL.addView(tvName);
        LL.addView(tvActName);
        LL.addView(tvDate);
        LL.addView(tvTime);
        LL.addView(tvLocation);
        LL.addView(tvIntro);
        LL.addView(reLayout);
        LL.addView(tvJoining);
        LL.addView(line);
        overallLayout.addView(LL);
    }

    //This method reads the info of the user who is attending the activity passed as parameter from JoinInfo.txt
    public String readInfo (String actName) {
        try {
            String filePath = getFilesDir().getPath().toString() + "/JoinInfo.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String join = "";
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList = str.split("/");
                if (arrayList[0].equals(actName)) {
                    join = arrayList[1];
                    return join;
                }
            }
            in.close();
            return join;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

// this method adds a user to the activity passed as parameter in JoinInfo.txt
    public void writeInfo (String actName){
        String filePath = getFilesDir().getPath().toString() + "/JoinInfo.txt";
        File file = new File(filePath);
        String temp = username;
        ArrayList<String> store = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (!(arrayList[0].equals(actName))) {
                    store.add(str);
                }
                else{
                    if(!str.contains(temp))
                        temp = str + ", " + temp;
                    else
                        temp = str;
                }

            }
            in.close();
            file.delete();
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
        BufferedWriter bw = null;
        try {
            File file1 = new File(filePath);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileWriter fw = new FileWriter(file1, true);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < store.size(); i++){
                bw.write(store.get(i));
                bw.newLine();
            }
            if (temp.equals(username))
                bw.write(actName + "/" + temp);
            else
                bw.write(temp);
            bw.newLine();
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // a simple method popping up an alert dialog for the sake of testing
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
    //This method changes a line.separator to be 5 spaces.
    public String changeIntro (String intro){
        intro = intro.replaceAll(System.getProperty("line.separator"), "     ");
        return intro;
    }
    //This method changes 5 spaces back to be a line.separator
    public String changeBack (String intro){
        intro = intro.replaceAll( "     ", System.getProperty("line.separator"));
        return intro;
    }
    // this method reads the activities in the activities.txt file in a reverse order and call createLayout method.
    public void read(String file){
        try{
            String Name,ActName,intro, date, time, location;
            ArrayList<String []> store = new ArrayList<String []>();
            String filePath = getFilesDir().getPath().toString() + "/" + file;
            File myFile = new File(filePath);
            if(!myFile.exists()) {
                return;
            }
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                store.add(arrayList);
            }
            for(int i = store.size()-1; i >= 0; i--){
                Name = store.get(i)[0];
                ActName = store.get(i)[1];
                date = store.get(i)[2];
                time = store.get(i)[3];
                location = store.get(i)[4];
                intro = store.get(i)[5];
                intro = changeBack(intro);
                createLayout(Name, ActName, date, time, location, intro);
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
        switch (v.getId()) {
            case R.id.bProfile:
                Intent i = new Intent(this, Profile.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                i.putExtras(bundle);
                finish();
                startActivity(i);
                break;
            // have to transfer the bundle to createActivities class so that when createActivities starts activitylist class, the bundle can be transfered back
            case R.id.imgPlus:
                Intent ii = new Intent(this, CreateActivity.class);
                Bundle bundlee = new Bundle();
                bundlee.putString("username", username);
                ii.putExtras(bundlee);
                startActivity(ii);
                break;
        }
    }
}
