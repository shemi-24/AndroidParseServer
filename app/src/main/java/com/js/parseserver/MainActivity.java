package com.js.parseserver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.txtusername);
        email=findViewById(R.id.txtemail);
        password=findViewById(R.id.txtpassword);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        login=findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                try{
                    ParseUser user=new ParseUser();
                    user.setEmail(email.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                ParseUser.logOut();
                                alertDisplayer("Account Created Successfully","Please Verify your email before login",false);
                            }
                            else{
                                ParseUser.logOut();
                                alertDisplayer("Error Account Creation failed","Account could not be created"+":"+e.getMessage(),true);
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }

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
                            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok=builder.create();
        ok.show();

    }
}