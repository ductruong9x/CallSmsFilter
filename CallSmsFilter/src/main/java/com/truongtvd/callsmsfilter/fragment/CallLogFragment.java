package com.truongtvd.callsmsfilter.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.truongtvd.callsmsfilter.DialogAdd;
import com.truongtvd.callsmsfilter.R;
import com.truongtvd.callsmsfilter.adapter.CallAdapter;
import com.truongtvd.callsmsfilter.database.DataCallFilterHelper;
import com.truongtvd.callsmsfilter.model.CallFilter;

import java.util.ArrayList;

/**
 * Created by truongtvd on 6/10/14.
 */
public class CallLogFragment extends Fragment {

    private View mParent;
    private final static int REQUEST_CODE_FILTER = 5;
    private DataCallFilterHelper dataCallFilterHelper;
    private ListView lvList;
    private ProgressBar loading;
    private CallAdapter adapter;
    private ArrayList<CallFilter> listfilter = new ArrayList<CallFilter>();
    private boolean isLoad = false;
    private ImageButton plus_button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataCallFilterHelper = new DataCallFilterHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParent = inflater.inflate(R.layout.fragment_layout_call, null);
        lvList = (ListView) mParent.findViewById(R.id.list_item);
        loading = (ProgressBar) mParent.findViewById(R.id.load);
        plus_button=(ImageButton)mParent.findViewById(R.id.button_add);
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogAdd.class);
                startActivityForResult(intent, REQUEST_CODE_FILTER);
            }
        });
        setHasOptionsMenu(true);

        getFilter();
        return mParent;
    }

    public void init() {
        if (isLoad) {
            return;
        }

        isLoad = true;


    }

    private void getFilter() {
        loading.setVisibility(View.GONE);
        listfilter = dataCallFilterHelper.getListFilter();
        adapter = new CallAdapter(getActivity(), R.layout.item_layout, listfilter);
        lvList.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.new_filter:
//                Intent intent = new Intent(getActivity(), DialogAdd.class);
//                startActivityForResult(intent, REQUEST_CODE_FILTER);
//                break;
            case R.id.rate:
                Intent goMoreApp = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri
                                .parse("https://play.google.com/store/apps/developer?id=App+Entertainment"));
                startActivity(goMoreApp);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getData() {
      //  loading.setVisibility(View.GONE);
        listfilter.clear();
        listfilter = dataCallFilterHelper.getListFilter();
        adapter = new CallAdapter(getActivity(), R.layout.item_layout, listfilter);
        lvList.setAdapter(adapter);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Log.e("Asdasd", "asdasdas");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILTER) {
            if (resultCode == REQUEST_CODE_FILTER) {
                int type = data.getIntExtra("TYPE", 0);

                if (type == 1) {
                    //add to call filter
                    String name = data.getStringExtra("NAME");
                    String phone = data.getStringExtra("PHONE");
                    String title=data.getStringExtra("TITLE");
                    String content=data.getStringExtra("CONTENT");
                    int show = data.getIntExtra("SHOW",3);
                    dataCallFilterHelper.addFilterCall(phone, name,title,content,show);
                    getData();

                }

            }
        }
    }



}
