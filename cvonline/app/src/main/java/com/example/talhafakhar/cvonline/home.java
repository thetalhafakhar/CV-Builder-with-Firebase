package com.example.talhafakhar.cvonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;





public class home extends AppCompatActivity {
    static String uid;
    static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Button create = (Button) findViewById(R.id.create);
        Button view = (Button) findViewById(R.id.view);

        Button logout = (Button) findViewById(R.id.logout);
        uid = getIntent().getStringExtra("key");
        email =  getIntent().getStringExtra("email");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getApplicationContext(), uid+" <- id",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),create.class);

                i.putExtra("uid",uid);
                i.putExtra("email",email);
                Toast.makeText(getApplicationContext(), uid+" <- id",Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), uid+" <- id",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),cv_view.class);
                i.putExtra("uid",uid);
                i.putExtra("email",email);
                startActivity(i);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
