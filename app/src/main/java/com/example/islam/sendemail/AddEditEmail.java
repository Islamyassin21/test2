package com.example.islam.sendemail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.islam.sendemail.model.MyEmail;

public class AddEditEmail extends AppCompatActivity {

    EditText addEmail;
    EditText addName;
    Button save, cancel;
    DataBase db = new DataBase(AddEditEmail.this);
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_email);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("hello worled");
        setSupportActionBar(toolbar);


        addEmail = (EditText) findViewById(R.id.add_email);
        addName = (EditText) findViewById(R.id.add_name);

/**********************************************(Cancel Button)************************************/

        cancel = (Button) findViewById(R.id.cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/**********************************************(Save Button)**************************************/

        save = (Button) findViewById(R.id.save_new_email);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((addEmail.length() == 0) || (addName.length() == 0)) {

                    Toast.makeText(getApplicationContext(), "Please Enter The Name & Email", Toast.LENGTH_LONG).show();

                } else {
                    MyEmail email = new MyEmail();
                    email.setEmail(addEmail.getText().toString().trim());
                    email.setName(addName.getText().toString().trim());

                    addEmail.setText("");
                    addName.setText("");
                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
                    Log.v("data:0", "islam yassin");

                    db.addEmail(email);
                    db.close();
                }
            }
        });
/**********************************************()*************************************************/

    } // End Of onCreate
}
