package com.example.useing_connecting_api;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText userEmail,userPassword,userFirstName,userLastName,userMobile;
    TextView response_txt;
    ConnectivityManager connectivityManager;
//    String url_string="https://randomuser.me/api/";
    String url_string = "http://192.168.154.1:3000/accountsService/createAccount";
    String line="";
    String readData="";

    Button btn_save,btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.txt_userEmail);
        userPassword = findViewById(R.id.txt_userPassword);
        userFirstName = findViewById(R.id.txt_userFirstName);
        userLastName = findViewById(R.id.txt_userLastName);
        userMobile = findViewById(R.id.txt_userMobile);
        response_txt = findViewById(R.id.txt_response);

        btn_save  = findViewById(R.id.btn_save);
        btn_clear = findViewById(R.id.btn_clear);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(url_string);
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(isDeviceConnected()){
//            startThreadForGettingDataUsingHttp();
//            getDataUsingVolley(url_string);
//            postData(url_string);
        }else{
            Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_SHORT).show();
        }
    }



    public void getDataUsingVolley(String url_string){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_string, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    public void postData(String url_string){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_string, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                response_txt.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response","Error is : "+error.getMessage());
                response_txt.setText(error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put("userEmail",userEmail.getText().toString());

                map.put("userPassword",userPassword.getText().toString());

                map.put("userFirstName",userFirstName.getText().toString());

                map.put("userLastName",userLastName.getText().toString());

                map.put("userMobile",userMobile.getText().toString());

                return map;
            }
        };

        queue.add(stringRequest);

    }



    public void  startThreadForGettingDataUsingHttp(){
        Thread  thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("network","Loading data");
                String data = getDataFromAPI(url_string);
                Log.d("network",data);
            }
        });
        thread.start();
    }


    public String getDataFromAPI(String url_string){
        try {
            URL url =  new URL(url_string);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine())!=null){
                readData+=line;
            }
            return readData;
        } catch (MalformedURLException e) {
          return   e.getMessage();
        } catch (IOException e) {
          return   e.getMessage();
        }

    }

    public boolean isDeviceConnected(){
        //1-Create connectivity Manager
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //2-get the info of all available network.
        NetworkInfo [] networkInfo =  connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info : networkInfo){
//            Log.d("network", String.valueOf(info.getState()));
            //4 states of connection .. like connected,connecting,suspended

//            If(info.isConnected()){
//
//            }

//            switch (info.getState()){
//                case CONNECTED:
//                    break;
//                case CONNECTING:
//                    break;
//                case DISCONNECTED:
//                    break;
//                case DISCONNECTING:
//                    break;
//                case SUSPENDED:
//                    break;
//
//            }

            if(info.getState()== NetworkInfo.State.CONNECTED){
//                Log.d("network",info.toString());
//                Log.d("network",info.getTypeName());
                return true;
            }

        }
        return false;
    }
}