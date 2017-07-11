package com.flybits.android.samples.vanilla;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flybits.android.samples.vanilla.fragments.LoginFragment;
import com.flybits.commons.library.api.FlybitsManager;
import com.flybits.commons.library.api.results.callbacks.ConnectionResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;

public class LoginActivity extends AppCompatActivity implements LoginFragment.ILoginOptions{

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressDialog = new ProgressDialog(this);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            setProgressBar(getString(R.string.loadingIsConnected), true);

            FlybitsManager.isConnected(LoginActivity.this, true, new ConnectionResultCallback() {
                @Override
                public void onConnected() {
                    Intent intent   = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    stopProgressBar();
                    LoginActivity.this.finish();
                }

                @Override
                public void notConnected() {
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, LoginFragment.newInstance()).commit();
                    stopProgressBar();
                }

                @Override
                public void onException(FlybitsException exception) {

                }
            });
        }

    }

    @Override
    public void onLogin(String email, String password) {

    }

    @Override
    public void onRegister() {

    }

    private void setProgressBar(String text, boolean isCancelable) {
        progressDialog.setCancelable(isCancelable);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        progressDialog.show();
        progressDialog.setMessage(text);
    }

    private void stopProgressBar() {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {}
    }
}
