package com.siso.retrofitexample;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.siso.retrofitexample.model.User;
import com.squareup.picasso.Picasso;

public class UserInfoActivity extends AppCompatActivity {

    ImageView avater ;
    TextView firstName;
    TextView lastName;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
    }

    private void init() {
        avater = findViewById(R.id.userInfoImageId) ;
        firstName = findViewById(R.id.firstNameId) ;
        lastName = findViewById(R.id.lastNameTextId) ;


        Intent intent = getIntent();
        user =  intent.getParcelableExtra(MainActivity.USER_ITEM);
        firstName.setText(user.getFirst_name());
        lastName.setText(user.getLast_name());
        Picasso.get()
                .load(user.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(avater);
    }
}
