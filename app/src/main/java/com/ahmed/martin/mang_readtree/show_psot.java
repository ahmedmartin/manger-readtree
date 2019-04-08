package com.ahmed.martin.mang_readtree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class show_psot extends AppCompatActivity {

    private post_details post;
    private post_details share_post;
    private TextView book_name,address,category,part,sale_price,rent_price,description,ad_name,ad_phone,date,type;
    private LinearLayout sale,rent;
    private String date_s,type_s,key,payment_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_psot);

        post = (post_details) getIntent().getSerializableExtra("post");
        date_s = getIntent().getStringExtra("date");
        type_s = getIntent().getStringExtra("type");
        key = getIntent().getStringExtra("key");
        payment_s = getIntent().getStringExtra("payment");
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, payment_s, Toast.LENGTH_SHORT).show();


        book_name = findViewById(R.id.book_name);
        address=findViewById(R.id.address);
        category=findViewById(R.id.category);
        part=findViewById(R.id.part);
        sale_price=findViewById(R.id.sale_price);
        rent_price=findViewById(R.id.rent_price);
        description=findViewById(R.id.description);
        ad_name=findViewById(R.id.adviser_name);
        ad_phone=findViewById(R.id.adviser_phone);
        sale=findViewById(R.id.sale);
        rent=findViewById(R.id.rent);
        type=findViewById(R.id.type);
        date=findViewById(R.id.date);

        type.setText(type_s);
        date.setText(date_s);
        book_name.setText(post.getBook_name());
        address.setText(post.getAddress());
        category.setText(post.getCategory());
        part.setText(post.getPart());
        description.setText(post.getDescription());
        ad_name.setText(post.getAdvisor_name());
        ad_phone.setText(post.getAdvisor_phone());
        if(post.getSale()!=null){
            sale_price.setText(post.getSale());
        }else{
            sale.setVisibility(View.INVISIBLE);
        }

        if(post.getRent()!=null){
            rent_price.setText(post.getRent());
        }else{
            rent.setVisibility(View.INVISIBLE);
        }

    }

    public void share_post(View view) {
        put_data();
        switch (type_s){
            case "public" : _public();     break;
            case "home" : home();  break;
            case "out_category" :  out_category();   break;
            case  "in_category" : in_category();   break;
        }




    }

    private void put_data() {
        share_post=new post_details(description.getText().toString(),ad_name.getText().toString(),ad_phone.getText().toString()
                  ,address.getText().toString());

        if(post.getSale()!=null)
            share_post.setSale(post.getSale());

        if(post.getRent()!=null)
            share_post.setRent(post.getRent());

        switch (type_s){
            case "home":{share_post.setBook_name(book_name.getText().toString());
                share_post.setCategory(category.getText().toString());
                share_post.setPart(part.getText().toString());
                break;
            }
            case "in_category":{
                share_post.setBook_name(book_name.getText().toString());
                break;
            }
            case "out_category" :{
                share_post.setBook_name(book_name.getText().toString());
                share_post.setPart(part.getText().toString());
                break;
            }

        }


    }

    public void delete(View view) {
    }

    private void _public(){
        DatabaseReference post_ref = FirebaseDatabase.getInstance().getReference().child("book").child(category.getText().toString())
                .child(part.getText().toString()).child(book_name.getText().toString()).child(date_s).push();

        DatabaseReference user_ref =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid()).child("published")
                        .child("public").child(date_s).child(post_ref.getKey()).child(category.getText().toString()) .child(part.getText().toString());
        DatabaseReference user_remove =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("saving").child(type_s).child(date_s).child(key);
        DatabaseReference post_remove =  FirebaseDatabase.getInstance().getReference().child("saving")
                .child(type_s).child(date_s).child(key);


        post_ref.setValue(share_post);
        user_ref.setValue(book_name.getText().toString());
        user_remove.removeValue();
        post_remove.removeValue();
        finish();

    }
    private void  home(){
       DatabaseReference post_ref= FirebaseDatabase.getInstance().getReference().child("book").child("home").child(date_s).push();
        DatabaseReference user_ref =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("published").child("home").child(post_ref.getKey());
        DatabaseReference user_remove =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("saving").child(type_s).child(date_s).child(key);
        DatabaseReference post_remove =  FirebaseDatabase.getInstance().getReference().child("saving")
                .child(type_s).child(payment_s).child(date_s).child(key);

        post_ref.setValue(share_post);
             user_ref.setValue(date_s);
                user_remove.removeValue();
                post_remove.removeValue();
                finish();

    }
    private void  in_category(){
        DatabaseReference  post_ref= FirebaseDatabase.getInstance().getReference().child("book").child(post.getCategory())
                .child(post.getPart()).child("in_category").child(date_s).push();
        DatabaseReference user_ref =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("published").child("in_category").child(date_s).child(post_ref.getKey()).child(post.getCategory());
        DatabaseReference user_remove =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("saving").child(type_s).child(date_s).child(key);
        DatabaseReference post_remove =  FirebaseDatabase.getInstance().getReference().child("saving")
                .child(type_s).child(payment_s).child(date_s).child(key);

        post_ref.setValue(share_post);
            user_ref.setValue(post.getPart());
                user_remove.removeValue();
                post_remove.removeValue();

                finish();

    }
    private void out_category(){
      DatabaseReference  post_ref= FirebaseDatabase.getInstance().getReference().child("book")
              .child(post.getCategory()).child("out_category").child(date_s).push();
        DatabaseReference user_ref =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("published").child("out_category").child(date_s).child(post_ref.getKey());
        DatabaseReference user_remove =  FirebaseDatabase.getInstance().getReference().child("users").child(post.getUid())
                .child("saving").child(type_s).child(date_s).child(key);
        DatabaseReference post_remove =  FirebaseDatabase.getInstance().getReference().child("saving")
                .child(type_s).child(payment_s).child(date_s).child(key);

        post_ref.setValue(share_post);
             user_ref.setValue(post.getCategory());
                user_remove.removeValue();
                post_remove.removeValue();
                finish();

    }
}
