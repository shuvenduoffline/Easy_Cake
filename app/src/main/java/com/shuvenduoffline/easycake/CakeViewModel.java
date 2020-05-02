package com.shuvenduoffline.easycake;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CakeViewModel extends ViewModel {
    public CakeReprosotiry cakerepro;
    public MutableLiveData<List<Cake>> cakelist ;
   public MutableLiveData<List<Cake>> cartlist = new MutableLiveData<>();

    public CakeViewModel() {
        List<Cake> arr = new ArrayList<>();
        cartlist.setValue(arr);
    }
    public void creatCakeRepro(Application application){
        if (cakerepro == null){
            cakerepro = new CakeReprosotiry(application, new CakeReprosotiry.OnCompelteLoading() {
                @Override
                public void loadingComplete(List<Cake> cakeList) {
                    cakelist.setValue(cakeList);
                }
            });
        }
    }

    public MutableLiveData<List<Cake>> getCakeList() {
        if(cakelist == null){
          cakelist  = new MutableLiveData<>();
          fetchdata();
        }
        return cakelist;
    }

    private void fetchdata() {
        cakerepro.getMutableLiveData();
    }


    public MutableLiveData<List<Cake>> getCartList() {
        return cartlist;
    }

    public void removeCart(Cake cake){
        List<Cake> list = cartlist.getValue();
        list.remove(cake);
        cartlist.setValue(list);
    }

    public void addCart(Cake cake){
        List<Cake> list = cartlist.getValue();
        list.add(cake);
        cartlist.setValue(list);
    }

    public void deleteCake(Cake cake) {
        List<Cake> list = cakelist.getValue();
        list.remove(cake);
        cakelist.setValue(list);
    }
}
