package me.msile.train.commonrecycleradapter.adapter.holder;

import android.content.Context;

/**
 * 未知类型布局
 */
public class UnknownViewHolder extends CommonRecyclerViewHolder<Object> {

    public UnknownViewHolder(Context context) {
        super(new UnknownView(context));
    }

    @Override
    public void initViews(android.view.View itemView) {

    }

    @Override
    public void initData(Object data) {

    }

}
