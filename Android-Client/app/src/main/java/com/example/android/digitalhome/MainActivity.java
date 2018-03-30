package com.example.android.digitalhome;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static Bundle bundle = new Bundle();

    SharedPreferences sharedPreferences;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("myToken", Context.MODE_PRIVATE);
        str = sharedPreferences.getString("token", null);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (str== null) {
            BlankFragment fragmentOne = new BlankFragment();
            fragmentTransaction.add(R.id.fragment, fragmentOne,"login");
            fragmentTransaction.commit();
        }
        else {
            MainFragment mainFragment= new MainFragment();
            fragmentTransaction.add(R.id.fragment,mainFragment,"main");
            fragmentTransaction.commit();
        }
    }
}