package com.shuvenduoffline.easycake;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CakeReprosotiry {
    private Application application;
    public interface OnCompelteLoading {
        void loadingComplete(List<Cake> cakeList);
    }
    public  OnCompelteLoading listner;

    public CakeReprosotiry(Application application,OnCompelteLoading listner) {
        this.application = application;
        this.listner = listner;
    }

    public void getMutableLiveData() {
        List<Cake> templist = DataHolder.getInstance().getData();
        if (templist != null) {
            listner.loadingComplete(templist);
            return;
        }

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(application);
        String url = "http://kekizadmin.com/kekiz_api/actions.php?action=get_cakes&category=27";

// Request a string response from the provided URL.
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        // Display the first 500 characters of the response string.
                        //("Response is: " + response.substring(0, 500));
                        //Log.d("CakeList", "onResponse: " + response);
                        Toast.makeText(application, ""+response.toString(), Toast.LENGTH_LONG).show();
                        //[{"weight_id":"13","weight":"2 Kgs","layer_id":"3","layer":"1","price":"1500","pictures":"{\"file_name\":\"2e59f0e59512c6554952d566ff2163b3.jpg\"}"}]
                        try {
                            JSONArray array= response.getJSONArray("data");
                            List<Cake> cakeList = new ArrayList<>();
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object= array.getJSONObject(i);
                                JSONArray wlparr = object.getJSONArray("w_l_p");
                                JSONObject wlpobj = wlparr.getJSONObject(0);
                                String picstr = wlpobj.getString("pictures");
                                JSONObject picobj = new JSONObject(picstr);
                                Cake cake = new Cake(object.getString("cake_name"),wlpobj.getString("weight"),wlpobj.getString("price"),object.getString("id"),picobj.getString("file_name"));
                                cakeList.add(cake);
                            }

                            if (!cakeList.isEmpty()){
                                DataHolder.getInstance().setData(cakeList);
                               listner.loadingComplete(cakeList);
                            }else{
                                Toast.makeText(application, "Empty Data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(application, "Err"+e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(application, ""+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        queue.add(getRequest);

    }
}
