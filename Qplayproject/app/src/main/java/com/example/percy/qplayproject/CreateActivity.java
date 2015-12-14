package com.example.percy.qplayproject;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import android.support.v4.app.FragmentManager;



public class CreateActivity extends FragmentActivity implements View.OnClickListener{
    Button bCancel, bPost;
    TextView tvDate,tvTime;
    EditText etIntro, etLocation, etActName;
    String username;
    ImageView imgDate, imgTime;
    int year_X,month_X,day_X;
    static final int DIALOGTIME_ID = 1;
    int hour_x, minute_x,second_x;
    static final int DILOG_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        final Calendar cal = Calendar.getInstance();
        imgDate = (ImageView) findViewById(R.id.imgDate);
        imgTime = (ImageView) findViewById(R.id.imgTime);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        etActName = (EditText) findViewById(R.id.etActName);
        etIntro = (EditText) findViewById(R.id.etIntro);
        etLocation = (EditText) findViewById(R.id.etLocation);
        year_X = cal.get(Calendar.YEAR);
        month_X = cal.get(Calendar.MONTH);
        day_X = cal.get(Calendar.DAY_OF_MONTH);
        bCancel = (Button) findViewById(R.id.bCancel);
        bCancel.setTransformationMethod(null);
        bCancel.setOnClickListener(this);
        bPost = (Button) findViewById(R.id.bPost);
        bPost.setTransformationMethod(null);
        bPost.setOnClickListener(this);
        showDialogOnButtonClick();
        showTimePickerDialog();
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
    }
    // The method to show the time picker dialog by user pressing the clock icon
    public void showTimePickerDialog(){
        tvTime.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DIALOGTIME_ID);
                    }
                }
        );
        imgTime.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DIALOGTIME_ID);
                    }
                }
        );

    }
//    public void showDatePickerDialog(View v) {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(ft, "dialog");
//    }

// The method to show the date picker dialog by user pressing the calendar icon
    public void showDialogOnButtonClick (){
        tvDate.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DILOG_ID);
                    }
                }
        );
        imgDate.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        showDialog(DILOG_ID);
                    }
                }
        );
    }
//    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "timePicker");
//    }
    // methods to create dialogs for both time picker and date picker
    protected Dialog onCreateDialog (int id){
        if (id == DILOG_ID)
            return new DatePickerDialog(this, dpickerListener , year_X, month_X, day_X);
        else if(id == DIALOGTIME_ID)
            return new TimePickerDialog(this,kTimePIckerLIstenr ,hour_x,minute_x,false);
        return null;

    }
    // a method to set the time displayed
    protected TimePickerDialog.OnTimeSetListener kTimePIckerLIstenr =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tvTime = (TextView) findViewById(R.id.tvTime);
                    hour_x = hourOfDay;
                    minute_x = minute;
                    String mins;
                    if (Integer.toString(minute_x).equals("0"))
                        mins = "00";
                    else
                        mins = Integer.toString(minute_x);
                    tvTime.setText(Integer.toString(hour_x)+" : " + mins);
                    Toast.makeText(CreateActivity.this, hour_x + " : " + minute_x ,Toast.LENGTH_LONG).show();
                }
            };
    // a method to set the date displayed
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tvDate = (TextView) findViewById(R.id.tvDate);
            year_X = year;
            month_X = monthOfYear + 1;
            String month = null;
            day_X = dayOfMonth;
            if(month_X == 1)
                month = "Jan";
            else if(month_X == 2)
                month = "Feb";
            else if(month_X == 3)
                month = "Mar";
            else if(month_X == 4)
                month = "Apr";
            else if(month_X == 5)
                month = "May";
            else if(month_X == 6)
                month = "June";
            else if(month_X == 7)
                month = "July";
            else if(month_X == 8)
                month = "Aug";
            else if(month_X == 9)
                month = "Sept";
            else if(month_X == 10)
                month = "Oct";
            else if(month_X == 11)
                month = "Nov";
            else if(month_X == 12)
                month = "Dec";
            tvDate.setText(month + "  " + Integer.toString(day_X));
            Toast.makeText(CreateActivity.this, year_X + " / " + month_X + " / " + day_X, Toast.LENGTH_LONG).show();
        }
    };
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
    public void writeJoinInfo(String actName){
        BufferedWriter bw = null;
        try {
            String mycontent = actName + "/" + username;
            String filePath = getFilesDir().getPath().toString() + "/JoinInfo.txt";
            File file = new File(filePath);
            //file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(mycontent);
            bw.newLine();
            bw.close();
            Toast.makeText(getBaseContext(), "Successful creation",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // a method to write all the information related to the new created activity to the file
    public void writeInfo(String activityName,String intro, String date, String time, String location){
        BufferedWriter bw = null;
        try {
            String mycontent = username + "/" + activityName + "/" + date + "/" + time + "/" + location + "/" + intro;
            String filePath = getFilesDir().getPath().toString() + "/activities.txt";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(mycontent);
            bw.newLine();
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    //this method changes a line separator to be 5 spaces.
    public String changeIntro (String intro){
        intro = intro.replaceAll(System.getProperty("line.separator"), "     ");
        return intro;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCancel:
                finish();
                break;
            case R.id.bPost:
                String activityName = etActName.getText().toString().trim();
                String et_Intro = etIntro.getText().toString().trim();
                String txDate = tvDate.getText().toString().trim();
                String txTime = tvTime.getText().toString().trim();
                String et_Location = etLocation.getText().toString().trim();
                if (activityName.isEmpty()){
                    alert("Wrong input", "Please enter the name of your activity.");
                }
                else if (et_Intro.isEmpty()){
                    alert("Wrong input", "Please enter some introduction of your activity.");
                }
                else if (txDate.isEmpty()){
                    alert("Wrong input", "Please selete a date.");
                }
                else if (txTime.isEmpty()){
                    alert("Wrong input", "Please selete a Time.");
                }
                else if (et_Location.isEmpty()){
                    alert("Wrong input", "Please enter a location");
                }
                else{
                    et_Intro = changeIntro(et_Intro);
                    writeJoinInfo(activityName);
                    writeInfo(activityName, et_Intro, txDate, txTime, et_Location);
                    Intent i = new Intent(this, ActivityList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    i.putExtras(bundle);
                    finish();
                    startActivity(i);
                }
                break;

        }
    }
}
