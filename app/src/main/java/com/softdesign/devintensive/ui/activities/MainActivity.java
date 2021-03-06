package com.softdesign.devintensive.ui.activities;

import com.softdesign.devintensive.R;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";
    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private DataManager mDataManager;
    private int mCurrentEditMode = 0;
    private ImageView mDhImgFoto;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFloatingActionButton;
    private EditText mUserPhone, mUserEmail, mUserVk, mUserGit, mUserAbout;

    private List<EditText> mUserInfoViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        mCallImg = (ImageView) findViewById(R.id.call_img);
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);

        mCallImg.setOnClickListener(this);

        mDataManager = DataManager.getInstance();
        mCallImg = (ImageView)findViewById(R.id.call_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout)findViewById(R.id.navigation_drawer);
        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        if (savedInstanceState == null){
            //активити запускается впервые
        } else {
            //активити уже создана
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if (mCurrentEditMode == 0){
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                }else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }
    private void showSnackbar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }
    private void setupToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer(){
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
    /*Переключаем режим редактирования
    * 1 - режим редактирования
    * 0 - режим просмотра*/
    private void changeEditMode(int mode){
        if (mode == 1){
            mFloatingActionButton.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews){
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        }else {
            mFloatingActionButton.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews){
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
            }
        }

    }

    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i <userData.size(); i++){
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue(){
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (EditText userFieldView : mUserInfoViews){
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }
}
