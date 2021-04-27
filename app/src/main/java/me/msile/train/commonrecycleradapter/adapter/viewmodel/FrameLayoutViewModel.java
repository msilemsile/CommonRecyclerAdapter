package me.msile.train.commonrecycleradapter.adapter.viewmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @param <T> 数据模型
 */
public class FrameLayoutViewModel<T> extends FrameLayout implements IViewModel<T> {

    protected T mData;

    public FrameLayoutViewModel(@NonNull Context context) {
        this(context, null);
    }

    public FrameLayoutViewModel(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameLayoutViewModel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(T mData) {
        this.mData = mData;
    }

}
