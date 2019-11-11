package com.example.homedemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homedemo.R;

import java.util.List;

public class TabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> datas;

    private int TYPE_ITEM = 0;//正常的Item
    private int TYPE_FOOT = 1;//尾部刷新
    private int TYPE_EMPTY = 2;//没有数据，显示空布局类型
    private int mEmptyType = 0;// 控制空布局的显隐
    /**
     * footer显示的四种状态
     * 第一次加载数据时，将footer隐藏，避免rv的item不满一页时，footer一直显示
     */
    public static final int STATE_FIRST = 1;//第一次显示，隐藏footer
    public static final int STATE_SHOW = 2;//第一次之后的正常显示，显示加载中
    public static final int STATE_ERROR_NET = 3;//网络错误时显示，可以点击重新加载
    public static final int STATE_All_LOADED = 4;//全部数据已加载完毕

    private View footView;//footview布局
    private int loadState = STATE_FIRST;//记录rv的foot状态
    private boolean isLoading = false;//避免多次上滑加载多次

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public int getLoadState() {
        return loadState;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    //    private boolean hasMore = true;
    private OnItemClickListener onItemClickListener;

    public TabAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT;
        }
        Log.i("adapter","getItemViewType mEmptyType："+mEmptyType);
       /* if(datas.size()==0){
            Log.i("adapter","getItemViewType 执行了:"+mEmptyType);
            return TYPE_EMPTY;
        }*/
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.i("adapter","viewType:"+viewType);
        if (viewType == TYPE_ITEM) {
            return new TabHolder(LayoutInflater.from(context).inflate(R.layout.tab_rcy_item, viewGroup, false));
        }/*else if(viewType == TYPE_EMPTY){
            return new EmptyHolder(LayoutInflater.from(context).inflate(R.layout.layout_empty_view, viewGroup, false));
        }*/
        footView = LayoutInflater.from(context).inflate(R.layout.tab_rcy_foot, viewGroup, false);
        return new FootHolder(footView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int positon) {
        if (viewHolder instanceof TabHolder) {

            ((TabHolder) viewHolder).title.setText(datas.get(positon));
            ((TabHolder) viewHolder).time.setText("2019年10月14日");
            ((TabHolder) viewHolder).desc.setText("具体内容的描述");
            if(true){
                ((TabHolder) viewHolder).ivNext.setVisibility(View.GONE);
            }else {
                ((TabHolder) viewHolder).handleView.setVisibility(View.GONE);
            }


        } else if(viewHolder instanceof FootHolder) {
            ((FootHolder) viewHolder).setAllGone();
            footView.setVisibility(View.GONE);
            switch (loadState) {
                case STATE_FIRST:
                case STATE_SHOW:
                    ((FootHolder) viewHolder).layout_loading.setVisibility(View.VISIBLE);
                    break;
                case STATE_ERROR_NET:
                    footView.setVisibility(View.VISIBLE);
                    ((FootHolder) viewHolder).layout_errornet.setVisibility(View.VISIBLE);
                    break;
                case STATE_All_LOADED:
                    footView.setVisibility(View.VISIBLE);
                    ((FootHolder) viewHolder).layout_nomore.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        if(viewHolder instanceof EmptyHolder){

            Log.i("adapter","EmptyHolder");
        }

        if (onItemClickListener != null && viewHolder instanceof TabHolder) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (false) {//已经处理，显示历史信息
                        onItemClickListener.onHistoryItemClick(view, positon);
                    } else {//未处理
                        onItemClickListener.onItemClick(view, positon);
                    }
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(view, positon);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {

//        return datas != null ? datas.size() + 1 : 0;
        if(datas.size()==0){
            return 0;
        }
        return datas.size() + 1;
    }

    public void showFooter() {
        if (footView != null) {
            footView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 列表子项
     */
    class TabHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView time;
        public ImageView imageView;
        public TextView handleView;//处置按钮
        ImageView ivLine;//左侧短线
        ImageView ivNext;//下一个按钮
        TextView desc;//描述

        public TabHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tab_fragment_tv_title);
            time = itemView.findViewById(R.id.tab_fragment_tv_time);
            imageView = itemView.findViewById(R.id.tab_fragment_iv);
            handleView = itemView.findViewById(R.id.tab_fragment_btn_process);
            ivLine = itemView.findViewById(R.id.tab_fragment_iv_line);
            ivNext = itemView.findViewById(R.id.tab_fragment_btn_next);
            desc=itemView.findViewById(R.id.tab_fragment_tv_desc);
        }
    }


    /**
     * 底部显示内容
     */
    class FootHolder extends RecyclerView.ViewHolder {

        View layout_loading;
        View layout_nomore;
        View layout_errornet;

        public FootHolder(@NonNull View itemView) {
            super(itemView);
            layout_loading = itemView.findViewById(R.id.tab_fragment_layout_loading);
            layout_nomore = itemView.findViewById(R.id.tab_fragment_layout_nomore);
            layout_errornet = itemView.findViewById(R.id.tab_fragment_layout_errornet);
        }

        public void setAllGone() {
            if (layout_loading != null) {
                layout_loading.setVisibility(View.GONE);
            }
            if (layout_nomore != null) {
                layout_nomore.setVisibility(View.GONE);
            }
            if (layout_errornet != null) {
                layout_errornet.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 当数据为空时显示的布局
     */
    class EmptyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        View text;

        public EmptyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.none_data);
            text = itemView.findViewById(R.id.textView);

            Glide.with(context).asGif().load(R.drawable.loading).into(imageView);//动态图
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void onHistoryItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
