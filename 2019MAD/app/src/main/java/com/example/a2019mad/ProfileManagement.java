package com.example.a2019mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a2019mad.database.DBHandler;

public class ProfileManagement extends AppCompatActivity {

    EditText et_profile_username, et_profile_dob, et_profile_password;
    RadioButton rd_profile_btn_male, rd_profile_btn_female;
    Button  btn_profile_add, btn_profile_update;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        et_profile_username = findViewById(R.id.et_profile_username);
        et_profile_dob = findViewById(R.id.et_profile_dob);
        et_profile_password = findViewById(R.id.et_profile_password);
        btn_profile_add = findViewById(R.id.btn_profile_add);
        btn_profile_update = findViewById(R.id.btn_profile_update);
        rd_profile_btn_male = findViewById(R.id.rd_profile_btn_male);
        rd_profile_btn_female = findViewById(R.id.rd_profile_btn_female);


        btn_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileManagement.this, EditProfile.class);
                startActivity(intent);
            }
        });

        btn_profile_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_profile_username.getText().toString();
                String dob = et_profile_dob.getText().toString();
                String password= et_profile_password.getText().toString();

                if(rd_profile_btn_male.isChecked()){
                    gender = "Male";
                }else{
                    gender = "Female";
                }

                DBHandler handler = new DBHandler(getApplicationContext());
                long newId = handler.addInfo(username, dob, password, gender);

                if(newId>0) {
                    Toast.makeText(ProfileManagement.this, "Data Inserted ! User ID" + newId, Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(intent);

                    et_profile_username.setText(null);
                    et_profile_dob.setText(null);
                    et_profile_password.setText(null);
                    rd_profile_btn_male.setChecked(true);
                    rd_profile_btn_female.setChecked(false);

                }
                else{
                    Toast.makeText(ProfileManagement.this, "Data Insertion Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}