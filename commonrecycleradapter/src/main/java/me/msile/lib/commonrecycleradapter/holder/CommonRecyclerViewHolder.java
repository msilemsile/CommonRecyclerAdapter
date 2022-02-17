package me.msile.lib.commonrecycleradapter.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import me.msile.lib.commonrecycleradapter.CommonRecyclerAdapter;
import me.msile.lib.commonrecycleradapter.R;

/**
 * @param <T> 数据模型
 */
public abstract class CommonRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected View mItemView;
    protected T mData;
    protected CommonRecyclerAdapter mDataAdapter;
    protected Context mContext;

    public CommonRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mItemView = itemView;
        initViews(itemView);
    }

    public void setParentView(ViewParent mParentView) {
        initParentView(mParentView);
    }

    public void setDataAdapter(CommonRecyclerAdapter adapter) {
        this.mDataAdapter = adapter;
    }

    public void setData(T mData) {
        this.mData = mData;
        initData(mData);
        mItemView.setTag(R.id.vh_data_tag, mData);
    }

    protected View findViewById(@IdRes int id) {
        return mItemView.findViewById(id);
    }

    public int getDataPosition() {
        return mDataAdapter.findItemDataIndex(mData);
    }

    public void notifyItemDataChange() {
        mDataAdapter.changeData(mData);
    }

    public View getItemView() {
        return mItemView;
    }

    protected void initParentView(ViewParent viewParent) {

    }

    public boolean isFirstInitData() {
        Object dataTag = mItemView.getTag(R.id.vh_data_tag);
        boolean firstInitData = dataTag != mData;
        Log.d("CRVH", getHolderClassName() + " isFirstInitData = " + firstInitData);
        return firstInitData;
    }

    public void putFirstInitData(boolean isFirstInit) {
        if (isFirstInit) {
            mItemView.setTag(R.id.vh_data_tag, null);
        }
    }

    public void putAdapterPrivateData(@NonNull String key, @NonNull Object value) {
        String holderDataKey = getHolderClassName() + "_" + key;
        mDataAdapter.putAdapterPrivateData(holderDataKey, value);
    }

    public @Nullable
    Object getAdapterPrivateData(@NonNull String key) {
        String holderDataKey = getHolderClassName() + "_" + key;
        return mDataAdapter.getAdapterPrivateData(holderDataKey);
    }

    public boolean isHolderDestroyed(T oldData) {
        return mDataAdapter.findItemDataIndex(oldData) < 0;
    }

    public String getHolderClassName() {
        return this.getClass().getCanonicalName();
    }

    public abstract void initViews(View itemView);

    public abstract void initData(T data);

    public RecyclerView.LayoutParams getCustomLayoutParams(ViewGroup parent) {
        return null;
    }

    public interface Factory<T> {
        CommonRecyclerViewHolder<T> createViewHolder(View itemView);

        @LayoutRes
        int getLayResId();

        Class<T> getItemDataClass();
    }

}
