package com.ahmed.martin.mang_readtree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter list_adapt ,type_adapter,date_adapter,payment_adapter;
    private ArrayList <String> phone_array=new ArrayList<>();
    private ArrayList <String> type_array=new ArrayList<>();
    private ArrayList <String> payment_array=new ArrayList<>();
    private ArrayList <String> date_array=new ArrayList<>();
    private ArrayList <post_details> posts = new ArrayList<>();
    private ArrayList <String> key = new ArrayList<>();
    private Spinner type,date,payment;
    private ListView phone_list;
    private String type_s,date_s,payment_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        type = findViewById(R.id.type);
        payment = findViewById(R.id.payment);
        date=findViewById(R.id.date);

        phone_list = findViewById(R.id.phone_list);
        list_adapt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,phone_array);
        phone_list.setAdapter(list_adapt);
        phone_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent t = new Intent(MainActivity.this,show_psot.class);
               t.putExtra("post", posts.get(position));
               t.putExtra("date",date_s);
                t.putExtra("type",type_s);
                t.putExtra("key",key.get(position));
                t.putExtra("payment",payment_s);
               startActivity(t);
            }
        });

        ref_type();


    }

    private void ref_type(){
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("saving");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                type_array.clear();
                int i=0;
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    type_array.add(data.getKey().toString());
                    i++;
                }
                if(i==dataSnapshot.getChildrenCount())
                    get_type();

                ref.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void get_type(){
        type_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type_array);
        type_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(type_adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type_s = type_array.get(position);
                payment_s="";
                date_s="";
                if(type_s.equals("public")){
                    ref_date();
                }else
                    ref_payment ();

            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void ref_payment() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("saving").child(type_s);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                         payment_array.clear();
                         int i=0;
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        payment_array.add(data.getKey().toString());
                        i++;
                    }
                    if(i==dataSnapshot.getChildrenCount())
                        get_payment();

                    ref.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void get_payment() {
        payment_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,payment_array);
        payment_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        payment.setAdapter(payment_adapter);
        payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payment_s = payment_array.get(position);
                ref_date();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ref_date() {
        final DatabaseReference ref;

        // if it public and free
        if(type_s.equals("public"))
           ref  =  FirebaseDatabase.getInstance().getReference().child("saving").child(type_s);
        else
         ref =  FirebaseDatabase.getInstance().getReference().child("saving").child(type_s).child(payment_s);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date_array.clear();
                int i=0;
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    date_array.add(data.getKey().toString());
                    i++;
                }
                if(i==dataSnapshot.getChildrenCount())
                    get_date();

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void get_date() {
        date_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,date_array);
        date_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        date.setAdapter(date_adapter);
        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                date_s = date_array.get(position);
                ref_phone();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ref_phone() {
        final DatabaseReference ref;
        // if it public and free
        if(type_s.equals("public"))
            ref  = FirebaseDatabase.getInstance().getReference().child("saving").child(type_s).child(date_s);
        else
            ref =  FirebaseDatabase.getInstance().getReference().child("saving").child(type_s)
                    .child(payment_s).child(date_s);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                phone_array.clear();
                posts.clear();
                key.clear();
                int i=0;
                 for(DataSnapshot data : dataSnapshot.getChildren()){
                     posts.add(data.getValue(post_details.class));
                     key.add(data.getKey().toString());
                     phone_array.add(data.getValue(post_details.class).getShare_phone());
                     list_adapt.notifyDataSetChanged();
                     i++;
                 }
                 ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
