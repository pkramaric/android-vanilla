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
import android.widget.Toast;

import com.flybits.android.kernel.KernelScope;
import com.flybits.android.push.PushScope;
import com.flybits.android.samples.vanilla.fragments.ContentFeedFragment;
import com.flybits.android.samples.vanilla.interfaces.IProgressDialog;
import com.flybits.commons.library.api.FlybitsManager;
import com.flybits.commons.library.api.results.callbacks.BasicResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IProgressDialog{

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog  = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
