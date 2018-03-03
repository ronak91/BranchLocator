package com.branchlocator.branchlocator.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.branchlocator.branchlocator.Activity.MainDrawerActivity;
import com.branchlocator.branchlocator.R;

/**
 * Created by PRINCE on 17/02/2018.
 */

public class MapFragment extends Fragment
{
    View rootView;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_branchlist, container, false);

        MainDrawerActivity mainActivity = (MainDrawerActivity) getActivity();

        return  rootView;
    }
}
