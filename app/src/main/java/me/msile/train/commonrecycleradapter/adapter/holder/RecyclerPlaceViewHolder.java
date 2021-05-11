package me.msile.train.commonrecycleradapter.adapter.holder;

import android.view.View;

import me.msile.train.commonrecycleradapter.adapter.model.RecyclerPlaceHolderInfoBean;

/**
 * recyclerView itemView 占位布局(当布局不需要数据模型时)
 *
 * @param <T> 数据模型
 */
public class RecyclerPlaceViewHolder extends CommonRecyclerViewHolder<RecyclerPlaceHolderInfoBean> {

    public RecyclerPlaceViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
        super(itemView);
    }

    @Override
    public void initViews(View itemView) {

    }

    @Override
    public void initData(RecyclerPlaceHolderInfoBean data) {

    }

}
