package com.shveed.cookmegood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shveed.wallpapperparser.R;

public class AuthorizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
    }

    public void toMainPage(View view){
        Intent intent = new Intent(AuthorizeActivity.this, MainActivity.class);
        startActivity(intent);
        Log.d("CHECKPOINT", "MEME");
    }

    public void toRegistrationPage(View view){
        Intent intent = new Intent(AuthorizeActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
