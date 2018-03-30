package com.example.android.digitalhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Rishabh Tanwar on 11/28/2017.
 */

public class FanActivity extends AppCompatActivity {

    private static Bundle bundle = new Bundle();
    ToggleButton ftoggle1;
    ToggleButton ftoggle2;

    SeekBar fseekbar1;
    SeekBar fseekbar2;

    Button btnSendHttpRequest2;
    OkHttpClient client;
    Request request;

    Boolean myvar;
    Boolean myvar2;
    int progress1=100;
    int progress2=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fanactivity);

        myvar=bundle.getBoolean("ToggleButtonState1", false);
        myvar2=bundle.getBoolean("ToggleButtonState2", false);

        ftoggle1 = (ToggleButton) findViewById(R.id.ftoggleButton1);
        ftoggle2 = (ToggleButton) findViewById(R.id.ftoggleButton2);

        fseekbar1 = (SeekBar) findViewById(R.id.fseekBar1);
        fseekbar2 = (SeekBar) findViewById(R.id.fseekBar2);

        ftoggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {myvar = true;}
                else {myvar = false;}
            }
        });
        ftoggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {myvar2 = true;}
                else {myvar2 = false;}
            }
        });
        fseekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress1=i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        fseekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress2=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSendHttpRequest2 = (Button) findViewById(R.id.sendbutton2);
        btnSendHttpRequest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = new OkHttpClient();
                myfunction(myvar, myvar2);

            }
        });
    }
    private void myfunction(Boolean lmyvar , Boolean lmyvar2) {

        /**SharedPreferences tokenSharedPreferences=getSharedPreferences("myToken", Context.MODE_PRIVATE);
        String strToken = tokenSharedPreferences.getString("token", null);
        SharedPreferences idSharedPreferences=getSharedPreferences("myId", Context.MODE_PRIVATE);
        String strId = idSharedPreferences.getString("id", null);*/

                MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Item3Bool\"\r\n\r\n"+lmyvar+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Item3Value\"\r\n\r\n"+String.valueOf(progress1)+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Item4Bool\"\r\n\r\n"+lmyvar2+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Item4Value\"\r\n\r\n"+String.valueOf(progress2)+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                request = new Request.Builder()
                        .url("https://home-automation-aries.herokuapp.com/api/update/2/")
                        .put(body)
                        .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                        .addHeader("Authorization", "Token 7675172a4f5fab39a37992885d5e98ac26167ba6")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Postman-Token", "c15ae0f5-decd-c961-7e4b-95109a850d6a")
                        .build();

                /** Response response = client.newCall(request).execute();*/
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(FanActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String mMessage = response.body().string();
                        if (response.isSuccessful()) {
                            try {
                                /** JSONObject json = new JSONObject(mMessage);
                                 final String serverResponse = json.getString("Your Index");*/
                                response= client.newCall(request).execute();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }


    @Override
    public void onPause() {
        super.onPause();
        bundle.putBoolean("ToggleButtonState1", ftoggle1.isChecked());
        bundle.putBoolean("ToggleButtonState2", ftoggle2.isChecked());

        bundle.putInt("SeekBarState1", fseekbar1.getProgress());
        bundle.putInt("SeekBarState2", fseekbar2.getProgress());

    }
    @Override
    public void onResume() {
        super.onResume();
        ftoggle1.setChecked(bundle.getBoolean("ToggleButtonState1", false));
        ftoggle2.setChecked(bundle.getBoolean("ToggleButtonState2", false));

        fseekbar1.setProgress(bundle.getInt("SeekBarState1", 100));
        fseekbar2.setProgress(bundle.getInt("SeekBarState2", 100));

    }
}
