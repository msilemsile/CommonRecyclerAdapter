package me.msile.train.commonrecycleradapter.adapter.model;

import me.msile.train.commonrecycleradapter.adapter.holder.CommonRecyclerViewHolder;
import me.msile.train.commonrecycleradapter.adapter.holder.RecyclerPlaceViewHolder;

/**
 * 占位Place Holder
 */
public class RecyclerPlaceHolderInfoBean extends RecyclerItemInfoBean<RecyclerPlaceHolderInfoBean> {

    //唯一标识
    private Object tag;

    public RecyclerPlaceHolderInfoBean(int layResId) {
        super(layResId, RecyclerPlaceHolderInfoBean.class, new RecyclerPlaceViewHolder.Factory());
    }

    public RecyclerPlaceHolderInfoBean(int layResId, CommonRecyclerViewHolder.Factory<RecyclerPlaceHolderInfoBean> factory) {
        super(layResId, RecyclerPlaceHolderInfoBean.class, factory);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }
}