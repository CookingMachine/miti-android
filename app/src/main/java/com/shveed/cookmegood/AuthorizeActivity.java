package com.shveed.cookmegood;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shveed.cookmegood.entity.User;
import com.shveed.wallpapperparser.R;

public class AuthorizeActivity extends AppCompatActivity {

    Button signIn;
    Button signUp;

    String loginText;
    String passwordText;

    User user;

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

    private void clickSignUp(){

    }
    public void clickSignIn(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View loginDialog = layoutInflater.inflate(R.layout.login_alert, null);

        AlertDialog.Builder loginDialogBuilder = new AlertDialog.Builder(this);
        loginDialogBuilder.setView(loginDialog);

        EditText loginEdit = (EditText)findViewById(R.id.loginEdit);
        EditText passwordEdit = (EditText)findViewById(R.id.passwordEdit);

        loginDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Войти", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginText = loginEdit.getText().toString();
                        passwordText = passwordEdit.getText().toString();
                        user = new User();
                        //checkUser();
                        if(loginText.equals("") || passwordText.equals("")){
//                            Toast errorToast = Toast.makeText(getApplicationContext(),
//                                    "Пустое поле!", Toast.LENGTH_SHORT);
//                            errorToast.show();
                        }
                        else {
                            if (checkUserAccess(loginText, passwordText)) {
//                                Toast errorToast = Toast.makeText(getApplicationContext(),
//                                        "Добро пожаловать.", Toast.LENGTH_SHORT);
//                                errorToast.show();
                            }
                            else{
//                                Toast errorToast = Toast.makeText(getApplicationContext(),
//                                        "Неправильный логин или пароль", Toast.LENGTH_SHORT);
//                                errorToast.show();
                            }
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = loginDialogBuilder.create();
        alertDialog.show();
        if(user != null) {
            Intent intent = new Intent(AuthorizeActivity.this, StartActivity.class);
            intent.putExtra("userObject", user);
            startActivity(intent);
        }
    }
    public void asGuest(View v){
        Intent intent = new Intent(this, StartActivity.class);
        user = new User();
        intent.putExtra("userObject", user);
        startActivity(intent);
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
