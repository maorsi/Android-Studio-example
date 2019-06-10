package com.siso.retrofitexample.repository;

import com.siso.retrofitexample.model.LoginRequest;
import com.siso.retrofitexample.model.LoginResponse;
import com.siso.retrofitexample.model.ResponseUser;
import com.siso.retrofitexample.model.UserListRespone;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReqresApi {

    @GET("api/users")
    Call<UserListRespone> getUsers(@Query("page") Integer page ,
                                         @Query("per_page") Integer per_page    );


    @GET("api/users/{id}")
    Call<ResponseUser> getUser(@Path("id") Integer userId);

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
