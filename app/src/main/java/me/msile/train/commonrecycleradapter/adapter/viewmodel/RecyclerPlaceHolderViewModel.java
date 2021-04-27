package me.msile.train.commonrecycleradapter.adapter.viewmodel;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.msile.train.commonrecycleradapter.adapter.model.RecyclerPlaceHolderInfoBean;

/**
 * recyclerView itemView 占位布局(当布局不需要数据模型时)
 *
 * @param <T> 数据模型
 */
public class RecyclerPlaceHolderViewModel extends RecyclerItemViewModel<RecyclerPlaceHolderInfoBean> {

    public RecyclerPlaceHolderViewModel(@NonNull Context context) {
        super(context);
    }

    public RecyclerPlaceHolderViewModel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerPlaceHolderViewModel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setDataInner(RecyclerPlaceHolderInfoBean data) {

    }


}
