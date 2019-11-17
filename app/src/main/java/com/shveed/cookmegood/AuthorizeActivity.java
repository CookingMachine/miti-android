package com.shveed.cookmegood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shveed.wallpapperparser.R;

public class AuthorizeActivity extends AppCompatActivity {

    Button signIn;
    Button signUp;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        signIn = (Button) findViewById(R.id.btn_signIn);
        signUp = (Button) findViewById(R.id.btn_signUp);

        View.OnClickListener signInListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSignIn();
            }
        };
        View.OnClickListener signUpListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSignUp();
            }
        };

        signIn.setOnClickListener(signInListener);
        signUp.setOnClickListener(signUpListener);
    }

    private void clickSignIn(){
        if(user != null) {
            Intent intent = new Intent(AuthorizeActivity.this, MainActivity.class);
            intent.putExtra("userObject", user);
            startActivity(intent);
        }
    }
    public void clickSignUp(){
        Intent intent = new Intent(AuthorizeActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void asGuest(View v){
        Intent intent = new Intent(this, MainActivity.class);
        User user = new User();
        intent.putExtra("userObject", user);
        startActivity(intent);
    }
}
