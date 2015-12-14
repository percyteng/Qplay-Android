package com.example.percy.qplayproject;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserActivitylist extends AppCompatActivity implements View.OnClickListener{
    LinearLayout llOverall;
    String username;
    TextView tvTitleName;
    int subtract = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activitylist);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        llOverall = (LinearLayout) findViewById(R.id.llOverall);
        tvTitleName = (TextView) findViewById(R.id.tvTitleName);
        tvTitleName.setText(username);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        if (width < 2000 && height < 3000)
            subtract = 10;
        llOverall.setId(R.id.llOverall);
        ArrayList<String> data = getInfo();
        for(int i = 0; i < data.size(); i++){
            RelativeLayout reLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams Rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            reLayout.setLayoutParams(Rp);
            createLayout(reLayout, data.get(i));
            llOverall.addView(reLayout);
        }
    }
    public String[] readInfo(String actName){
        String[] store = new String[2];
        try{
            String filePath = getFilesDir().getPath().toString() + "/activities.txt";
            File myFile = new File(filePath);
            if(!myFile.exists()) {
                return null;
            }
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList[1].equals(actName)){
                    store[1] = arrayList[2];
                    store[0] = arrayList[0];
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
        return store;
    }
    public void createLayout (RelativeLayout relativeLayout, String actName){
        String[] store = readInfo(actName);
        String date = store[1];
        String name = store[0];

        TextView tvDate = new TextView(this);
        tvDate.setId(R.id.tvDate);
        tvDate.setText(date);
        tvDate.setTypeface(Typeface.DEFAULT_BOLD);
        RelativeLayout.LayoutParams layouttvDate = new RelativeLayout.LayoutParams(500, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layouttvDate.setMargins(50, 50, 50, 50);
        tvDate.setLayoutParams(layouttvDate);
        tvDate.setTextSize(40-subtract);
        tvDate.setTextColor(Color.parseColor("#202020"));

        TextView tvName = new TextView(this);
        tvName.setText(name);
        tvName.setTypeface(Typeface.DEFAULT_BOLD);
        tvName.setId(R.id.tvName);
        RelativeLayout.LayoutParams layouttvName = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layouttvName.setMargins(500, 0, 0, 20);
        layouttvName.addRule(RelativeLayout.BELOW,tvDate.getId());
        tvName.setLayoutParams(layouttvName);
        tvName.setTextSize(35-subtract);
        tvName.setTextColor(Color.parseColor("#1569C7"));

//        final Button button = new Button(this);
//        button.setText("Rate");
//        button.setTransformationMethod(null);
//        RelativeLayout.LayoutParams layoutButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutButton.setMargins(40, 40, 30, 0);
//        layoutButton.addRule(RelativeLayout.BELOW, tvDate.getId());
//        button.setTextSize(25);
//        button.setLayoutParams(layoutButton);
//        button.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundbutton));

        TextView tvActName = new TextView(this);
        tvActName.setText("\"" + actName + "\"");
        tvActName.setId(R.id.tvActName);
        tvActName.setTypeface(Typeface.DEFAULT_BOLD);
        RelativeLayout.LayoutParams layouttvActName = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layouttvActName.setMargins(500, 0, 0, 0);
        layouttvActName.addRule(RelativeLayout.BELOW, tvName.getId());
        tvActName.setLayoutParams(layouttvActName);
        tvActName.setTextSize(35-subtract);
        tvActName.setTextColor(Color.parseColor("#CD7F32"));

        View line = new View(this);
        RelativeLayout.LayoutParams layoutLine = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1);
        layoutLine.setMargins(0, 40, 0, 0);
        layoutLine.addRule(RelativeLayout.BELOW, tvActName.getId());
        line.setLayoutParams(layoutLine);
        line.setBackgroundColor(Color.parseColor("#808080"));

        relativeLayout.addView(tvDate);
        relativeLayout.addView(tvName);
//        relativeLayout.addView(button);
        relativeLayout.addView(tvActName);
        relativeLayout.addView(line);
    }
    public ArrayList<String> getInfo(){
        ArrayList<String> eventInfo = new ArrayList<String>();
        try {
            String filePath = getFilesDir().getPath().toString() + "/JoinInfo.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList.length > 1) {
                    if (arrayList[1].contains(username)) {
                        eventInfo.add(arrayList[0]);
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }
        return eventInfo;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
