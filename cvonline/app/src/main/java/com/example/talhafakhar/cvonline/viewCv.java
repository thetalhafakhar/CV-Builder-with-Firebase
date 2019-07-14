package com.example.talhafakhar.cvonline;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import android.support.v7.app.AppCompatActivity;

public class viewCv extends AppCompatActivity {
    more_image obj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            final String uid = getIntent().getStringExtra("uid");
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            final List<CV_attributes> cr = db.get_cvdata(uid);

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);


            ScrollView sc = new ScrollView(this);
            sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            sc.setFillViewport(true);

            LinearLayout layout1 = new LinearLayout(this);
            layout1.setOrientation(LinearLayout.VERTICAL);

            LinearLayout layout2 = new LinearLayout(this);

            layout2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));


            ImageView img = new ImageView(this);


            img.setLayoutParams(new LinearLayout.LayoutParams(200, 95));

            img.setX(400);
            img.setY(100);
            sc.addView(layout1);
            layout1.addView(img);
            try {
                if (cr != null) {
                    for (int i = 0; i < cr.size(); i++) {
                        LinearLayout row = new LinearLayout(this);
                        row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));


                        Button btnTag = new Button(this);
                        btnTag.setLayoutParams(new LayoutParams(350, 150));
                        btnTag.setText("CV " + (i + 1));
                        int id = Integer.parseInt(cr.get(i).cv_id);
                        btnTag.setId(id);

                        row.addView(btnTag);

                        row.setGravity(Gravity.CENTER);
                        row.setX(400);
                        row.setY(100);
                        layout1.addView(row);

                        final String finalI = String.valueOf(id);
                        btnTag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent l = new Intent(getApplicationContext(), cv_view.class);
                                l.putExtra("key", finalI);
                                l.putExtra("uid", uid);
                                Toast.makeText(getApplicationContext(), "id" + finalI, Toast.LENGTH_SHORT).show();
                                startActivity(l);
                            }
                        });
                    }
                }
                layout.addView(sc);
                setContentView(layout);


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "View Cv " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "View Cv " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void getRef(final String key) {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query ref = firebaseDatabase.getReference().child("user-info").orderByChild("id").equalTo(key);
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
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), home.class);
        final String uid = getIntent().getStringExtra("uid");
        i.putExtra("key", uid);
        startActivity(i);
        super.onBackPressed();  // optional depending on your needs
    }
}
