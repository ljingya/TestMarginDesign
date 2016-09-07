package com.example.shuiai.testmargindesign.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * @author shuiai@dianjia.io
 * @Company 杭州木瓜科技有限公司
 * @date 2016/9/7
 */
public class DefineRecyView extends RecyclerView {
    public DefineRecyView(Context context) {
        super(context);
    }

    public DefineRecyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DefineRecyView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private boolean isLoadingMore = false;//是否正在加载更多
    private boolean loadMoreEnable = false;//是否允许加载更多
    private int footerResource = -1;//脚布局
    private boolean footer_visible = false;//脚部是否可以见
    private OnLoadMoreListener loadMoreListener;

    private void init() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getAdapter() != null && getLayoutManager() != null) {
                    int lastVisivablePosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    int itemCount = getAdapter().getItemCount();
                    if (distancey < 0 && loadMoreEnable && itemCount != 0 && !isLoadingMore && lastVisivablePosition == itemCount - 1) {
                        isLoading();
                        if (footerResource != -1) {
                            footer_visible = true;
                            getAdapter().notifyItemChanged(itemCount - 1);
                        }
                        if (loadMoreListener != null) {
                            loadMoreListener.loadMoreListener();
                        }
                    }

                }
            }
        });
    }

    private float startY = 0;
    private float distancey = 0;

    /**
     * 判断滑动的方向
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                distancey = y - startY;
                startY = y;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置是否允许加载更多
     */
    public void setLoadMoreEnable(boolean isEnable) {
        this.loadMoreEnable = isEnable;
    }

    /**
     * 设置脚布局
     */
    public void setFootResource(int footerResource) {
        this.footerResource = footerResource;
    }

    /**
     * 加载完成
     */
    public void loadMoreComplete() {
        this.isLoadingMore = false;
    }

    public void isLoading() {
        this.isLoadingMore = true;
    }

    /**
     * 加载更多数据回调
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void loadMoreListener();//上拉刷新
    }

    /**
     * 刷新数据
     */
    public void notifyData() {
        if (getAdapter() != null) {
            loadMoreComplete();
            if (footerResource != -1 && loadMoreEnable) {
                footer_visible = false;
            }
            getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        SlideInBottomAnimationAdapter slide = new SlideInBottomAnimationAdapter(adapter);
        slide.setDuration(500);
        AutoLoadAdapter auto = new AutoLoadAdapter(slide);
        super.setAdapter(auto);
    }

    private class AutoLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Adapter adpter;
        private final int TYPE_FOOTER = Integer.MAX_VALUE;//底部布局

        public AutoLoadAdapter(RecyclerView.Adapter adpter) {
            this.adpter = adpter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1 && loadMoreEnable && footerResource != -1 && footer_visible) {
                return TYPE_FOOTER;
            }
            if (adpter.getItemViewType(position) == TYPE_FOOTER) {
                throw new RuntimeException("adapter中itemType不能为:" + Integer.MAX_VALUE);
            }
            return adpter.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHorder = null;
            if (viewType == TYPE_FOOTER) {
                viewHorder = new FooterViewHolder(LayoutInflater.from(getContext()).inflate(footerResource, parent, false));
            } else {
                viewHorder = adpter.onCreateViewHolder(parent, viewType);
            }

            return viewHorder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int itemViewType = getItemViewType(position);
            if (itemViewType != TYPE_FOOTER) {
                adpter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            if (adpter.getItemCount() != 0) {
                int count = adpter.getItemCount();
                if (loadMoreEnable && footer_visible && footerResource != -1) {
                    count++;
                }
                return count;
            }
            return 0;
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);

            }
        }
    }
}
