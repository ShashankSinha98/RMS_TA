package com.example.rms_ta;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    FirebaseFirestore db;

    EditText name_et, age_et;
    Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = FirebaseFirestore.getInstance();

        name_et = findViewById(R.id.input_name_et);
        age_et = findViewById(R.id.input_age_et);
        add_btn = findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_et.getText().toString();
                String age = age_et.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(age)){
                    Toast.makeText(AddActivity.this, "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                } else {
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String date = df.format(c);

                   // Log.d("xlr8",name+" "+age+" "+date);

                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();


                    MyListData note = new MyListData(name,age,date,ts);


                    db.collection("USER").document().set(note)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(AddActivity.this, "Add Successful.", Toast.LENGTH_SHORT).show();
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(AddActivity.this, "Add Failed.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
}
