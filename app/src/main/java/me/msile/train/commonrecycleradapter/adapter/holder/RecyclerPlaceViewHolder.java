package me.msile.train.commonrecycleradapter.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;

import me.msile.train.commonrecycleradapter.adapter.model.RecyclerPlaceHolderInfoBean;

/**
 * recyclerView itemView 占位布局(当布局不需要数据模型时)
 */
public class RecyclerPlaceViewHolder extends CommonRecyclerViewHolder<RecyclerPlaceHolderInfoBean> {

    public RecyclerPlaceViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews(View itemView) {

    }

    @Override
    public void initData(RecyclerPlaceHolderInfoBean data) {

    }

    public static class Factory implements CommonRecyclerViewHolder.Factory<RecyclerPlaceHolderInfoBean> {

        @Override
        public CommonRecyclerViewHolder<RecyclerPlaceHolderInfoBean> createViewHolder(View view) {
            return new RecyclerPlaceViewHolder(view);
        }

    }

}
