package com.shveed.cookmegood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shveed.wallpapperparser.R;

public class AuthorizeActivity extends AppCompatActivity {

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.loggedIn = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
    }

    public void toMainPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        startActivity(intent);
    }

    public void toRegistrationPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        startActivity(intent);
    }
}
