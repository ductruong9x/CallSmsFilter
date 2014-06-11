package com.truongtvd.callsmsfilter.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.truongtvd.callsmsfilter.R;
import com.truongtvd.callsmsfilter.fragment.CallLogFragment;
import com.truongtvd.callsmsfilter.fragment.LogFragment;
import com.truongtvd.callsmsfilter.fragment.SmsFilterFragment;


public class ViewPagerHomeAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] CONTENT;


    public ViewPagerHomeAdapter(Context context, FragmentManager fm) {

        super(fm);
        this.context = context;
        CONTENT = new String[]{context.getString(R.string.sms),
                context.getString(R.string.calllog), context.getString(R.string.log)};
    }

    Fragment fragment;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        super.setPrimaryItem(container, position, object);
        if (position == 0) {
            SmsFilterFragment smsFilterFragment = (SmsFilterFragment) object;
            smsFilterFragment.init();
        } else if (position == 1) {
            CallLogFragment callLogFragment = (CallLogFragment) object;
            callLogFragment.init();
        }else if (position == 1) {
            LogFragment logFragment = (LogFragment) object;
            logFragment.init();
        }

    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                fragment = new SmsFilterFragment();

                break;
            case 1:
                fragment = new CallLogFragment();
                break;
            case 2:
                fragment = new LogFragment();
                break;

            default:
                break;
        }
        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position];
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

}