package me.msile.train.commonrecycleradapter.adapter.model;

import me.msile.train.commonrecycleradapter.adapter.holder.CommonRecyclerViewHolder;

/**
 * 每个recyclerView的item的信息
 *
 * @param <T> 数据模型
 */
public class RecyclerItemInfoBean<T> {

    //布局id
    private int layResId;
    //数据模型class
    private @androidx.annotation.NonNull
    Class<T> mItemDataClass;
    //布局+数据class
    private @androidx.annotation.NonNull
    Class<? extends CommonRecyclerViewHolder<T>> mItemViewHolderClass;

    public RecyclerItemInfoBean(int layResId, @androidx.annotation.NonNull Class<T> mItemDataClass, @androidx.annotation.NonNull Class<? extends CommonRecyclerViewHolder<T>> mItemViewHolderClass) {
        this.layResId = layResId;
        this.mItemDataClass = mItemDataClass;
        this.mItemViewHolderClass = mItemViewHolderClass;
    }

    public RecyclerItemInfoBean(int layResId) {
        this.layResId = layResId;
    }

    public Class<T> getItemDataClass() {
        return mItemDataClass;
    }

    public void setItemDataClass(Class<T> mItemDataClass) {
        this.mItemDataClass = mItemDataClass;
    }

    public int getLayResId() {
        return layResId;
    }

    public void setLayResId(int layResId) {
        this.layResId = layResId;
    }

    public Class<? extends CommonRecyclerViewHolder<T>> getItemViewHolderClass() {
        return mItemViewHolderClass;
    }

    public void setItemViewHolderClass(Class<? extends CommonRecyclerViewHolder<T>> mItemViewHolderClass) {
        this.mItemViewHolderClass = mItemViewHolderClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecyclerItemInfoBean<?> that = (RecyclerItemInfoBean<?>) o;

        return layResId == that.layResId;
    }

    @Override
    public int hashCode() {
        return layResId;
    }
}
