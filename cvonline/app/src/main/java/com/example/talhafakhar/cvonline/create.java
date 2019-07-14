package com.example.talhafakhar.cvonline;

import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class create extends AppCompatActivity {
    static more_image obj;
    static String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final EditText name   = (EditText) findViewById(R.id.cname);
        final EditText fname  = (EditText) findViewById(R.id.cfname);
        final EditText age = (EditText) findViewById(R.id.cage);
        final EditText dob = (EditText) findViewById(R.id.dob);
        final EditText uni = (EditText) findViewById(R.id.q_uni);
        final EditText bs = (EditText) findViewById(R.id.q_edu);
        final EditText ms = (EditText) findViewById(R.id.ms_edu);
        final EditText job1 = (EditText) findViewById(R.id.e_pjob);
        final EditText job2 = (EditText) findViewById(R.id.e_pjob1);
        final EditText experience = (EditText) findViewById(R.id.e_expy);
        final String data = getIntent().getStringExtra("uid");

        if(getIntent().getStringExtra("email")!=null) {

            email = getIntent().getStringExtra("email");

        }

        if(getIntent().getStringExtra("from")!=null) {
            set_fields(email);

        }


        final Button next = (Button) findViewById(R.id.cnext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf_create();

                String id= "1";

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user-info").child(getIntent().getStringExtra("uid"));

                myRef.child("id").setValue(data);
                myRef.child("email").setValue(email);
                myRef.child("name").setValue(name.getText().toString());
                myRef.child("fname").setValue(fname.getText().toString());
                myRef.child("age").setValue(age.getText().toString());
                myRef.child("dob").setValue(dob.getText().toString());
                myRef.child("uni").setValue(uni.getText().toString());
                myRef.child("bs").setValue(bs.getText().toString());

                myRef.child("ms").setValue(ms.getText().toString());
                myRef.child("job1").setValue(job1.getText().toString());
                myRef.child("job2").setValue(job2.getText().toString());
                myRef.child("experience").setValue(experience.getText().toString());

                Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();



            }
        });
    }


    public void set_fields(String key)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query ref = firebaseDatabase.getReference().child("user-info").orderByChild("email").equalTo(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = (int) dataSnapshot.getChildrenCount();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    obj = ds.getValue(more_image.class);


                }
                final EditText name   = (EditText) findViewById(R.id.cname);
                final EditText fname  = (EditText) findViewById(R.id.cfname);
                final EditText age = (EditText) findViewById(R.id.cage);
                final EditText dob = (EditText) findViewById(R.id.dob);
                final EditText uni = (EditText) findViewById(R.id.q_uni);
                final EditText bs = (EditText) findViewById(R.id.q_edu);
                final EditText ms = (EditText) findViewById(R.id.ms_edu);
                final EditText job1 = (EditText) findViewById(R.id.e_pjob);
                final EditText job2 = (EditText) findViewById(R.id.e_pjob1);
                final EditText experience = (EditText) findViewById(R.id.e_expy);
                name.setText(obj.name);
                fname.setText(obj.fname);
                age.setText(obj.age);
                dob.setText(obj.dob);
                uni.setText(obj.uni);
                bs.setText(obj.bs);
                ms.setText(obj.ms);
                job1.setText(obj.job1);
                job2.setText(obj.job2);
                experience.setText(obj.experience);
                Log.d("pro count", String.valueOf(count));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    public void create_pdf()
    {
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(100, 100, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        /*Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(50, 50, 30, paint);*/
        View v = findViewById(R.id.cname);
        v.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);
        String f = "/first.pdf";
        String targetPdf =  Environment.getExternalStorageDirectory().getPath() + f ;
        Toast.makeText(this, targetPdf, Toast.LENGTH_SHORT).show();
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, targetPdf, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }
    public void pdf_create()
    {
        PdfDocument document = new PdfDocument();

        // repaint the user's text into the page

        // crate a page description

        //scroll


        View z =  findViewById(R.id.scroll); // parent view

        Bitmap b = loadBitmapFromView(z);
        Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);



        //end

        int pageNumber = 1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(),
                b.getHeight() - 20, pageNumber).create();

        // create a new page from the PageInfo
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bitmap, 0, 0, null);

        // do final processing of the page
        document.finishPage(page);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String pdfName = "pdf"
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        File outputFile = new File(Environment.getExternalStorageDirectory().getPath(), pdfName);

        try {
            outputFile.createNewFile();
            OutputStream out = new FileOutputStream(outputFile);
            document.writeTo(out);
            document.close();
            out.close();
        } catch (IOException e) {
            Log.d("issues",e.getMessage());
            e.printStackTrace();
        }


    }
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}




