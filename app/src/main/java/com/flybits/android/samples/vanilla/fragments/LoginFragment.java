package com.flybits.android.samples.vanilla.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flybits.android.samples.vanilla.R;

public class LoginFragment extends Fragment {

    public interface ILoginOptions{
        public void onLogin(String email, String password);

        public void onRegister();
    }

    private ILoginOptions callbackOptions;
    private EditText edtEmail;
    private EditText edtPassword;

    public static LoginFragment newInstance(){

        Bundle bundle           = new Bundle();
        LoginFragment fragment  = new LoginFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_login, container, false);

        edtEmail    = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);

        Button btnLogin     = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().length() > 0 && edtPassword.getText().length() > 0) {
                    callbackOptions.onLogin(edtEmail.getText().toString(), edtPassword.getText().toString());
                }else{
                    Toast.makeText(getActivity(), R.string.errorMissingFields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnCreate     = (Button) view.findViewById(R.id.btnRegister);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackOptions.onRegister();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callbackOptions = (ILoginOptions) context;
    }
}