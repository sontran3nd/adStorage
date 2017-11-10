package com.example.admin.automgs.Fragments.Dialogs;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Admin on 10/13/2017.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private int layoutID = 0;

    protected abstract int setUpLayout();

    private int getLayoutID() {
        return this.layoutID;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.layoutID = setUpLayout();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutID(), container, false);

        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager manager = getActivity().getWindowManager();

        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(metrics);
            setUpWindowsSize(rootView, metrics);
//            int width = (int) (metrics.widthPixels * 0.85);
////            int height = metrics.heightPixels;
//            rootView.setMinimumWidth(width);
////            rootView.setMinimumHeight(height);
        }

        setupViews(rootView);
        return rootView;
    }

    protected abstract void setUpWindowsSize(View rootView, DisplayMetrics metrics);

    protected abstract void setupViews(View view);
}
