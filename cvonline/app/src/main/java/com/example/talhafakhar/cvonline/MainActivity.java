package com.example.talhafakhar.cvonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView lgn = (TextView) findViewById(R.id.l_register);

        lgn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), register.class);
                startActivity(i);
                //create_pdf();
            }
        });


        final EditText email = (EditText) findViewById(R.id.l_email);
        final EditText password = (EditText) findViewById(R.id.l_password);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable s) {
                final String email1 = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (email1.matches(emailPattern) && s.length() > 0) {
                    try{

                        email.setTextColor(Color.GREEN);
                        Button btn = (Button) findViewById(R.id.l_login);

                        btn.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View view) {
                                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                                        R.style.Theme_AppCompat_DayNight_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                progressDialog.setMessage("Authenticating...");

                                progressDialog.show();
                                if (password.length() > 1 && email.length() > 1) {
                                    try {
                                        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                                        final int id = db.check(email.getText().toString(), password.getText().toString());
                                        final String ids = String.valueOf(id);
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        if (id >= 0) {
                                                            Toast.makeText(getApplicationContext(), "Successfully Login" + ids, Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(getApplicationContext(), home.class);
                                                            i.putExtra("key", ids);
                                                            i.putExtra("email",email.getText().toString());

                                                            startActivity(i);
                                                            progressDialog.dismiss();
                                                        } else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(getApplicationContext(), "Login Failed" + id, Toast.LENGTH_SHORT).show();

                                                        }}},2000);

                                    } catch (Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), e.getMessage()
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Please Fill in all fields correctly", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    email.setTextColor(Color.RED);
                    //or

                }
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
        View v = findViewById(R.id.l_email);
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


    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();

    }
}
