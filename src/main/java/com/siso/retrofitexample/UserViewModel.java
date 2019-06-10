package com.siso.retrofitexample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.siso.retrofitexample.model.ResponseUser;
import com.siso.retrofitexample.model.User;
import com.siso.retrofitexample.model.UserListRespone;
import com.siso.retrofitexample.repository.ReqresApi;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel  extends ViewModel {
    private MutableLiveData<List<User>> usersList ;

    public LiveData<List<User>> getUsers(){

        if (usersList == null) {
            usersList = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadUsers();
        }

        //finally we will return the list
        return usersList;
    }

    private void loadUsers() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        ReqresApi  reqresApi = retrofit.create(ReqresApi.class);

        Call<UserListRespone> callUser = reqresApi.getUsers(null,null);

        callUser.enqueue(new Callback<UserListRespone>() {
            @Override
            public void onResponse(Call<UserListRespone> call, Response<UserListRespone> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                UserListRespone userListRespone = response.body();

                usersList.setValue( userListRespone.getData());
            }

            @Override
            public void onFailure(Call<UserListRespone> call, Throwable t) {

            }
        });
    }
}
