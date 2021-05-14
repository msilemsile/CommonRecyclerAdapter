package me.msile.train.commonrecycleradapter.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.msile.train.commonrecycleradapter.adapter.holder.CommonRecyclerViewHolder;
import me.msile.train.commonrecycleradapter.adapter.holder.UnknownViewHolder;
import me.msile.train.commonrecycleradapter.adapter.model.RecyclerItemInfoBean;
import me.msile.train.commonrecycleradapter.adapter.model.RecyclerPlaceHolderInfoBean;

/**
 * 通用recyclerAdapter
 */
public class CommonRecyclerAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //每个item信息
    private SparseArray<RecyclerItemInfoBean> mItemInfoSA = new SparseArray<>();
    //列表数据
    private List<Object> mItemDataList = new ArrayList<>();
    //事件信息
    private Set<OnItemEventListener> mItemEventListenerList = new HashSet<>();

    public CommonRecyclerAdapter() {
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CommonRecyclerAdapter", "onCreateViewHolder");
        Context context = parent.getContext();
        if (viewType == 0) {
            return new UnknownViewHolder(context);
        }
        RecyclerItemInfoBean itemInfoBean = mItemInfoSA.get(viewType);
        if (itemInfoBean == null) {
            return new UnknownViewHolder(context);
        }
        int layResId = itemInfoBean.getLayResId();
        View itemView = LayoutInflater.from(context).inflate(layResId, parent, false);
        if (itemView == null) {
            return new UnknownViewHolder(context);
        }
        CommonRecyclerViewHolder.Factory viewHolderFactory = itemInfoBean.getViewHolderFactory();
        CommonRecyclerViewHolder viewHolderImpl = viewHolderFactory.createViewHolder(itemView);
        if (viewHolderImpl == null) {
            return new UnknownViewHolder(context);
        }
        RecyclerView.LayoutParams itemLayoutParams = viewHolderImpl.getItemLayoutParams();
        if (itemLayoutParams != null) {
            itemView.setLayoutParams(itemLayoutParams);
        }
        viewHolderImpl.setDataAdapter(this);
        return viewHolderImpl;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder holder, int position) {
        Log.d("CommonRecyclerAdapter", "onBindViewHolder");
        Object data = getData(position);
        if (data != null) {
            holder.setData(data);
        }
    }

    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }

    public boolean isEmptyData() {
        return mItemDataList.isEmpty();
    }

    public Object getData(int position) {
        int itemCount = mItemDataList.size();
        if (position < 0) {
            return null;
        }
        if (position >= itemCount) {
            return null;
        }
        return mItemDataList.get(position);
    }

    public int findItemDataIndex(Object obj) {
        return mItemDataList.indexOf(obj);
    }

    public List<Object> getDataList() {
        return mItemDataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 0) {
            return 0;
        }
        int itemCount = mItemDataList.size();
        if (position >= itemCount) {
            return 0;
        }
        Object obj = mItemDataList.get(position);
        if (obj instanceof RecyclerPlaceHolderInfoBean) {
            return ((RecyclerPlaceHolderInfoBean) obj).getLayResId();
        }
        for (int i = 0; i < mItemInfoSA.size(); i++) {
            RecyclerItemInfoBean itemInfoBean = mItemInfoSA.valueAt(i);
            Class dataClass = itemInfoBean.getItemDataClass();
            if (obj.getClass() == dataClass) {
                return itemInfoBean.getLayResId();
            }
        }
        return 0;
    }

    public <T> void addItemInfo(@LayoutRes int layoutId, @NonNull Class<T> dataClass, @NonNull CommonRecyclerViewHolder.Factory<T> factory) {
        mItemInfoSA.put(layoutId, new RecyclerItemInfoBean<T>(layoutId, dataClass, factory));
    }

    public void addLayout(@LayoutRes int layoutId) {
        addLayoutWithTag(layoutId, null);
    }

    public void addLayoutWithTag(@LayoutRes int layoutId, Object tag) {
        mItemInfoSA.put(layoutId, new RecyclerPlaceHolderInfoBean(layoutId));
        RecyclerPlaceHolderInfoBean placeHolderInfoBean = new RecyclerPlaceHolderInfoBean(layoutId);
        placeHolderInfoBean.setTag(tag);
        addData(placeHolderInfoBean);
    }

    public void addLayout(@LayoutRes int layoutId, @NonNull CommonRecyclerViewHolder.Factory<RecyclerPlaceHolderInfoBean> factory) {
        addLayoutWithTag(layoutId, factory, null);
    }

    public void addLayoutWithTag(@LayoutRes int layoutId, @NonNull CommonRecyclerViewHolder.Factory<RecyclerPlaceHolderInfoBean> factory, Object tag) {
        mItemInfoSA.put(layoutId, new RecyclerPlaceHolderInfoBean(layoutId, factory));
        RecyclerPlaceHolderInfoBean placeHolderInfoBean = new RecyclerPlaceHolderInfoBean(layoutId, factory);
        placeHolderInfoBean.setTag(tag);
        addData(placeHolderInfoBean);
    }

    public void addData(Object obj) {
        if (obj != null) {
            mItemDataList.add(obj);
            int itemCount = mItemDataList.size();
            notifyItemInserted(itemCount - 1);
            for (OnItemEventListener eventListener : mItemEventListenerList) {
                eventListener.onAddItemData(obj);
            }
            Log.d("CommonRecyclerAdapter", "addData");
        }
    }

    public void addDataList(java.util.List objList) {
        if (objList != null) {
            int beforeSize = mItemDataList.size();
            mItemDataList.addAll(objList);
            int afterSize = mItemDataList.size();
            if (afterSize > beforeSize) {
                int appendSize = afterSize - beforeSize;
                notifyItemRangeInserted(beforeSize, appendSize);
                for (OnItemEventListener eventListener : mItemEventListenerList) {
                    eventListener.onAddItemDataList(objList);
                }
            }
            Log.d("CommonRecyclerAdapter", "addDataList");
        }
    }

    public void changeData(Object obj) {
        if (obj != null) {
            int index = mItemDataList.indexOf(obj);
            if (index != -1) {
                mItemDataList.set(index, obj);
                notifyItemChanged(index);
                for (OnItemEventListener eventListener : mItemEventListenerList) {
                    eventListener.onChangeItemData(obj);
                }
                Log.d("CommonRecyclerAdapter", "changeData");
            }
        }
    }

    public void changeDataList(java.util.List objList) {
        if (objList != null) {
            java.util.List tempChangedList = new ArrayList();
            boolean needUpdate = false;
            for (Object obj : objList) {
                int index = mItemDataList.indexOf(obj);
                if (index != -1) {
                    mItemDataList.set(index, obj);
                    needUpdate = true;
                    tempChangedList.add(obj);
                }
            }
            if (needUpdate) {
                notifyDataSetChanged();
                for (OnItemEventListener eventListener : mItemEventListenerList) {
                    eventListener.onChangeItemDataList(tempChangedList);
                }
                Log.d("CommonRecyclerAdapter", "changeData");
            }

        }
    }

    public void removeData(Object obj) {
        if (obj != null) {
            int index = mItemDataList.indexOf(obj);
            if (index != -1) {
                mItemDataList.remove(index);
                notifyItemRemoved(index);
                for (OnItemEventListener eventListener : mItemEventListenerList) {
                    eventListener.onRemoveItemData(obj);
                }
                Log.d("CommonRecyclerAdapter", "removeData");
            }
        }
    }

    public void removeAllData() {
        if (mItemDataList.isEmpty()) {
            return;
        }
        mItemDataList.clear();
        notifyDataSetChanged();
        for (OnItemEventListener eventListener : mItemEventListenerList) {
            eventListener.onRemoveAllItemData();
        }
        Log.d("CommonRecyclerAdapter", "removeAllData");
    }

    public void swapItemData(int startPosition, int endPosition) {
        if (mItemDataList.isEmpty()) {
            return;
        }
        Collections.swap(mItemDataList, startPosition, endPosition);
        notifyItemMoved(startPosition, endPosition);
    }

    public void addItemEventListener(OnItemEventListener eventListener) {
        if (eventListener != null) {
            mItemEventListenerList.add(eventListener);
        }
    }

    public void removeItemEventListener(OnItemEventListener eventListener) {
        if (eventListener != null) {
            mItemEventListenerList.remove(eventListener);
        }
    }

    public void notifyItemClickListener(View view, Object obj) {
        for (OnItemEventListener eventListener : mItemEventListenerList) {
            eventListener.onClickItemView(view, obj);
        }
    }

    public void notifyItemClickListener(View view) {
        for (OnItemEventListener eventListener : mItemEventListenerList) {
            eventListener.onClickItemView(view);
        }
    }

    public interface OnItemEventListener {
        default void onAddItemData(Object obj) {
        }

        default void onAddItemDataList(List objList) {
        }

        default void onRemoveItemData(Object obj) {
        }

        default void onRemoveAllItemData() {
        }

        default void onChangeItemData(Object obj) {
        }

        default void onChangeItemDataList(List objList) {
        }

        default void onClickItemView(View view, Object obj) {
        }

        default void onClickItemView(View view) {
        }
    }

}
