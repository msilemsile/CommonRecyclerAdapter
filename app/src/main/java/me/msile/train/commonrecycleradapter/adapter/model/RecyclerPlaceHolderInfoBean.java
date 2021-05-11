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
        super(layResId, RecyclerPlaceHolderInfoBean.class, RecyclerPlaceViewHolder.class);
    }

    public RecyclerPlaceHolderInfoBean(int layResId, Class<? extends CommonRecyclerViewHolder<RecyclerPlaceHolderInfoBean>> mItemViewModelClass) {
        super(layResId, RecyclerPlaceHolderInfoBean.class, mItemViewModelClass);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }
}