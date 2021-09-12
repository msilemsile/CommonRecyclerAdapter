package me.msile.lib.commonrecycleradapter.holder;

import android.content.Context;
import android.view.View;

/**
 * 未知类型布局
 */
public class UnknownViewHolder extends CommonRecyclerViewHolder<Object> {

    public UnknownViewHolder(Context context) {
        super(new UnknownView(context));
    }

    @Override
    public void initViews(View itemView) {

    }

    @Override
    public void initData(Object data) {

    }

}
