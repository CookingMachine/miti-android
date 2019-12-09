package com.shveed.cookmegood.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shveed.cookmegood.LoginDialog;
import com.shveed.cookmegood.entity.User;
import com.shveed.wallpapperparser.R;

public class AuthorizeActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener {

    Button signIn;
    Button signUp;

    User user;

    EditText loginEdit;
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        loginEdit = (EditText)findViewById(R.id.loginEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);

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

    private void clickSignUp(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void clickSignIn(){
        openDialog();
    }
    public void asGuest(View v){
        Intent intent = new Intent(this, StartActivity.class);
        user = new User();
        intent.putExtra("userObject", user);
        startActivity(intent);
        finish();
    }

    public void openDialog(){
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), "Login dialog");
    }

    @Override
    public void loginUser(String login, String password) {
        if(login.equals("") || password.equals("")){
            goToast("Пустое поле!");
        }
        else {
            if (checkUserAccess(login, password)) {
                goToast("Добро пожаловать");
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
            }
            else{
                goToast("Неправильный логин или пароль");
            }
        }
    }

    public void goToast(String output){
        Toast errorToast = Toast.makeText(this,
                output, Toast.LENGTH_SHORT);
        errorToast.show();
    }

    public boolean checkUserAccess(String loginText, String passwordText){
        if(loginText.equals("1") && passwordText.equals("1")){
            return true;
        }
        else{
            return false;
        }
    }
}
