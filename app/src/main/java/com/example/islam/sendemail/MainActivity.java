package com.example.islam.sendemail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int All_Email = 0;
    public static final int Custum_EMail = 1;
    public SharedPreferences sharedPreferences;
    public TextView chooseEmail, Email;
    public EditText Subject, Messege;
    public Button Attach, Send;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("hello worled");
        setSupportActionBar(toolbar);


/*****************************************(Choose Email Button)***********************************/

        chooseEmail = (TextView) findViewById(R.id.choose);
        chooseEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder Mail = new AlertDialog.Builder(MainActivity.this);
                Mail.setItems(R.array.chooseEmail, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == All_Email) {
                            DataBase db = new DataBase(MainActivity.this);
                            String data = db.getAllEmail();
                            Email.setText(data);

                        } else if (which == Custum_EMail) {
                            Intent i = new Intent(getApplicationContext(), Custum_Email_List.class);
                            startActivity(i);

                        } else {
                            Intent i = new Intent(getApplicationContext(), AddEditEmail.class);
                            startActivity(i);
                        }
                    } // End Of AlertDialog
                });
                Mail.setTitle("Choose one");
                Mail.setCancelable(true);
                Mail.show();
            }
        }); // End of ChooseEmail click

/******************************************(Email TextView)***************************************/

        Email = (TextView) findViewById(R.id.Email);
        sharedPreferences = getSharedPreferences("custum", MODE_PRIVATE);
        Email.setText(sharedPreferences.getString("cutumEmail", ""));
/******************************************(subject TextView)*************************************/

        Subject = (EditText) findViewById(R.id.subject_title);
/********************************************(Messege TextView)***********************************/

        Messege = (EditText) findViewById(R.id.messege);
/*************************************************(Attach Button)*********************************/
        Attach = (Button) findViewById(R.id.attachfile);
        Attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DataBase db = new DataBase(MainActivity.this);
//                db.deleteAllEmail();
                Email.setText("");
            }
        });
/**********************************************(Send Button)**************************************/

        Send = (Button) findViewById(R.id.send);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
/*************************************************************************************************/

    }// End Of onCreate Method

/*************************************************************************************************/

    public void send() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Messege.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Email.getText().toString()});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, Subject.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("custum", MODE_PRIVATE);
        Email.setText(sharedPreferences.getString("custumEmail", ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("custumEmail").commit();

    }

    /*************************************************************************************************/

}
