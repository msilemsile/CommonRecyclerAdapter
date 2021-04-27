package me.msile.train.commonrecycleradapter.adapter.model;

import androidx.annotation.NonNull;

import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerItemViewModel;

/**
 * 每个recyclerView的item的信息
 * @param <T> 数据模型
 */
public class RecyclerItemInfoBean<T> {

    //布局id
    private int layResId;
    //数据模型class
    private @NonNull
    Class<T> mItemDataClass;
    //布局+数据class
    private @NonNull
    Class<? extends RecyclerItemViewModel<T>> mItemViewModelClass;

    public RecyclerItemInfoBean(int layResId, @NonNull Class<T> mItemDataClass, @NonNull Class<? extends RecyclerItemViewModel<T>> mItemViewModelClass) {
        this.layResId = layResId;
        this.mItemDataClass = mItemDataClass;
        this.mItemViewModelClass = mItemViewModelClass;
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

    public Class<? extends RecyclerItemViewModel<T>> getItemViewModelClass() {
        return mItemViewModelClass;
    }

    public void setItemViewModelClass(Class<? extends RecyclerItemViewModel<T>> mItemViewModelClass) {
        this.mItemViewModelClass = mItemViewModelClass;
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
