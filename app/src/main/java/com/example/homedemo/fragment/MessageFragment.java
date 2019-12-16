package com.example.homedemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homedemo.R;
import com.example.homedemo.fragment.base.TabFragment;
import com.example.homedemo.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MessageFragment";
    private Context context;
    private OnDotListener onDotListener;
    private List<String> titles = new ArrayList<>();//tab title列表
    private List<Fragment> fragments = new ArrayList<>();//viewpager 中fragment列表
    private TabLayout tabLayout;


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        context = getActivity();
        getData();//初始化tab，fragment数据
        initView(view);
        return view;
    }

    private void getData() {
        //init title,fragments
        titles.add("健康");
        fragments.add(TabFragment.newInstance("健康"));
        titles.add("词典");
        fragments.add(TabFragment.newInstance("词典"));
        for (int i = 0; i < 10; i++) {
            titles.add("标题" + i);
            fragments.add(TabFragment.newInstance("标题" + i));
        }
    }

    private void initView(View view) {
        //button
        view.findViewById(R.id.message_fragment_btn1).setOnClickListener(this);
        view.findViewById(R.id.message_fragment_btn2).setOnClickListener(this);
        view.findViewById(R.id.message_fragment_btn3).setOnClickListener(this);
        view.findViewById(R.id.message_fragment_btn4).setOnClickListener(this);

        //sort
        view.findViewById(R.id.message_fragment_btn_sort).setOnClickListener(this);

        //init tablayout
        tabLayout = view.findViewById(R.id.message_fragment_tab_layout);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                addTab(tabLayout, titles.get(i), false);
            } else {
                addTab(tabLayout, titles.get(i), true);
            }
        }

        //init viewpager
        final ViewPager viewPager = view.findViewById(R.id.message_fragment_view_pager);
//        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });

        //viewpager和tablayout联动
//        tabLayout.setupWithViewPager(viewPager);//自定义tablayout布局失效
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    TextView tabTitle = tab.getCustomView().findViewById(R.id.tab_title);
                    tabTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    TextView tabTitle = tab.getCustomView().findViewById(R.id.tab_title);
                    tabTitle.setTextColor(getResources().getColor(R.color.colorGray));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //使用自定义布局，第一次启动选中状态未生效
        //默认选中
        if (tabLayout.getTabAt(0) != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            TextView textView = tab.getCustomView().findViewById(R.id.tab_title);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }


    }

    /**
     * 添加tab
     *
     * @param tabLayout Tablayout
     * @param title     tab标题
     * @param isShowRed 是否显示红点
     */
    private void addTab(TabLayout tabLayout, String title, boolean isShowRed) {
        TabLayout.Tab tab = tabLayout.newTab().setCustomView(R.layout.layout_tab_item);
        //标题
        TextView tabTitle = tab.getCustomView().findViewById(R.id.tab_title);
        tabTitle.setText(title);
        //获取sp值
        float pxValue = getResources().getDimension(R.dimen.sp_15);
        System.out.println(String.format("pxValue:%s",pxValue));
        Log.i("convert",String.format("pxValue:%s",pxValue));
        //将px值转换成sp值
        int spValue = ConvertUtils.px2sp(getActivity(),pxValue);
        tabTitle.setTextSize(spValue);
        if (isShowRed) {
            //红点
            ImageView tabRedDot = tab.getCustomView().findViewById(R.id.tab_reddot);
            tabRedDot.setVisibility(View.VISIBLE);
        }
        tabLayout.addTab(tab);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //主页
            case R.id.message_fragment_btn1:
                onDotListener.showDot(50);
                break;
            case R.id.message_fragment_btn2:
                onDotListener.clearDot();
                break;
            //tab
            case R.id.message_fragment_btn3:
                setTabRedDotVisible(0, View.VISIBLE);
                break;
            case R.id.message_fragment_btn4:
                setTabRedDotVisible(0, View.INVISIBLE);
                break;

//                sort
            case R.id.message_fragment_btn_sort:
                Toast.makeText(context, "分类", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setTabRedDotVisible(int index, int visible) {
        if (visible == View.INVISIBLE || visible == View.VISIBLE) {
            ImageView imageView = tabLayout.getTabAt(index).getCustomView().findViewById(R.id.tab_reddot);
            imageView.setVisibility(visible);
        } else {
            throw new RuntimeException("setTabRedDotVisible(int index, int visible)" +
                    " visible must be View.INVISIBLE or  View.VISIBLE");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDotListener) {
            onDotListener = (OnDotListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDotListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDotListener = null;
    }

    public interface OnDotListener {
        /**
         * 显示红点
         *
         * @param count 红点内容
         */
        void showDot(int count);

        /**
         * 取消显示
         */
        void clearDot();
    }
}
