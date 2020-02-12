package com.shveed.cookmegood.activity;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shveed.cookmegood.adapter.LoginDialogAdapter;
import com.shveed.cookmegood.data.NetworkService;
import com.shveed.cookmegood.entity.User;
import com.shveed.wallpapperparser.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizeActivity extends AppCompatActivity
        implements LoginDialogAdapter.LoginDialogListener {

    @BindView(R.id.btn_signIn) Button signIn;
    @BindView(R.id.btn_signUp) Button signUp;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_signIn)
    public void clickSignIn(Button button){
        LoginDialogAdapter loginDialog = new LoginDialogAdapter();
        loginDialog.show(getSupportFragmentManager(), "Login dialog");
    }

    @OnClick(R.id.btn_signUp)
    public void clickSignUp(Button button){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void asGuest(View v){
        Intent intent = new Intent(this, StartActivity.class);
        user = new User();
        startActivity(intent);
        finish();
    }

    @Override
    public void loginUser(String login, String password) {
        if(login.equals("") || password.equals("")){
            goToast("Пустое поле!");
        }
        else {
            doAuth(login, password);
        }
    }

    public void goToast(String output){
        Toast errorToast = Toast.makeText(this, output, Toast.LENGTH_SHORT);
        errorToast.show();
    }

    private void doAuth(String login, String pass){
        NetworkService.getInstance()
                .getUserApi()
                .checkUser(login, pass)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<User> call,
                            @NonNull Response<User> response) {
                        user = response.body();
                        if(user == null){
                            goToast("Неправильный логин или пароль");
                        }
                        else{
                            goToast("Добро пожаловать, " + login);
                            Intent intent = new Intent(AuthorizeActivity.this,
                                    StartActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<User> call,
                            @NonNull Throwable t) {
                        goToast(t.getLocalizedMessage());
                    }
                });

    }
}
