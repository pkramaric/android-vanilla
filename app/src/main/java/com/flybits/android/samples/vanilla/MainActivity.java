package com.flybits.android.samples.vanilla;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flybits.android.kernel.KernelScope;
import com.flybits.android.push.PushScope;
import com.flybits.android.samples.vanilla.context.BankingData;
import com.flybits.android.samples.vanilla.fragments.ContentFeedFragment;
import com.flybits.android.samples.vanilla.fragments.CustomContextDialog;
import com.flybits.android.samples.vanilla.interfaces.IProgressDialog;
import com.flybits.commons.library.api.FlybitsManager;
import com.flybits.commons.library.api.results.callbacks.BasicResultCallback;
import com.flybits.commons.library.caching.FlybitsUIObjectObserver;
import com.flybits.commons.library.caching.FlybitsUserObserver;
import com.flybits.commons.library.exceptions.FlybitsException;
import com.flybits.commons.library.models.User;
import com.flybits.context.ContextManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IProgressDialog{

    private TextView headerName;
    private TextView headerEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog  = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View header = navigationView.getHeaderView(0);
        headerName = (TextView) header.findViewById(R.id.txtName);
        headerEmail = (TextView) header.findViewById(R.id.txtEmail);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        FlybitsManager.getUser(MainActivity.this);
        FlybitsUserObserver.observe(MainActivity.this).add(new FlybitsUIObjectObserver.DataChanged<User>() {
            @Override
            public void onUpdate(User data) {
                if (data != null) {
                    headerName.setText(data.getFirstName() + " " + data.getLastName());
                    headerEmail.setText(data.getEmail());
                }else{
                    headerName.setText("");
                    headerEmail.setText("");
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ContentFeedFragment.newInstance()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class fragmentClass = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        switch(id) {
            case R.id.nav_home:
                fragmentClass = ContentFeedFragment.class;
                break;
            case R.id.nav_logout:
                setProgressBar(getString(R.string.loggingOut), false);
                logout();
                break;
            case R.id.nav_send_context_highnet:
                BankingData data    = new BankingData();
                data.segmentation   = "highnet";
                ContextManager.updateContextNow(MainActivity.this, data, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_send_context_pensioner:
                BankingData data2    = new BankingData();
                data2.segmentation   = "pensioner";
                ContextManager.updateContextNow(MainActivity.this, data2, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_send_context_student:
                BankingData data3    = new BankingData();
                data3.segmentation   = "student";
                ContextManager.updateContextNow(MainActivity.this, data3, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_send_context_balance1:
                BankingData data4    = new BankingData();
                data4.accountBalance = 1000;
                ContextManager.updateContextNow(MainActivity.this, data4, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_send_context_balance2:
                BankingData data5    = new BankingData();
                data5.accountBalance   = 10000;
                ContextManager.updateContextNow(MainActivity.this, data5, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_send_context_credit1:
                BankingData data6   = new BankingData();
                data6.creditCard    = "visa";
                ContextManager.updateContextNow(MainActivity.this, data6, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_send_context_credit2:
                BankingData data7   = new BankingData();
                data7.creditCard    = "mastercard";
                ContextManager.updateContextNow(MainActivity.this, data7, "ctx.rgabanking.banking", System.currentTimeMillis() / 1000, null);
                break;
            case R.id.nav_custom_context:
                FragmentManager fm = getSupportFragmentManager();
                CustomContextDialog dialogFragment = new CustomContextDialog ();
                dialogFragment.show(fm, "Custom Context Dialog");
                break;
            default:
                fragmentClass = ContentFeedFragment.class;
        }
        if (fragmentClass != null) {

            try {
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        FlybitsManager manager  = new FlybitsManager.Builder(MainActivity.this)
                .addScope(KernelScope.SCOPE)
                .addScope(new PushScope(MainActivity.this))
                .setDebug()
                .build();
        manager.disconnect(new BasicResultCallback() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                stopProgressBar();
                MainActivity.this.finish();
            }

            @Override
            public void onException(FlybitsException exception) {
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                stopProgressBar();
            }
        });

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

    @Override
    public void onStartProgress(String text, boolean isCancelable) {
        setProgressBar(text, isCancelable);
    }

    @Override
    public void onStopProgress() {
        stopProgressBar();
    }
}
