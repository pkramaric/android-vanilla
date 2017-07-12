package com.flybits.android.samples.vanilla;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.flybits.android.samples.vanilla.fragments.CreateAccountFragment;
import com.flybits.android.samples.vanilla.fragments.LoginFragment;
import com.flybits.commons.library.api.FlybitsManager;
import com.flybits.commons.library.api.idps.FlybitsIDP;
import com.flybits.commons.library.api.results.callbacks.ConnectionResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;

public class LoginActivity extends AppCompatActivity implements LoginFragment.ILoginOptions, CreateAccountFragment.ICreateAccount{

    private ProgressDialog progressDialog;
    private RelativeLayout layoutSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FlybitsManager.setDebug();

        layoutSplash    = (RelativeLayout) findViewById(R.id.layoutSplash);
        progressDialog = new ProgressDialog(this);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            setProgressBar(getString(R.string.loadingIsConnected), true);
            FlybitsManager.isConnected(LoginActivity.this, true, connectionCallback);
        }
    }

    @Override
    public void onLogin(String email, String password) {
        setProgressBar(getString(R.string.loggingIn), true);
        FlybitsIDP  idp = new FlybitsIDP(email, password);
        connectToFlybits(idp);
    }

    @Override
    public void onCreate(String firstName, String lastName, String email, String password) {
        setProgressBar(getString(R.string.registeringUser), true);
        FlybitsIDP  idp = new FlybitsIDP(firstName, lastName, email, password);
        connectToFlybits(idp);
    }

    @Override
    public void onRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CreateAccountFragment.newInstance()).addToBackStack("Login").commit();
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

    ConnectionResultCallback connectionCallback = new ConnectionResultCallback() {
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
            layoutSplash.setVisibility(View.GONE);
        }

        @Override
        public void onException(FlybitsException exception) {
            layoutSplash.setVisibility(View.GONE);
        }
    };

    private void connectToFlybits(FlybitsIDP idp) {
        FlybitsManager manager  = new FlybitsManager.Builder(LoginActivity.this)
                .setAccount(idp)
                .build();
        manager.connect(connectionCallback);
    }
}
