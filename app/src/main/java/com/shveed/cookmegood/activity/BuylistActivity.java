package com.shveed.cookmegood.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.shveed.cookmegood.R;

import butterknife.ButterKnife;

public class BuylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buylist);
        ButterKnife.bind(this);
    }
}
