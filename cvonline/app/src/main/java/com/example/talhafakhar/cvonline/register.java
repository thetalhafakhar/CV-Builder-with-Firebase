package com.example.talhafakhar.cvonline;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView lgn = (TextView) findViewById(R.id.r_login);

        lgn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        final EditText username = (EditText) findViewById(R.id.r_username);
        final EditText password = (EditText) findViewById(R.id.r_password);
        final EditText email = (EditText) findViewById(R.id.r_email);





        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable s) {
                String email1 = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                Button btn = (Button) findViewById(R.id.register);
                if (email1.matches(emailPattern) && s.length() > 0) {
                    email.setTextColor(Color.GREEN);
                    btn.setEnabled(true);
                    try {

                        btn.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View view) {

                                if (password.length() > 3 && username.length() > 2) {
                                    DatabaseHandler db = new DatabaseHandler(register.this);
                                    Long id = db.addContact(username.getText().toString(), email.getText().toString(), password.getText().toString());
                                    if (id >= 0) {
                                        Toast.makeText(getApplicationContext(), "Successfully registered an account" + id, Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Registeration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Please Fill in all fields correctly", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage()
                                , Toast.LENGTH_SHORT).show();


                    }

                }
                else {
                    email.setTextColor(Color.RED);
                    btn.setEnabled(false);
                    //or

                }
            }


        });
    }
}
