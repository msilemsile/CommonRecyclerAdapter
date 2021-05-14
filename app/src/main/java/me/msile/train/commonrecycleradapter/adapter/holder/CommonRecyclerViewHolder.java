package me.msile.train.commonrecycleradapter.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.msile.train.commonrecycleradapter.adapter.CommonRecyclerAdapter;

/**
 * @param <T> 数据模型
 */
public abstract class CommonRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected View mItemView;
    protected T mData;
    protected CommonRecyclerAdapter mDataAdapter;

    public CommonRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        initViews(itemView);
    }

    public void setDataAdapter(CommonRecyclerAdapter adapter) {
        this.mDataAdapter = adapter;
    }

    public void setData(T mData) {
        this.mData = mData;
        initData(mData);
    }

    public RecyclerView.LayoutParams getItemLayoutParams() {
        return null;
    }

    public abstract void initViews(View itemView);

    public abstract void initData(T data);

    public interface Factory<T> {
        CommonRecyclerViewHolder<T> createViewHolder(View itemView);
    }

}
