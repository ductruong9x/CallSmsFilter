package com.truongtvd.callsmsfilter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truongtvd.callsmsfilter.R;

/**
 * Created by truongtvd on 6/10/14.
 */
public class LogFragment extends Fragment {

    private View mParent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParent=inflater.inflate(R.layout.fragment_layout,null);


        return mParent;
    }

    public void init(){

    }
}
