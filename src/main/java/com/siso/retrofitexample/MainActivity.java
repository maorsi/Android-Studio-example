package com.siso.retrofitexample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.siso.retrofitexample.model.User;
import com.siso.retrofitexample.model.UserRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerViewUsers ;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager ;
    public static  final String USER_ITEM = "USER_ITEM";
    private SearchView userListFilter;
    UserViewModel model;
    //   private ReqresApi reqresApi ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userListFilter = findViewById(R.id.userListFilterId);
      //  userList = new ArrayList<>();
        model = ViewModelProviders.of(this).get(UserViewModel.class);
    //    userList.add( new User("email1", " first_name", "last name", "avatr"));
   //     userList.add( new User("email2", " first_name", "last name", "avatr"));

        buildRecyclerView();
     //   getUser();
    //    getUsers();
        model.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

              //  userList = new ArrayList<>(users);
                userRecyclerViewAdapter.setUserList(users);
            }
        });


        userListFilter.setImeOptions(EditorInfo.IME_ACTION_DONE);

        userListFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                userRecyclerViewAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
/*
    private void getUsers() {
        Log.i(TAG, "getUsers: ");
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

        Call<UserListRespone> callUsers = reqresApi.getUsers(null,null);

        callUsers.enqueue(new Callback<UserListRespone>() {
            @Override
            public void onResponse(Call<UserListRespone> call, Response<UserListRespone> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:  fail" );
                    return;
                }
           //     UserListRespone userListRespone = response.body();
             //   userList.addAll(userListRespone.getData());
            //    userRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<UserListRespone> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getUser() {
        Log.i(TAG, "getUser: ");

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

        Call<ResponseUser> callUser = reqresApi.getUser(1);

        callUser.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "onResponse:  fail" );
                    return;
                }
                User user = response.body().getUser();

         //       userList.add(user);

                Log.i(TAG, "onResponse:   "  + user);

             //   userRecyclerViewAdapter.notifyDataSetChanged();
                userRecyclerViewAdapter.getFilter().filter("");

            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }

        });

    }
*/
    private void buildRecyclerView() {
        Log.i(TAG, "buildRecyclerView:  " );
        recyclerViewUsers = findViewById(R.id.recycler_view_users);
        recyclerViewUsers.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(new ArrayList<User>()) ;

        userRecyclerViewAdapter.setOnItemClickListener(new UserRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(MainActivity.this , UserInfoActivity.class);

                intent.putExtra(USER_ITEM, user);
                startActivity(intent);
            }
        });
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setAdapter(userRecyclerViewAdapter);

    }
}
