package com.truongtvd.callsmsfilter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.truongtvd.callsmsfilter.adapter.ViewPagerHomeAdapter;
import com.truongtvd.callsmsfilter.database.DataCallFilterHelper;
import com.truongtvd.callsmsfilter.database.DataSmsFilterHelper;
import com.viewpagerindicator.PagerSlidingTabStrip;


public class MainActivity extends SherlockFragmentActivity {

    private ActionBar actionBar;
    private ViewPagerHomeAdapter adapter;
    private ViewPager vpMain;
    private PagerSlidingTabStrip indicator;
    private final static int REQUEST_CODE_FILTER=5;

    private DataCallFilterHelper dataCallFilterHelper;
    private DataSmsFilterHelper dataSmsFilterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff' size='25'>"
                + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#2357bc")));
        dataCallFilterHelper=new DataCallFilterHelper(MainActivity.this);
        dataCallFilterHelper.getWritableDatabase();
        dataSmsFilterHelper=new DataSmsFilterHelper(MainActivity.this);
        dataSmsFilterHelper.getWritableDatabase();
        vpMain = (ViewPager) findViewById(R.id.vpMain);
        indicator = (PagerSlidingTabStrip) findViewById(R.id.indicatorTabHome);
        indicator.setIndicatorColor(Color.parseColor("#2357bc"));
        indicator.setTextColor(Color.BLACK);
        adapter = new ViewPagerHomeAdapter(MainActivity.this, getSupportFragmentManager());
        vpMain.setAdapter(adapter);
        vpMain.setCurrentItem(0);
        vpMain.setOffscreenPageLimit(3);
        indicator.setAllCaps(false);
        indicator.setIndicatorHeight(6);
        indicator.setViewPager(vpMain);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_filter:
                Intent intent=new Intent(MainActivity.this,DialogAdd.class);
                startActivityForResult(intent,REQUEST_CODE_FILTER);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_FILTER){

            if(resultCode==REQUEST_CODE_FILTER){
                Log.e("asdasdasdasd",data.getIntExtra("TEST",0)+"");

            }
        }

    }
}
