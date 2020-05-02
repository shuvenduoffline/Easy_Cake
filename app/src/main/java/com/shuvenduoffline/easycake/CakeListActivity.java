package com.shuvenduoffline.easycake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CakeListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    CakeRveAdapter cakeRveAdapter;
    CakeViewModel cakeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_list);

        recyclerView = findViewById(R.id.cakeRv);
        progressBar = findViewById(R.id.progressBar);


//// Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://kekizadmin.com/kekiz_api/actions.php?action=get_cakes&category=27";
//
//// Request a string response from the provided URL.
//        // prepare the Request
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        Log.d("Response", response.toString());
//                        // Display the first 500 characters of the response string.
//                        //("Response is: " + response.substring(0, 500));
//                        //Log.d("CakeList", "onResponse: " + response);
//                   //     Toast.makeText(CakeListActivity.this, ""+response.toString(), Toast.LENGTH_LONG).show();
//                        //[{"weight_id":"13","weight":"2 Kgs","layer_id":"3","layer":"1","price":"1500","pictures":"{\"file_name\":\"2e59f0e59512c6554952d566ff2163b3.jpg\"}"}]
//                        try {
//                            JSONArray array= response.getJSONArray("data");
//                            List<Cake> cakeList = new ArrayList<>();
//                            for(int i=0;i<array.length();i++)
//                            {
//                                JSONObject object= array.getJSONObject(i);
//                                JSONArray wlparr = object.getJSONArray("w_l_p");
//                                JSONObject wlpobj = wlparr.getJSONObject(0);
//                                String picstr = wlpobj.getString("pictures");
//                                JSONObject picobj = new JSONObject(picstr);
//                                Cake cake = new Cake(object.getString("cake_name"),wlpobj.getString("weight"),wlpobj.getString("price"),object.getString("id"),picobj.getString("file_name"));
//                                cakeList.add(cake);
//                            }
//
//                            if (!cakeList.isEmpty()){
//                                populateRvwithCakeData(cakeList);
//                            }
//                        } catch (Exception e) {
        //thi is changes

try {

    cakeViewModel =  ViewModelProviders.of(this).get(CakeViewModel.class);
    cakeViewModel.creatCakeRepro(getApplication());

    // Create the observer which updates the UI.
    final Observer<List<Cake>> cakeObserver = new Observer<List<Cake>>() {
        @Override
        public void onChanged(@Nullable final List<Cake> cakes) {
            // Update the UI, in this case, a TextView.
           // Toast.makeText(CakeListActivity.this, "Live data Udated!!", Toast.LENGTH_SHORT).show();
            populateRvwithCakeData(cakes);

        }
    };

    // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
    cakeViewModel.getCakeList().observe(this, cakeObserver);
}catch (Exception e){
    Toast.makeText(this, ""+e.toString(), Toast.LENGTH_LONG).show();
}


    }

    private void populateRvwithCakeData(List<Cake> cakeList) {
        if (cakeList == null ) return;
        //for the cakedapter
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        cakeRveAdapter = new CakeRveAdapter(cakeList, CakeListActivity.this, new CakeRveAdapter.MyAdapterListener() {
            @Override
            public void buttonAddCart(View v, int position) {
           try {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                intent.putExtra("position",position);
               startActivity(intent);
           }catch (Exception e){
               Toast.makeText(CakeListActivity.this, ""+e.toString(), Toast.LENGTH_LONG).show();
           }
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cakeRveAdapter);
        cakeRveAdapter.notifyDataSetChanged();
    }
}
