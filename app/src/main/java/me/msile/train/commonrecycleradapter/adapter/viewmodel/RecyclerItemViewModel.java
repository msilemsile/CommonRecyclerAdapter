package me.msile.train.commonrecycleradapter.adapter.viewmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.msile.train.commonrecycleradapter.adapter.CommonRecyclerAdapter;

/**
 * recyclerView itemView 根布局
 * @param <T> 数据模型
 */
public abstract class RecyclerItemViewModel<T> extends FrameLayoutViewModel<T> {

    protected CommonRecyclerAdapter mDataAdapter;

    public RecyclerItemViewModel(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerItemViewModel(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerItemViewModel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void inflateItemLayout(@LayoutRes int layId) {
        inflate(getContext(), layId, this);
        findViews();
    }

    public void setDataAdapter(CommonRecyclerAdapter adapter) {
        this.mDataAdapter = adapter;
    }

    @Override
    public void setData(T mData) {
        super.setData(mData);
        setDataInner(mData);
    }

    public View getView() {
        return this;
    }

    protected abstract void findViews();

    protected abstract void setDataInner(T data);

}
