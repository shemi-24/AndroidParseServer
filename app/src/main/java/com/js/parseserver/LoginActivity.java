package com.js.parseserver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {


    EditText login_username,login_password;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_username=findViewById(R.id.txt_login_username);
        login_password=findViewById(R.id.txt_login_password);
        login_button=findViewById(R.id.btn_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(login_username.getText().toString(), login_password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user!=null){
                            if(user.getBoolean("email verified")){
                                alertDisplayer("Login Successfull","Welcome, "+login_username.getText().toString()+"!",false );
                            }
                            else{
                                ParseUser.logOut();
                                alertDisplayer("Login fail","Please verify your email first",true);
                            }
                        }
                        else{
                            ParseUser.logOut();
                            alertDisplayer("Login Fail",e.getMessage()+"Please re-try",true);
                        }

                    }
                });

            }
        });
    }
    private void alertDisplayer(String title,String message,final boolean error){
        AlertDialog.Builder builder=new AlertDialog.Builder(this).
                setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error){
                            Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok=builder.create();
        ok.show();

    }
}