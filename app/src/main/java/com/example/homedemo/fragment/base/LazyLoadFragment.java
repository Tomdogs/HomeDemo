package com.example.homedemo.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * 实现fragment懒加载：
 * viewPager+Fragment组合使用时，默认是加载当前显示的fragment和当前fragment左右两个fragment
 * 如果希望在展示相应 Fragment 页面时再动态加载当前页面数据，可实现fragment懒加载
 */
public abstract class LazyLoadFragment extends Fragment {

    private static final String TAG = "LazyLoadFragment";

    protected boolean isViewInitiated;
    protected boolean isDataLoaded;//避免重复加载

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ===================");
        isViewInitiated = true;
        prepareRequestData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: ------------------");
        super.setUserVisibleHint(isVisibleToUser);
        prepareRequestData();
    }

    public abstract void requestData();

    public boolean prepareRequestData() {
        return prepareRequestData(false);
    }

    /**
     *
     * @param forceUpdate 强制刷新
     * @return
     */
    public boolean prepareRequestData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            Log.d(TAG, "请求数据: ---------------------");
            requestData();
            isDataLoaded = true;
            return true;
        }
        return false;
    }
}
