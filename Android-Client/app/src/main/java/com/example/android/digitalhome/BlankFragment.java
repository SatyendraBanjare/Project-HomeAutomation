package com.example.android.digitalhome;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    View view;
    public EditText username;
    public EditText password;
    public Button btn;

    OkHttpClient client;
    Request request;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.userpassword);
        btn = (Button) view.findViewById(R.id.loginbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("Hello");
                String usrname = username.getText().toString();
                String pswd = password.getText().toString();
                client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\n"+usrname+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n"+pswd+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                request = new Request.Builder()
                        .url("https://home-automation-aries.herokuapp.com/api/get-auth-token/")
                        .post(body)
                        .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                        .addHeader("csrfmiddlewaretoken", "QcKL3lX85tCNjRiJb2epemlZ4EZ5nun7Ip3sOqK9UioCXg4JRTpnSbrT0coEDwWC")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Postman-Token", "ad2c219a-2483-df90-3c7f-d75980d82824")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try {
                                response = client.newCall(request).execute();
                                JSONObject myResponse = new JSONObject(response.body().string());
                                sharedPreferences = getContext().getSharedPreferences("myToken", Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("token", myResponse.getString("token"));
                                editor.commit();

                                Request request = new Request.Builder()
                                        .url("https://home-automation-aries.herokuapp.com/api/get-id/")
                                        .get()
                                        .addHeader("Authorization", "Token "+myResponse.getString("token"))
                                        .addHeader("Cache-Control", "no-cache")
                                        .addHeader("Postman-Token", "c0d322e9-3eba-f0d2-fd3a-de28252a9f11")
                                        .build();
                                Response idresponse = client.newCall(request).execute();
                                JSONArray arr=new JSONArray(idresponse.body().string());
                                JSONObject myIdResponse = arr.getJSONObject(0);
                                sharedPreferences = getContext().getSharedPreferences("myId", Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("id", myIdResponse.getString("id"));
                                editor.commit();

                                MainFragment mainFragment= new MainFragment();
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment,mainFragment);
                                fragmentTransaction.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
            }
        });


        return view;
    }
}
