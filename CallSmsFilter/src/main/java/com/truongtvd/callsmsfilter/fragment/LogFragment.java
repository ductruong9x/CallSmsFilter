package com.truongtvd.callsmsfilter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.truongtvd.callsmsfilter.adapter.LogAdapter;
import com.truongtvd.callsmsfilter.R;
import com.truongtvd.callsmsfilter.database.DataLogHelper;
import com.truongtvd.callsmsfilter.model.LogFilter;

import java.util.ArrayList;

/**
 * Created by truongtvd on 6/10/14.
 */
public class LogFragment extends Fragment {

    private View mParent;
    private LogAdapter adapter;
    private ArrayList<LogFilter> listlog=new ArrayList<LogFilter>();
    private ListView lvLog;
    private ProgressBar loading;
    private DataLogHelper dataLogHelper;
    private boolean isLoad=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataLogHelper=new DataLogHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParent=inflater.inflate(R.layout.fragment_layout,null);
        lvLog=(ListView)mParent.findViewById(R.id.list_item);
        loading=(ProgressBar)mParent.findViewById(R.id.load);
       // getLog();
        return mParent;
    }



    public void init(){
        if(isLoad){
            return;
        }
        getLog();
        isLoad=true;

    }
    private void getLog(){
        loading.setVisibility(View.GONE);
        listlog=dataLogHelper.getListFilter();
        adapter=new LogAdapter(getActivity(),R.layout.item_log_layout,listlog);
        lvLog.setAdapter(adapter);
    }
}

