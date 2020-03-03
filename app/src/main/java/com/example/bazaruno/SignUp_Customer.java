package com.example.bazaruno;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.google.android.material.snackbar.Snackbar;
import com.victor.loading.rotate.RotateLoading;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp_Customer extends Fragment {

    View view;
    private RotateLoading rotating;
    private VolleyService volleyService;

    public SignUp_Customer() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.signup_customer,container,false);

        Button signup=(Button) view.findViewById(R.id.signup);
        final EditText email=(EditText) view.findViewById(R.id.email);
        final EditText name=(EditText) view.findViewById(R.id.name);
        final EditText pass=(EditText) view.findViewById(R.id.pass);
        final EditText again_pass=(EditText) view.findViewById(R.id.again_pass);
        rotating = view.findViewById(R.id.newton_cradle_loading);

        volleyService=new VolleyService(getActivity());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email.getText().toString().trim().length()<5){
                    Toast.makeText(getActivity(), "Enter Correct Email", Toast.LENGTH_SHORT).show();

                }
                else if (name.getText().toString().trim().length()<3){
                    Toast.makeText(getActivity(), "Enter Correct Name", Toast.LENGTH_SHORT).show();

                }

                else if (pass.getText().toString().trim().length()<8){
                    Toast.makeText(getActivity(), "Password Must Be Greater then 8", Toast.LENGTH_SHORT).show();

                }
                else if (!pass.getText().toString().trim().matches(again_pass.getText().toString().trim())){
                    Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();

                }
            else {
                    Users buyerUsers=new Users();
                    buyerUsers.setType("buyer");
                    buyerUsers.setStatus("1");
                    buyerUsers.setEmail(email.getText().toString().trim());
                    buyerUsers.setUsername(name.getText().toString().trim());
                    buyerUsers.setPassword(pass.getText().toString().trim());
                    registerData(buyerUsers);
                }
            }
        });
        return view;
    }

    public void registerData(Users users){
        rotating.start();
        volleyService.RegisterUserBuyer(AppConstant.DomainName + AppConstant.Dir + AppConstant.registerUser
                , users, new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG+" SignUp Success",response);



                        rotating.stop();
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onError(VolleyError error) {
                        Log.d(AppConstant.TAG+" SignUp onError VolleyError",error.getMessage());
                        rotating.stop();
                    }
                });



    }

}
