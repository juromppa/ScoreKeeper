package com.gmail.romppainen.matti.juha.scorekeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Juha on 28.12.2016.
 */

public class MainActivityTabFragment1 extends Fragment {

    public MainActivityTabFragment1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.main_activity_tab_fragment_1, container, false);
    }
}