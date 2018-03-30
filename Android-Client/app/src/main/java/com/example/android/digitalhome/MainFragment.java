package com.example.android.digitalhome;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    View view;
    public Button mainbtn;
    public Button lightbtn;
    //public Button fanbtn;
    public Button logoutbtn;


    OkHttpClient client;
    Request request;
    boolean lmvar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_main, container, false);
        mainbtn= (Button) view.findViewById(R.id.mbutton);
        lightbtn= (Button) view.findViewById(R.id.blight);
        //fanbtn= (Button) view.findViewById(R.id.bfan);
        logoutbtn= (Button) view.findViewById(R.id.blogout);

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainbtn.getText().toString()=="ON") {
                    // mainbtn.setText("OFF");
                    lmvar= true;}
                else {//mainbtn.setText("ON");
                    lmvar=false;;}
                client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Item9Bool\"\r\n\r\n"+lmvar+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                request = new Request.Builder()
                        .url("https://home-automation-aries.herokuapp.com/api/update/2/")
                        .put(body)
                        .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                        .addHeader("Authorization", "Token 7675172a4f5fab39a37992885d5e98ac26167ba6")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Postman-Token", "f214161a-b23f-e70d-43dd-8cbd894e7717")
                        .build();
                //Response response = client.newCall(request).execute();
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
                        if(response.isSuccessful()) {
                            try {
                                if(lmvar){mainbtn.setText("OFF");}
                                else{mainbtn.setText("ON");}
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
            }
        });
        lightbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(getActivity(), LightActivity.class);
                startActivity(i);
            }
        });
        /*fanbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), FanActivity.class);
                startActivity(i);
            }
        });*/
        logoutbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences("myToken", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("token", null);
                editor.commit();
                BlankFragment fragmentOne= new BlankFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,fragmentOne);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
