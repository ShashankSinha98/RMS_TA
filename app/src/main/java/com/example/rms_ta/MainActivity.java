package com.example.rms_ta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyListData> listdata;

    Button add_btn;

    FirebaseFirestore firebaseFirestore;

    CollectionReference collectionReference;

    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("xxx","ONCREATE");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_btn = findViewById(R.id.add_btn_main);

        listdata = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        query = firebaseFirestore.collection("USER").orderBy("timestamp", Query.Direction.DESCENDING);



/*
        MyListData data1 = new MyListData("A","12","13 Feb 19");

        listdata.add(data1);
        listdata.add(data1);
        listdata.add(data1);
        listdata.add(data1);
        listdata.add(data1);
        listdata.add(data1);
*/



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
    }


    public void loadData(){


        query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Toast.makeText(MainActivity.this, "Error occured : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    listdata = new ArrayList<>();

                    if(!queryDocumentSnapshots.isEmpty()){

                        String id = "";

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            id = documentSnapshot.getId();

                            Log.d("xlr8_id",id);
                            MyListData myListData = documentSnapshot.toObject(MyListData.class);
                            listdata.add(myListData);
                            String name = myListData.getUsername();
                            Log.d("xlr8_name", name);
                        }

                        Log.d("xlr8_id_l",id);

                        if(queryDocumentSnapshots.size() > 10)
                        {
                            Log.d("xlr8_del: ",id);
                            firebaseFirestore.collection("USER").document(id).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(MainActivity.this, "Deleted.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(MainActivity.this, "Delete Failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        RecyclerView recyclerView = findViewById(R.id.recycler_view);
                        MyListAdapter adapter = new MyListAdapter(listdata);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }
}
