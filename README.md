# Consuming-API-in-Andriod
This repository contains HttpUrlConnection and Volley based application example. In this you will find implementation of both ways to consume API. This repository show examples to send receive the data in both cases. 

## HttpUrlConnection
### Fetching data

1. Checking for internet connectivity using connectivity manager
` public boolean isDeviceConnected(){
connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo [] networkInfo =  connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info : networkInfo){
            if(info.getState()== NetworkInfo.State.CONNECTED){
                return true;
            }} `
2. Fetching data from an API
` public String getDataFromAPI(String url_string){
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
    }`

3.Start the thread and fetch data
`    public void  startThreadForGettingDataUsingHttp(){
        Thread  thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("network","Loading data");
                String data = getDataFromAPI(url_string);
                Log.d("network",data);
            }
        });
        thread.start();
    }`
## Volly

1. Add dependency: com.android.volley:volley:1.2.1
### Fetching data
`    public void getDataUsingVolley(String url_string){
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
    }`

### POSTING DATA
`public void postData(String url_string){
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
    }`

- 
