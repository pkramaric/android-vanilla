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

public class CreateAccountFragment extends Fragment {

    public interface ICreateAccount{
        public void onCreate(String firstName, String lastName, String email, String password);
    }

    private ICreateAccount callbackOptions;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtFirstName;
    private EditText edtLastName;

    public static CreateAccountFragment newInstance(){

        Bundle bundle           = new Bundle();
        CreateAccountFragment fragment  = new CreateAccountFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_create_account, container, false);

        edtEmail        = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword     = (EditText) view.findViewById(R.id.edtPassword);
        edtFirstName    = (EditText) view.findViewById(R.id.edtFirstName);
        edtLastName     = (EditText) view.findViewById(R.id.edtLastName);

        Button btnCreate     = (Button) view.findViewById(R.id.btnRegister);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFirstName.getText().length() > 0 && edtLastName.getText().length() > 0 && edtEmail.getText().length() > 0 && edtPassword.getText().length() > 0) {
                    callbackOptions.onCreate(edtFirstName.getText().toString(), edtLastName.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString());
                }else{
                    Toast.makeText(getActivity(), R.string.errorMissingFields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callbackOptions = (ICreateAccount) context;
    }
}