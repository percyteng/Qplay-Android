package com.example.percy.qplayproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    Button bProfile, bActivities;
    TextView tvCancel, tvSave;
    EditText etName, etInfo;
    String username, introduction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bProfile = (Button) findViewById(R.id.bProfile);
        bActivities = (Button) findViewById(R.id.bActivities);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvSave = (TextView) findViewById(R.id.tvSave);
        etName = (EditText) findViewById(R.id.etName);
        etInfo = (EditText) findViewById(R.id.etInfo);
        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        bProfile.setOnClickListener(this);
        bProfile.setTransformationMethod(null);
        bActivities.setOnClickListener(this);
        bActivities.setTransformationMethod(null);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        etName.setText(username);
        //deleteFile();
        readInfo();
    }
    public void deleteFile(){
        String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
        File myFile = new File(filePath);
        myFile.delete();
    }
    // a method that reads the content of current user intro and display them in the setting interface
    public void readInfo(){
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
                    if (arrayList.length > 1 ) {
                        introduction = changeBack(arrayList[1]);
                        etInfo.setText(introduction);
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

    //this method changes a line separator to be 5 spaces
    public String changeIntro (String intro){
        intro = intro.replaceAll(System.getProperty("line.separator"), "     ");
        return intro;
    }
    //this method changes 5 spaces back to be a line separator
    public String changeBack (String intro){
        intro = intro.replaceAll( "     ", System.getProperty("line.separator"));
        return intro;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvCancel:
                String Name = etName.getText().toString().trim();
                Intent is = new Intent(this, Profile.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", Name);
                is.putExtras(bundle);
                startActivity(is);
                finish();
                break;
            case R.id.tvSave:
                String et_Name = etName.getText().toString().trim();
                String et_Intro = etInfo.getText().toString().trim();
                et_Intro = changeIntro(et_Intro);
                if(et_Name.equals(username) && et_Intro.equals(introduction)){ //when user didn't change anything
                    finish();
                }
                else if(et_Name.equals(username) && !et_Intro.equals(introduction)){ // when user only changed his/her introduction part
                    if(!checkIfNeedName()){ // if the username doesn't exist, we can directly write both user name and intro into the file
                        writeIntro(et_Name,et_Intro);
                    }
                    else{ // if the username does exist, we need to change the intro of the username.
                        changeIntro(et_Name,et_Intro);
                    }
                }
                else{ // when user changed both username and intro
                    changeJoinInfo(username, et_Name);
                    if (!checkIfNeedName()){ // if the username doesn't exist, we can directly write both user name and intro into userintro.txt file and we need to change the username in userinfo.txt
                        writeIntro(et_Name,et_Intro);
                        changeNameInfo(et_Name);
                    }
                    else{ // if the username already exists, we need to change the intro of the username in userintro.txt and change the user name in userinfo.txt
                        changeNameInfo(et_Name);
                        changeIntro(et_Name, et_Intro);
                    }
                }
                finish();
                Intent i = new Intent(this, Profile.class);
                Bundle bundles = new Bundle();
                bundles.putString("username", et_Name);
                i.putExtras(bundles);
                startActivity(i);
                break;
            case R.id.bActivities:
                finish();
                Intent ii = new Intent(this, ActivityList.class);
                Bundle bundlee = new Bundle();
                bundlee.putString("username", username);
                ii.putExtras(bundlee);
                startActivity(ii);
                break;
        }
    }
    // a method directly writes intro and and name into the userintro.txt file
    public void writeIntro(String name, String intro) {
        BufferedWriter bw = null;
        try {
            String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
            String mycontent = name + "/" + intro;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(mycontent);
            bw.newLine();
            bw.close();
            Toast.makeText(getBaseContext(), "Successfully saved",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // A method to change the users' names in JoinInfo.txt
    public void changeJoinInfo( String oldName, String newName){
        String filePath = getFilesDir().getPath().toString() + "/JoinInfo.txt";
        File file = new File(filePath);
        ArrayList<String> newString = new ArrayList<String>();
        ArrayList<String> store = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList.length > 1) {
                    if (!(arrayList[1].contains(oldName))) {
                        store.add(str);
                    } else {
                        newString.add(arrayList[0] + "/" + arrayList[1].replace(oldName, newName));
                    }
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
            for (int j = 0; j < newString.size(); j++) {
                bw.write(newString.get(j));
            }
            bw.newLine();
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // a method that reads the userintro.txt file but it ignores the old user content. It deletes the old file and creates a new one with the same name and updated content
    public void changeIntro(String name, String intro){
        String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
        File file = new File(filePath);
        ArrayList<String> store = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (!(arrayList[0].equals(name))) {
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
            bw.write(name + "/" + intro);
            bw.newLine();
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

   /* public boolean checkIfNeed(String name){
        try {
            String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList[0].equals(name)) {
                    return true;
                }
            }
            in.close();
            return false;
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
        return false;
    }*/
   // a method check if userintro.txt file already has that username
    public boolean checkIfNeedName(){
        try {
            String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList[0].equals(username)) {
                    return true;
                }
            }
            in.close();
            return false;
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
        return false;
    }
    /*
    public void changeNameInTro(String name, String intro){
        String filePath = getFilesDir().getPath().toString() + "/userintro.txt";
        File file = new File(filePath);
        ArrayList<String> store = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (!(arrayList[0].equals(username))) {
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
            bw.write(name + "/" + intro);
            bw.newLine();
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }*/
    // a method to rewrite userinfo.txt with the new content
    public void changeNameInfo(String name){
        String email = null;
        String password = null;
        String filePath = getFilesDir().getPath().toString() + "/userinfo.txt";
        File file = new File(filePath);
        ArrayList<String> store = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (!(arrayList[2].equals(username))) {
                    store.add(str);
                }
                else{
                    email = arrayList[0];
                    password = arrayList[1];
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
            bw.write(email + "/" + password + "/" + name);
            bw.newLine();
            bw.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
