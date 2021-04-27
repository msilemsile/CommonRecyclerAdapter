package me.msile.train.commonrecycleradapter.adapter.model;

import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerItemViewModel;
import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerPlaceHolderViewModel;

/**
 * 占位Place Holder
 */
public class RecyclerPlaceHolderInfoBean extends RecyclerItemInfoBean<RecyclerPlaceHolderInfoBean> {

    //唯一标识
    private Object tag;

    public RecyclerPlaceHolderInfoBean(int layResId) {
        super(layResId, RecyclerPlaceHolderInfoBean.class, RecyclerPlaceHolderViewModel.class);
    }

    public RecyclerPlaceHolderInfoBean(int layResId, Class<? extends RecyclerItemViewModel<RecyclerPlaceHolderInfoBean>> mItemViewModelClass) {
        super(layResId, RecyclerPlaceHolderInfoBean.class, mItemViewModelClass);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }
}