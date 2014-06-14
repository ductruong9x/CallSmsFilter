package com.truongtvd.callsmsfilter;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.truongtvd.callsmsfilter.adapter.ViewPagerHomeAdapter;
import com.truongtvd.callsmsfilter.database.DataCallFilterHelper;
import com.truongtvd.callsmsfilter.database.DataLogHelper;
import com.viewpagerindicator.PagerSlidingTabStrip;


public class MainActivity extends FragmentActivity {

    private ActionBar actionBar;
    private ViewPagerHomeAdapter adapter;
    private ViewPager vpMain;
    private PagerSlidingTabStrip indicator;
    private final static int REQUEST_CODE_FILTER=5;
    private AdView adView;
    private DataCallFilterHelper dataCallFilterHelper;
    private DataLogHelper dataLogHelper;
    private InterstitialAd interstitialAd;
    private String UNIT_ID="ca-app-pub-6063844612770322/5295140093";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView=(AdView)findViewById(R.id.ad);
        adView.loadAd(new AdRequest.Builder().build());
        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(UNIT_ID);
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            //    interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        actionBar = getActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff' size='25'>"
                + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#33b5e5")));
        dataCallFilterHelper=new DataCallFilterHelper(MainActivity.this);
        dataCallFilterHelper.getWritableDatabase();
        dataLogHelper=new DataLogHelper(MainActivity.this);
        dataLogHelper.getWritableDatabase();
        vpMain = (ViewPager) findViewById(R.id.vpMain);
        indicator = (PagerSlidingTabStrip) findViewById(R.id.indicatorTabHome);
        indicator.setIndicatorColor(Color.parseColor("#33b5e5"));
        indicator.setTextColor(Color.BLACK);
        adapter = new ViewPagerHomeAdapter(MainActivity.this, getSupportFragmentManager());
        vpMain.setAdapter(adapter);
        vpMain.setOffscreenPageLimit(2);
        vpMain.setCurrentItem(0);
        indicator.setAllCaps(false);
        indicator.setIndicatorHeight(6);
        indicator.setViewPager(vpMain);
        danhGia();
    }

    public void danhGia() {
        SharedPreferences getPre = getSharedPreferences("SAVE", MODE_PRIVATE);
        int i = getPre.getInt("VOTE", 0);
        SharedPreferences pre;
        SharedPreferences.Editor edit;
        switch (i) {
            case 0:
                pre = getSharedPreferences("SAVE", MODE_PRIVATE);
                edit = pre.edit();
                edit.putInt("VOTE", 1);
                edit.commit();
                break;
            case 1:
                pre = getSharedPreferences("SAVE", MODE_PRIVATE);
                edit = pre.edit();
                edit.putInt("VOTE", i + 1);
                edit.commit();
                break;
            case 2:
                pre = getSharedPreferences("SAVE", MODE_PRIVATE);
                edit = pre.edit();
                edit.putInt("VOTE", i + 1);
                edit.commit();
                break;
            case 3:
                pre = getSharedPreferences("SAVE", MODE_PRIVATE);
                edit = pre.edit();
                edit.putInt("VOTE", i + 1);
                edit.commit();
                break;
            case 4:
                pre = getSharedPreferences("SAVE", MODE_PRIVATE);
                edit = pre.edit();
                edit.putInt("VOTE", i + 1);
                edit.commit();
                break;
            case 5:
                DialogVote dialog = new DialogVote(MainActivity.this);
                dialog.show();
                pre = getSharedPreferences("SAVE", MODE_PRIVATE);
                edit = pre.edit();
                edit.putInt("VOTE", 5);
                edit.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        interstitialAd.show();
        super.onBackPressed();
    }
}
