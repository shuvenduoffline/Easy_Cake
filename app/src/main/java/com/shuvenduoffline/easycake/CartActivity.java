package com.shuvenduoffline.easycake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView cakeList , carts;
    CakeListRvCartAdapter cakeadapter;
    CakeCartAdapter cartAdapter;
    CakeViewModel cakeViewModel;
    List<Cake> mycakeslist, mycartlis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cakeList = findViewById(R.id.rvCakeListCart);
        carts = findViewById(R.id.cartRv);


        try {

            cakeViewModel =  ViewModelProviders.of(this).get(CakeViewModel.class);
            cakeViewModel.creatCakeRepro(getApplication());

            // Create the observer which updates the UI.
            final Observer<List<Cake>> cakeObserver = new Observer<List<Cake>>() {
                @Override
                public void onChanged(@Nullable final List<Cake> cakes) {
                    // Update the UI, in this case, a TextView.
                    mycakeslist = cakes;
                    populateRvwithCakeData(mycakeslist);

                }
            };

            final Observer<List<Cake>> cartObserver = new Observer<List<Cake>>() {
                @Override
                public void onChanged(@Nullable final List<Cake> cakes) {
                    // Update the UI, in this case, a TextView.
                    mycartlis = cakes;
                    populatecart(mycartlis);

                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            cakeViewModel.getCakeList().observe(this, cakeObserver);
            cakeViewModel.getCartList().observe(this, cartObserver);

            Intent intent = getIntent();
            if (intent != null){
                int pos = intent.getIntExtra("position",0);
                cakeViewModel.addCart(cakeViewModel.getCakeList().getValue().get(pos));
            }
        }catch (Exception e){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void populatecart(List<Cake> cakes) {
        if (cakes == null) return;
        cartAdapter = new CakeCartAdapter(cakes, CartActivity.this, new CakeCartAdapter.MyAdapterListener() {
            @Override
            public void buttonRemoveListner(View v, int position) {
                cakeViewModel.removeCart(cakeViewModel.getCartList().getValue().get(position));
            }
        });
        carts.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        carts.setLayoutManager(mLayoutManager);
        carts.setItemAnimator(new DefaultItemAnimator());
        carts.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(carts);

    }

    private void populateRvwithCakeData(List<Cake> cakes) {
        if(cakes == null) return;

        cakeadapter = new CakeListRvCartAdapter(cakes, CartActivity.this, new CakeListRvCartAdapter.MyAdapterListener() {
            @Override
            public void buttonMoveClick(View v, int position) {
                cakeViewModel.addCart(cakeViewModel.getCakeList().getValue().get(position));
            }

            @Override
            public void buttonDeleteClick(View v, int position) {
                cakeViewModel.deleteCake(cakeViewModel.getCakeList().getValue().get(position));
            }
        });
        cakeList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        cakeList.setLayoutManager(mLayoutManager);
        cakeList.setItemAnimator(new DefaultItemAnimator());
        cakeList.setAdapter(cakeadapter);
        cakeadapter.notifyDataSetChanged();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |ItemTouchHelper.START | ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
           int frompostion = viewHolder.getAdapterPosition();
           int toposition = target.getAdapterPosition();
           Collections.swap(mycakeslist,frompostion,toposition);
           recyclerView.getAdapter().notifyItemMoved(frompostion,toposition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}
