package com.example.islam.sendemail;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.sendemail.model.MyEmail;

import java.util.ArrayList;

public class Custum_Email_List extends AppCompatActivity {

    private DataBase db;
    private ArrayList<MyEmail> dbEmails = new ArrayList<>();
    private EmailAdapter emailAdapter;
    private ListView listView;
    private Button save;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custum__email__list);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("hello worled");
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.custum_email_list);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refreshData();
    }

    private void refreshData() {

        dbEmails.clear();
        db = new DataBase(getApplicationContext());

        ArrayList<MyEmail> emailFromDB = db.getAllDetails();

        for (int i = 0; i < emailFromDB.size(); i++) {

            String title = emailFromDB.get(i).getName();
            String content = emailFromDB.get(i).getEmail();
            int mid = emailFromDB.get(i).getItemId();

            MyEmail myEmail = new MyEmail();
            myEmail.setName(title);
            myEmail.setEmail(content);
            myEmail.setItemId(mid);

            dbEmails.add(myEmail);
        }

        db.close();

        emailAdapter = new EmailAdapter(Custum_Email_List.this, R.layout.custum_email_row, dbEmails);
        listView.setAdapter(emailAdapter);
        emailAdapter.notifyDataSetChanged();
    }

    public class  EmailAdapter extends ArrayAdapter<MyEmail> {

        Activity activity;
        int layoutresource;
        MyEmail myEmail;
        ArrayList<MyEmail> mData = new ArrayList<>();

        public EmailAdapter(Activity act, int resource, ArrayList<MyEmail> data) {
            super(act, resource, data);

            activity = act;
            layoutresource = resource;
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public MyEmail getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(MyEmail item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null || (row.getTag()) == null) {

                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(R.layout.custum_email_row, null);
                holder = new ViewHolder();

                holder.mName = (TextView) row.findViewById(R.id.custumer_name_list);
                holder.mEmail = (TextView) row.findViewById(R.id.custumer_email_list);
                holder.mChkBox = (CheckBox) row.findViewById(R.id.select_email);
                holder.mChar = (TextView) row.findViewById(R.id.frist_char);

                row.setTag(holder);

            } else {

                holder = (ViewHolder) row.getTag();
            }

            holder.myEmail = getItem(position);
            holder.mName.setText(holder.myEmail.getName());
            holder.mEmail.setText(holder.myEmail.getEmail());
            holder.mChar.setText(holder.myEmail.getEmail().substring(0, 1).toUpperCase());

            final ViewHolder finalHolder = holder;

            holder.mChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        String Email = finalHolder.myEmail.getEmail().toString();
                        String custum = db.custumEmailChoose(Email);

                        Log.v("data:11", custum);

                        SharedPreferences preferences = getSharedPreferences("custum", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("custumEmail", custum);
                        editor.commit();
                    }
                }
            });

            holder.mChar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder delete = new AlertDialog.Builder(Custum_Email_List.this);
                    delete.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            int id = finalHolder.myEmail.getItemId();
                            Toast.makeText(getApplicationContext(), "id = " + id, Toast.LENGTH_SHORT).show();

                            String name = finalHolder.myEmail.getName();
                             Toast.makeText(getApplicationContext(), name + " deleted", Toast.LENGTH_LONG).show();
                               db.deleteEmail(id);
                        }
                    });
                    delete.setNegativeButton("No", null);
                    delete.setTitle("Delete");
                    delete.setTitle("Are you sure you want to delete " + "\n" +" \"" + finalHolder.myEmail.getName().toUpperCase() + "\" " + " ?");
                    delete.show();
                }
            });

            return row;
        }

        class ViewHolder {

            MyEmail myEmail;
            TextView mName;
            CheckBox mChkBox;
            TextView mChar;
            TextView mId;
            TextView mEmail;

        }
    }
}
