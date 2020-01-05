package com.shveed.cookmegood.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shveed.cookmegood.entity.User;
import com.shveed.wallpapperparser.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edit_name) EditText nameText;
    @BindView(R.id.edit_Email) EditText emailText;
    @BindView(R.id.edit_password) EditText passwordText;

    @BindView(R.id.btn_register) Button regButton;

    Toast errorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
    }

    private boolean checkEmptyInput(String line){
        return line.equals("");
    }

    public void toAuth(View view){
        Intent intent = new Intent(this, AuthorizeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void clickRegister(Button button){
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        String output = "Заполните все поля";

        if(checkEmptyInput(name) ||
                checkEmptyInput(email) ||
                checkEmptyInput(password)){
            errorToast = Toast.makeText(getApplicationContext(),
                    output, Toast.LENGTH_SHORT);
            errorToast.show();
        }
        else{
            User user = null;
            Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
            //intent.putExtra("userObject", user);
            startActivity(intent);
        }
    }
}
