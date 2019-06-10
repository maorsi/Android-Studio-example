package com.siso.retrofitexample;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.siso.retrofitexample.model.LoginRequest;
import com.siso.retrofitexample.model.LoginResponse;

import com.siso.retrofitexample.repository.ReqresApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends AppCompatActivity implements IStartActivty {

    private ReqresApi reqresApi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        init();
    }

    private void init() {
        LoginFragment loginFragment = new LoginFragment();

        changeFragment(loginFragment , null ,false);

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
        reqresApi = retrofit.create(ReqresApi.class);
    }



    public void changeFragment(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();



        transaction.replace(R.id.start_fragmentLayout, fragment, tag);

        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void login(String email, String password) {
        Call<LoginResponse> loginResponseCall = reqresApi.loginUser(new LoginRequest(email,password));

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(StartActivity.this, response.errorBody().toString(),Toast.LENGTH_SHORT).show();


                    return;
                }
                Toast.makeText(StartActivity.this, response.body().toString(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StartActivity.this , MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(StartActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void register(String email, String password) {
        Intent intent = new Intent(StartActivity.this , MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void inflateFragment(String fragmentTag) {
        if(fragmentTag.equals("Login")){
            LoginFragment fragment = new LoginFragment();
            changeFragment(fragment, fragmentTag, true);
        }
        else if(fragmentTag.equals("Register")){
            RegisterFragment fragment = new RegisterFragment();
            changeFragment(fragment, fragmentTag, true);
        }
    }
}
