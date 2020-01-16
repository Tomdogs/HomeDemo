package com.example.homedemo.fragment.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.homedemo.R;
import com.example.homedemo.adapter.TabAdapter;
import com.example.homedemo.entity.News;
import com.example.homedemo.entity.Translation;
import com.example.homedemo.net.LoggingInterceptor;
import com.example.homedemo.net.NewsRequest;
import com.example.homedemo.net.TranslationRequest;
import com.example.homedemo.recyclerView.EmptyRecyclerView;
import com.example.homedemo.utils.ILog;
import com.example.homedemo.utils.UnicodeToChinese;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabFragment extends LazyLoadFragment {

    private static final String TAG = "TabFragment";
    private Context context;

    private static final String TAB_TYPE = "tab_type";
    private OnFragmentInteractionListener mListener;
    private String tabType;


    private List<String> datas = new ArrayList<>();//列表数据源
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;
    private TabAdapter tabAdapter;
    private int reTimes = 0;
    private View mEmptyView;
    private ImageView loadingImageView;
    private TextView nodataTV;
    private RequestOptions requestOptions;
    private Retrofit retrofitNews;
    private Retrofit retrofitWord;

    public static TabFragment newInstance(String tabType) {
        TabFragment tabFragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString(TAB_TYPE, tabType);
        tabFragment.setArguments(args);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabType = getArguments().getString(TAB_TYPE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        context = getContext();
        initView(view);
        return view;
    }

    private void initNetNews(){

        OkHttpClient logClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        try {
            retrofitNews = new Retrofit.Builder()
                    .baseUrl("http://c.3g.163.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(logClient)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNetWord(){

        OkHttpClient logClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        try {
            retrofitWord = new Retrofit.Builder()
                    .baseUrl("http://fy.iciba.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(logClient)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //onActivityCreate
    @Override
    public void requestData() {

        for (int i = 0; i < 20; i++) {
            datas.add(i + "");
        }

        Log.i(TAG,"类型："+tabType);
        if(tabType.equals("标题2")){
            datas.clear();
        }

        if(tabType.equals("健康")){
            initNetNews();
            datas.clear();
            getHealthNews();
        }
        if(tabType.equals("词典")){
            initNetWord();
            datas.clear();
            getWord();
        }

        if (tabAdapter != null) {
            tabAdapter.notifyDataSetChanged();
        }
    }

    private void getWord(){
        final TranslationRequest request = retrofitWord.create(TranslationRequest.class);
        final Call<Translation> call = request.getWord();
        //异步方式获取数据
        /*call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if(response.isSuccessful()){
                    String result = UnicodeToChinese.UnicodeToCN(response.body().getContent().getOut());
                    datas.add(result);
                    ILog.i("getWord：",result);
                    ILog.i("getWord：当前线程：",Thread.currentThread().getName());
                    tabAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });*/

        //同步方式获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    result = UnicodeToChinese.UnicodeToCN(call.execute().body().getContent().getOut());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ILog.i("getWord：",result);
            }
        }).start();



    }

    private void getHealthNews(){
        final NewsRequest request = retrofitNews.create(NewsRequest.class);
        Call<News> call = request.getHealthNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()){
                    List<News.T1414389941036Entity> list =  response.body().getT1414389941036();
                    for(int i=0;i<list.size();i++){
                        String result =list.get(i).getTitle();
                        datas.add(result);
                        ILog.i("getHealthNews",result);
                    }
                    tabAdapter.notifyDataSetChanged();
                }else {
                    ILog.i("getHealthNews,获取数据失败！");
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                ILog.i("getHealthNews,错误",t);
            }
        });
    }


    private void initView(View view) {
//        TextView textView = view.findViewById(R.id.tab_type);
//        textView.setText(tabType);

        requestOptions = new RequestOptions();
        requestOptions.override(500,500);

        loadingImageView = view.findViewById(R.id.none_data);
        nodataTV = view.findViewById(R.id.textView);
        Glide.with(context)
                .asGif()
                .load(R.drawable.loading)
                .apply(requestOptions)
                .into(loadingImageView);//动态图

        mEmptyView = view.findViewById(R.id.id_loading_view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(datas.size()==0){
                    Glide.with(context)
                            .load(R.mipmap.no_data)
                            .apply(requestOptions)
                            .into(loadingImageView);//静态图
                    nodataTV.setText("暂无数据");
                }
            }
        },5000);

        recyclerView = view.findViewById(R.id.tab_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tabAdapter = new TabAdapter(context, datas);
        recyclerView.setAdapter(tabAdapter);
        Log.i(TAG,"执行了initView");
        recyclerView.setEmptyView(mEmptyView);


        swipeRefreshLayout = view.findViewById(R.id.tab_fragment_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //显示刷新动画效果
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //更新的数据
                        datas.clear();//清空数据
                        for (int i = 0; i < 20; i++) {
                            datas.add(i + "刷新");
                        }
                        //重新设置有更多数据
//                        tabAdapter.setHasMore(true);
                        reTimes = 0;
                        tabAdapter.notifyDataSetChanged();
                        //隐藏刷新效果
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                tabAdapter.showFooter();

                //滑动到底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //recyclerview滑动到底部,更新数据
                    //加载更多数据
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (reTimes < 4) {
                                getMoreData();
                                //告诉他有更多数据
//                                tabAdapter.setHasMore(true);
                                tabAdapter.notifyDataSetChanged();
                            } else {
                                //没有数据了
//                                tabAdapter.setHasMore(false);
                                tabAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 2000);
                } else {//滑上去了
//                    tabAdapter.setHasMore(true);
                }
            }
        });

        tabAdapter.setOnItemClickListener(new TabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"点击了，"+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"长按了，"+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onHistoryItemClick(View view, int position) {
                Toast.makeText(getActivity(),"历史事件，"+position,Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getMoreData() {
        reTimes++;
        for (int i = reTimes * 10; i < (reTimes + 1) * 10; i++) {
            datas.add(i + "");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
