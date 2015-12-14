package com.example.percy.qplayproject;
import android.app.AlertDialog;
import org.json.*;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bSignup, bCancel;
    EditText etUsername, etPassword, etEmail, etRepassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRepassword = (EditText) findViewById(R.id.etRePassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        bSignup = (Button) findViewById(R.id.bSignup);
        bSignup.setTransformationMethod(null);
        bCancel = (Button) findViewById((R.id.bCancel));
        bCancel.setTransformationMethod(null);
        bSignup.setOnClickListener(this);
        bCancel.setOnClickListener(this);

    }


    /*public void alert(String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        TextView myMsg = new TextView(this);
        myMsg.setText(message);
        myMsg.setTextSize(15);
        myMsg.setTextColor(Color.BLACK);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.setTitle(title);
        dialog.setView(myMsg);
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }*/
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
    // a method that takes user's input and store them in userinfo.txt file
    public void writeInfo(String email, String password, String name){
        BufferedWriter bw = null;
        try {
            String mycontent = email + "/"  + password + "/" + name;
            String filePath = getFilesDir().getPath().toString() + "/userinfo.txt";
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
            Toast.makeText(getBaseContext(), "Successful registration",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bSignup:
                String ed_email = etEmail.getText().toString().trim();
                String ed_Name = etUsername.getText().toString().trim();
                String ed_Password = etPassword.getText().toString().trim();
                String ed_Repass = etRepassword.getText().toString().trim();
                if (ed_email.isEmpty()){
                   alert("Wrong input", "Please enter your email.");
                }
               else if (ed_Name.isEmpty()){
                    alert("Wrong input", "Please enter your user name.");
                }
               else if (ed_Password.isEmpty()){
                    alert("Wrong input", "Please enter your password.");
                }
               else if (ed_Repass.isEmpty()){
                    alert("Wrong input", "Please re-enter your password.");
                }
                else if (!ed_Password.equals(ed_Repass)){
                    alert("Wrong input", "Password entered do not match");
                }
                else{
                    writeInfo(ed_email,ed_Password,ed_Name);
                    finish();
                }
                break;
            case R.id.bCancel:
                finish();
                break;
        }
    }
}