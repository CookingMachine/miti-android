package com.shveed.cookmegood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shveed.wallpapperparser.R;

public class RegisterActivity extends AppCompatActivity {

    EditText nameText;
    EditText emailText;
    EditText passwordText;

    Button regButton;

    Toast errorToast;

    // TODO доверстать страницу

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regButton = (Button) findViewById(R.id.btn_register);
        View.OnClickListener regListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameText = (EditText) findViewById(R.id.edit_name);
                emailText = (EditText) findViewById(R.id.edit_email);
                passwordText = (EditText) findViewById(R.id.edit_password);

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
                    User user = new User(name, email, password);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    //intent.putExtra("userObject", user);
                    startActivity(intent);
                }
            }
        };
        regButton.setOnClickListener(regListener);
    }

    private boolean checkEmptyInput(String line){
        return line.equals("");
    }
}
