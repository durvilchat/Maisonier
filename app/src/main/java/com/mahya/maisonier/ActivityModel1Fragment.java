package com.mahya.maisonier;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ActivityModel1Fragment extends Fragment {

    private CoordinatorLayout activityTypelogement;
    private RecyclerView list_item;
    private TextView empty_view;
    private View myfab_shadow;
    private FrameLayout myfab_main;
    private CoordinatorLayout activity_typelogement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_model1, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityTypelogement = (CoordinatorLayout) view.findViewById(R.id.activity_typelogement);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
