package com.kingwag.tinyworld.view.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingwag.tinyworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingNoneFragment extends Fragment {


    public ShoppingNoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View shopView = inflater.inflate(R.layout.shopping_has_login, container, false);
        return shopView;
    }

}
