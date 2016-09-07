package com.example.shuiai.testmargindesign;

import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shuiai.testmargindesign.adapter.MyAdapter;
import com.example.shuiai.testmargindesign.view.DefineRecyView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DefineRecyView.OnLoadMoreListener {
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private DefineRecyView rv;
    private List<Integer> dataList = new ArrayList<Integer>();
    private int[] imgs = {
            R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h,
    };
//    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationview = (NavigationView) findViewById(R.id.navigationView);
        rv = (DefineRecyView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefsh);
        rv.setLoadMoreEnable(true);
        rv.setFootResource(R.layout.foot);
        setSupportActionBar(toolbar);
        navigationview.setNavigationItemSelectedListener(this);
        MyAdapter myadapter = new MyAdapter(this, dataList);
        rv.setOnLoadMoreListener(this);
//        swipe.setRefreshing(true);
//        swipe.setOnRefreshListener(this);
        initdata();
        init();
        rv.setAdapter(myadapter);
    }

    private void initdata() {
        for (int i = 0; i < 6; i++) {
            dataList.add(imgs[i]);
        }
        rv.notifyData();
    }

    private void init() {
        ActionBarDrawerToggle actionBar = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open_layout, R.string.close_layout) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerlayout.setDrawerListener(actionBar);
        actionBar.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                Toast.makeText(this, "鸣人", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:
                Toast.makeText(this, "雏田", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option3:
                Toast.makeText(this, "自来也", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.luoxuanwan:
                Toast.makeText(this, "螺旋丸", Toast.LENGTH_LONG).show();
                break;
            case R.id.shoulijian:
                Toast.makeText(this, "手里剑", Toast.LENGTH_LONG).show();
                break;
            case R.id.yingfenshen:
                Toast.makeText(this, "影分身", Toast.LENGTH_LONG).show();
                break;
            case R.id.modle:
                Toast.makeText(this, "仙人模式", Toast.LENGTH_LONG).show();
                break;
            case R.id.tongling:
                Toast.makeText(this, "通灵", Toast.LENGTH_LONG).show();
                break;


        }
        return true;
    }

    @Override
    public void loadMoreListener() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    dataList.add(imgs[i]);
                }
                rv.notifyData();//刷新数据
            }
        }, 2000);
    }

    private Handler handler = new Handler();

//    @Override
//    public void onRefresh() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipe.setRefreshing(false);
//                dataList.add(0, "最新数据");
//                rv.notifyData();//刷新数据
//            }
//        }, 2000);
//    }
}
