package com.example.percy.qplayproject;
import android.app.AlertDialog;
import android.content.*;
import android.widget.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginInterface extends AppCompatActivity implements View.OnClickListener{
    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_interface);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        bLogin = (Button) findViewById(R.id.blogin);
        bLogin.setTransformationMethod(null);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }
    public void saveInfo(View view){
        SharedPreferences sharedPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", etUsername.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.apply();
    }
    public void getInfo(View view){
        SharedPreferences sharedPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", "");
        String password = sharedPref.getString("password", "");
        if (!email.isEmpty() && !password.isEmpty()){
//            Intent i = new Intent(this, Profile.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("username", username);
//            i.putExtras(bundle);
//            startActivity(i);
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
    // a method that takes the text that user entered and compare them with the correct user account and password stored on userinfo.txt file.
    public boolean readInfo(String userid, String password){
        try {
            String filePath = getFilesDir().getPath().toString() + "/userinfo.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arrayList=str.split("/");
                if (arrayList[0].equals(userid)){
                    if(arrayList[1].equals(password)){
                        username = arrayList[2];
                        Toast.makeText(getBaseContext(), "Login successfully",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else
                        return false;
                }
            }
            in.close();
            return false;
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.blogin:
                String ed_Name = etUsername.getText().toString().trim();
                String ed_Password = etPassword.getText().toString().trim();
                if (ed_Name.isEmpty()){
                    alert("Wrong input", "Please enter your user name.");
                }
                else if (ed_Password.isEmpty()){
                    alert("Wrong input", "Please enter your password.");
                }
                else{
                    if(readInfo(ed_Name,ed_Password)){
                        Intent i = new Intent(this, Profile.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        i.putExtras(bundle);
                        startActivity(i);
                        etPassword.setText("");
                        etUsername.setText("");
                    }
                    else
                        alert("Wrong input", "Please enter the correct user name or password.");
                }

                break;

        }
    }

}
