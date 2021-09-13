package com.example.homedemo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.homedemo.fragment.MeFragment;
import com.example.homedemo.fragment.MessageFragment;
import com.example.homedemo.fragment.SceneKotlinFragment;

import java.util.Random;

public class HomeActivity extends AppCompatActivity
        implements BottomNavigationBar.OnTabSelectedListener, MessageFragment.OnDotListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private MessageFragment messageFragment;
//    private SceneFragment sceneFragment;
    private SceneKotlinFragment sceneFragment;
    private MeFragment meFragment;

    private TextBadgeItem textBadgeItem;//红点
    private BottomNavigationBar bottomNavigationBar;//bnb
    private CoordinatorLayout.LayoutParams layoutParams_fl;
    private FrameLayout frameLayout;

    private static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationBar.post(new Runnable() {
            @Override
            public void run() {
                int height = bottomNavigationBar.getMeasuredHeight();
                layoutParams_fl.bottomMargin = height;
                frameLayout.setLayoutParams(layoutParams_fl);
            }
        });
    }

    private void initView() {
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //framlayout
        frameLayout = findViewById(R.id.container);
        layoutParams_fl = (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();

        //textbadgeitem 红点
        textBadgeItem = new TextBadgeItem()
                .setTextColorResource(R.color.colorWhite)
                .setBackgroundColorResource(R.color.colorRed)
                .setBorderWidth(5)
                .hide();

        //bottomnavigationbar
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setActiveColor(R.color.colorPrimary)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(new BottomNavigationItem(R.drawable.ic_message, R.string.message).setBadgeItem(textBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_scene, R.string.scene))
                .addItem(new BottomNavigationItem(R.drawable.ic_me, R.string.me))
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise();

        //
        messageFragment = MessageFragment.newInstance();
        transaction.add(R.id.container, messageFragment).commit();

    }

    @Override
    public void onTabSelected(int i) {
        showFragment(i);
    }

    @Override
    public void onTabUnselected(int i) {

    }

    @Override
    public void onTabReselected(int i) {
        Toast.makeText(this, "重新点击" + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_btn_add:
                //TODO 处理上报
                Toast.makeText(this, "上报", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void showFragment(int index) {
        transaction = fragmentManager.beginTransaction();
        hideFragments();
        switch (index) {
            case 0:
                if (null == messageFragment) {
                    messageFragment = MessageFragment.newInstance();
                    transaction.add(R.id.container, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 1:
                if (null == sceneFragment) {
//                    sceneFragment = SceneFragment.newInstance();
                    sceneFragment = SceneKotlinFragment.newInstance("","");
                    transaction.add(R.id.container, sceneFragment);
                } else {
                    transaction.show(sceneFragment);
                }
                break;
            case 2:
                if (null == meFragment) {
                    meFragment = MeFragment.newInstance();
                    transaction.add(R.id.container, meFragment);
                } else {
                    transaction.show(meFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments() {
        if (null != transaction) {
            if (null != messageFragment) {
                transaction.hide(messageFragment);
            }
            if (null != sceneFragment) {
                transaction.hide(sceneFragment);
            }
            if (null != meFragment) {
                transaction.hide(meFragment);
            }
        }
    }

    @Override
    public void showDot(int count) {
//        textBadgeItem.setText(count + "").show();
        textBadgeItem.setText(new Random().nextInt(100) + "").show();
    }

    @Override
    public void clearDot() {
        textBadgeItem.hide();
    }
}
