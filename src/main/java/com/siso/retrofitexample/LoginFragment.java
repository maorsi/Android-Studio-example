package com.siso.retrofitexample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    TextView email;
    TextView password;
    Button login;
    Button register;

    IStartActivty iStartActivty;
    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.emailLoginId);
        password = view.findViewById(R.id.passwordLoginId);
        login = view.findViewById(R.id.loginButtonId);
        iStartActivty = (IStartActivty) getActivity();
        register = view.findViewById(R.id.registerButtonId);
        if (iStartActivty== null){
            Toast.makeText(getActivity()," iStartActivty == null",Toast.LENGTH_LONG).show();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();

                if( !emailString.isEmpty()){
                    String passwordString =  password.getText().toString();

                    if( !passwordString.isEmpty()){

                    //    Toast.makeText(getActivity()," good work",Toast.LENGTH_LONG).show();
                        iStartActivty.login(emailString ,passwordString );
                    }
                    else{
                        Toast.makeText(getActivity()," error at the password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity()," error at the email",Toast.LENGTH_LONG).show();
                }

            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStartActivty.inflateFragment("Register");
            }
        });

        return  view ;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
