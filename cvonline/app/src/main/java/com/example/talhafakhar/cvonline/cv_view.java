package com.example.talhafakhar.cvonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.support.annotation.NonNull;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class cv_view extends AppCompatActivity {
    static more_image obj;
    static String email;
    static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_view);
        try {
            id = getIntent().getStringExtra("uid");
            email = getIntent().getStringExtra("email");
            Log.d("key",id);
            getRef(email);

            Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
            //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            //CV_attributes obj = db.get_cv(id);
            TextView t1 = (TextView) findViewById(R.id.cv_name);
            TextView t2 = (TextView) findViewById(R.id.cv_fname);
            TextView t3 = (TextView) findViewById(R.id.cv_age);
            TextView t4 = (TextView) findViewById(R.id.cv_dob);
            TextView t5 = (TextView) findViewById(R.id.cv_edu);
            TextView t6 = (TextView) findViewById(R.id.cv_school);

            TextView t8 = (TextView) findViewById(R.id.cv_uni);
         ;
            TextView t11 = (TextView) findViewById(R.id.cv_job);
            TextView t12 = (TextView) findViewById(R.id.cv_achi);
            TextView t13 = (TextView) findViewById(R.id.cv_pjob);

            t1.setText(obj.name);
            t2.setText(obj.fname);
            t3.setText(obj.age);
            t4.setText(obj.dob);
            t5.setText(obj.bs);
            t6.setText(obj.ms);
            t8.setText(obj.uni);
            t11.setText(obj.job1);
            t12.setText(obj.job2);
            t13.setText(obj.experience);


        }
        catch(Exception e)
        {
            Log.d("issue",e.getMessage());
            Toast.makeText(getApplicationContext(),"Cv View "+e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }
    public void getRef(final String key) {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query ref = firebaseDatabase.getReference().child("user-info").orderByChild("email").equalTo(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = (int) dataSnapshot.getChildrenCount();
                Log.d("proc count", key);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    obj = ds.getValue(more_image.class);

                }

                Log.d("pro count", String.valueOf(count));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int idd = item.getItemId();

        try {
            if (idd == R.id.action_settings) {
                String id = getIntent().getStringExtra("key");
                String uid = getIntent().getStringExtra("uid");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("user-info").child(uid).removeValue();

                Intent i= new Intent(getApplicationContext(),viewCv.class);
                i.putExtra("uid",uid);
                startActivity(i);
                return true;
            }
            else  if (idd == R.id.action_edit) {

                String uid = getIntent().getStringExtra("uid");

                Intent i= new Intent(getApplicationContext(),create.class);
                i.putExtra("uid",uid);
                i.putExtra("email",email);
                i.putExtra("from","cv");
                startActivity(i);
                return true;
            }
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT);
        }

        return super.onOptionsItemSelected(item);
    }





    }

