package com.example.a2019mad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a2019mad.database.DBHandler;

import java.util.List;

public class EditProfile extends AppCompatActivity {

    EditText et_edit_profile_username, et_edit_profile_dob, et_edit_profile_password;
    RadioButton rd_edit_profile_btn_male, rd_edit_profile_btn_female;
    Button btn_edit_profile_edit, btn_edit_profile_delete, btn_edit_profile_search;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_edit_profile_username = findViewById(R.id.et_edit_profile_username);
        et_edit_profile_dob = findViewById(R.id.et_edit_profile_dob);
        et_edit_profile_password = findViewById(R.id.et_edit_profile_password);
        btn_edit_profile_edit = findViewById(R.id.btn_edit_profile_edit);
        btn_edit_profile_delete = findViewById(R.id.btn_edit_profile_delete);
        btn_edit_profile_search = findViewById(R.id.btn_edit_profile_search);
        rd_edit_profile_btn_male = findViewById(R.id.rd_edit_profile_btn_male);
        rd_edit_profile_btn_female = findViewById(R.id.rd_edit_profile_btn_female);

        btn_edit_profile_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler handler = new DBHandler(getApplicationContext());
                List user =handler.readAll(et_edit_profile_username.getText().toString());

                if(user.isEmpty()){
                    Toast.makeText(EditProfile.this, "No User !", Toast.LENGTH_SHORT).show();
                    et_edit_profile_username.setText(null);
                }
                else{
                    Toast.makeText(EditProfile.this, "User Found ! User "+ user.get(0).toString(), Toast.LENGTH_SHORT).show();
                    et_edit_profile_username.setText(user.get(0).toString());
                    et_edit_profile_dob.setText(user.get(1).toString());
                    et_edit_profile_password.setText(user.get(2).toString());

                    if(user.get(3).toString().equals("Male")){
                        rd_edit_profile_btn_male.setChecked(true);
                    }
                    else{
                        rd_edit_profile_btn_female.setChecked(true);
                    }

                }

            }
        });

        btn_edit_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler handler = new DBHandler(getApplicationContext());

                if(rd_edit_profile_btn_male.isChecked()){
                    gender = "Male";
                }
                else {
                    gender = "female";
                }

                Boolean status = handler.updateInfo(et_edit_profile_username.getText().toString(),
                        et_edit_profile_dob.getText().toString(), et_edit_profile_password.getText().toString(),gender);
                
                if(status){
                    Toast.makeText(EditProfile.this, "User Updated !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfile.this, "User Deleted !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_edit_profile_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler handler = new DBHandler(getApplicationContext());

                handler.deleteInfo(et_edit_profile_username.getText().toString());
                Toast.makeText(EditProfile.this, "User Deleted !", Toast.LENGTH_SHORT).show();

                et_edit_profile_username.setText(null);
                et_edit_profile_dob.setText(null);
                et_edit_profile_password.setText(null);
                rd_edit_profile_btn_male.setChecked(false);
                rd_edit_profile_btn_female.setChecked(false);
            }
        });

    }
}