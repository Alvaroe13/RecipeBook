package com.example.foodrecipemvvm;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayoutBase = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayoutBase = constraintLayoutBase.findViewById(R.id.frameLayoutContainer);
        mProgressBar = constraintLayoutBase.findViewById(R.id.progressBar);

        //here me associate the Frame layout as the container whenever an activity extends BaseActivity
        getLayoutInflater().inflate(layoutResID, frameLayoutBase, true);
        super.setContentView(layoutResID);
    }

    /* @Override
    public void setContentView(int layoutInflate) {

        ConstraintLayout constraintLayoutBase = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayoutBase = constraintLayoutBase.findViewById(R.id.frameLayoutContainer);
        mProgressBar = constraintLayoutBase.findViewById(R.id.progressBar);

        //here me associate the Frame layout as the container whenever an activity extends BaseActivity
        getLayoutInflater().inflate(layoutInflate, frameLayoutBase, true);

        super.setContentView(layoutInflate);
    }*/

    /**
     * method in charge of showing or not the progressbar
     * @param visible
     */
    public void showProgressBar( boolean visible){
        mProgressBar.setVisibility( visible ? View.VISIBLE : View.INVISIBLE);
        //Quick note:  "visible" is the condition to study,
        // "?" is a conditional that means "if true equals to..."
        // ":" is a conditional that means "if false equals to..."

    }




}
