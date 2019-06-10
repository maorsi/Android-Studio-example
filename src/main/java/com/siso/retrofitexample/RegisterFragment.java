package com.siso.retrofitexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class RegisterFragment extends Fragment {
    TextView email;
    TextView confirmPassword;
    TextView password;
    Button register;
    Button toLogin;
    IStartActivty iStartActivty;


    public RegisterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  =inflater.inflate(R.layout.fragment_register, container, false);

        email = view.findViewById(R.id.emailRegisterTextId);
        password = view.findViewById(R.id.passwordRegisterTextId);
        confirmPassword = view.findViewById(R.id.confirmPasswordRegisterTextId);
        register = view.findViewById(R.id.registerButtonId);
        iStartActivty = (IStartActivty) getActivity();
        toLogin = view.findViewById(R.id.loginScreenButtonId);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();

                if(! emailString.isEmpty()){
                    String passwordString = password.getText().toString();
                    if(! passwordString.isEmpty()){
                        if(passwordString.equals(confirmPassword.getText().toString())){
                            iStartActivty.register(emailString,passwordString);

                        }
                    }
                }
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStartActivty.inflateFragment("Login");
            }
        });

        return view;
    }

}
