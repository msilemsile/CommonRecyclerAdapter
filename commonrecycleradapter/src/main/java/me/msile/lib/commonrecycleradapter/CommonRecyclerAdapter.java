package me.msile.lib.commonrecycleradapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.msile.lib.commonrecycleradapter.holder.CommonRecyclerViewHolder;
import me.msile.lib.commonrecycleradapter.holder.ItemViewTypeHolder;
import me.msile.lib.commonrecycleradapter.holder.placeholder.PlaceModel;
import me.msile.lib.commonrecycleradapter.holder.placeholder.PlaceRecyclerViewHolder;
import me.msile.lib.commonrecycleradapter.holder.placeholder.PlaceViewTypeHolder;
import me.msile.lib.commonrecycleradapter.holder.unknownholder.UnknownViewHolder;

/**
 * 通用recyclerAdapter
 */
public class CommonRecyclerAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    //创建viewHolder的工厂
    private SparseArray<CommonRecyclerViewHolder.Factory> mItemVHFactorySA = new SparseArray<>();
    //创建占位viewHolder的工厂(用于添加默认布局，addLayout，addLayoutList)
    private SparseArray<PlaceRecyclerViewHolder.Factory> mPlaceVHFactorySA;
    //相同数据模型的不同viewType数据
    private HashMap<Class, ItemViewTypeHolder> mItemViewTypeMap = new HashMap<>();
    //列表数据
    private List<Object> mItemDataList = new ArrayList<>();
    //事件信息
    private Set<OnItemEventListener> mItemEventListenerList = new HashSet<>();
    //是否是view_pager2
    private boolean mIsViewPager2;
    //adapter自定义特定数据（不复用,类似类成员变量,会在调用removeAllData()或者单独调用clearItemVHPrivateData方法清空)）
    private HashMap<String, Object> mAdapterPrivateData = new HashMap<>();

    public CommonRecyclerAdapter(boolean mIsViewPager2) {
        this.mIsViewPager2 = mIsViewPager2;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == 0) {
            return new UnknownViewHolder(context);
        }
        CommonRecyclerViewHolder.Factory viewHolderFactory = mItemVHFactorySA.get(viewType);
        if (viewHolderFactory == null) {
            if (mPlaceVHFactorySA != null) {
                viewHolderFactory = mPlaceVHFactorySA.get(viewType);
            }
            if (viewHolderFactory == null) {
                return new UnknownViewHolder(context);
            }
        }
        int layResId = viewHolderFactory.getLayResId();
        View itemView = LayoutInflater.from(context).inflate(layResId, parent, false);
        if (itemView == null) {
            return new UnknownViewHolder(context);
        }
        CommonRecyclerViewHolder viewHolderImpl = viewHolderFactory.createViewHolder(itemView);
        if (viewHolderImpl == null) {
            return new UnknownViewHolder(context);
        }
        viewHolderImpl.setParentView(parent);
        viewHolderImpl.setDataAdapter(this);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        if (mIsViewPager2) {
            if (layoutParams == null) {
                layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
                itemView.setLayoutParams(layoutParams);
            } else {
                if (layoutParams.width != RecyclerView.LayoutParams.MATCH_PARENT || layoutParams.height != RecyclerView.LayoutParams.MATCH_PARENT) {
                    layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT;
                    layoutParams.height = RecyclerView.LayoutParams.MATCH_PARENT;
                    itemView.setLayoutParams(layoutParams);
                }
            }
        } else {
            RecyclerView.LayoutParams customLayoutParams = viewHolderImpl.getCustomLayoutParams(parent);
            if (customLayoutParams != null) {
                itemView.setLayoutParams(customLayoutParams);
            }
        }
        return viewHolderImpl;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder holder, int position) {
        if (holder instanceof UnknownViewHolder) {
            return;
        }
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
        if (obj == null) {
            return 0;
        }
        //1.相同数据模型(自定义viewType)
        Class<?> objClass = obj.getClass();
        if (!mItemViewTypeMap.isEmpty()) {
            ItemViewTypeHolder itemViewTypeHolder = mItemViewTypeMap.get(objClass);
            if (itemViewTypeHolder != null) {
                return itemViewTypeHolder.getItemViewType(obj);
            }
        }
        //2.不同数据模型(不同viewType)
        for (int i = 0; i < mItemVHFactorySA.size(); i++) {
            CommonRecyclerViewHolder.Factory viewHolderFactory = mItemVHFactorySA.valueAt(i);
            if (viewHolderFactory == null) {
                return 0;
            }
            Class dataClass = viewHolderFactory.getItemDataClass();
            if (objClass == dataClass) {
                return viewHolderFactory.getLayResId();
            }
        }
        return 0;
    }

    public void addViewHolderFactory(@NonNull CommonRecyclerViewHolder.Factory factory) {
        int layResId = factory.getLayResId();
        mItemVHFactorySA.put(layResId, factory);
    }

    public void setItemViewTypeHolder(@NonNull Class cls, @NonNull ItemViewTypeHolder typeHolder) {
        mItemViewTypeMap.put(cls, typeHolder);
    }

    public void addLayout(@LayoutRes int layoutResId) {
        PlaceRecyclerViewHolder.Factory factory = new PlaceRecyclerViewHolder.Factory(layoutResId);
        addLayout(layoutResId, factory);
    }

    public void addLayoutList(@NonNull List<Integer> layoutResIdList) {
        if (layoutResIdList.isEmpty()) {
            return;
        }
        List<PlaceModel> placeModelList = new ArrayList<>();
        for (int i = 0; i < layoutResIdList.size(); i++) {
            int layoutResId = layoutResIdList.get(i);
            PlaceRecyclerViewHolder.Factory factory = new PlaceRecyclerViewHolder.Factory(layoutResId);
            if (mPlaceVHFactorySA == null) {
                mPlaceVHFactorySA = new SparseArray<>();
            }
            mPlaceVHFactorySA.put(layoutResId, factory);
            placeModelList.add(new PlaceModel(layoutResId));
        }
        ItemViewTypeHolder placeModelViewTypeHolder = mItemViewTypeMap.get(PlaceModel.class);
        if (placeModelViewTypeHolder == null) {
            mItemViewTypeMap.put(PlaceModel.class, new PlaceViewTypeHolder());
        }
        addDataList(placeModelList);
    }

    public void addLayout(@LayoutRes int layoutResId, @NonNull PlaceRecyclerViewHolder.Factory factory) {
        if (mPlaceVHFactorySA == null) {
            mPlaceVHFactorySA = new SparseArray<>();
        }
        mPlaceVHFactorySA.put(layoutResId, factory);
        ItemViewTypeHolder placeModelViewTypeHolder = mItemViewTypeMap.get(PlaceModel.class);
        if (placeModelViewTypeHolder == null) {
            mItemViewTypeMap.put(PlaceModel.class, new PlaceViewTypeHolder());
        }
        addData(new PlaceModel(layoutResId));
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

    public void addDataList(List objList) {
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

    public void changeDataList(List objList) {
        if (objList != null) {
            List tempChangedList = new ArrayList();
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

    public void removeAllDataWithoutPrivate() {
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

    public void removeAllData() {
        clearAdapterPrivateData();
        removeAllDataWithoutPrivate();
    }

    public void clearAdapterPrivateData() {
        mAdapterPrivateData.clear();
    }

    public void putAdapterPrivateData(@NonNull String key, @NonNull Object value) {
        mAdapterPrivateData.put(key, value);
    }

    public void removeAdapterPrivateData(@NonNull String key) {
        mAdapterPrivateData.remove(key);
    }

    public @Nullable
    Object getAdapterPrivateData(@NonNull String key) {
        return mAdapterPrivateData.get(key);
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

    public void notifyItemClickListener(CommonRecyclerViewHolder viewHolder, View view, Object obj) {
        for (OnItemEventListener eventListener : mItemEventListenerList) {
            eventListener.onClickItemView(viewHolder, view, obj);
        }
    }

    public void notifyItemClickListener(CommonRecyclerViewHolder viewHolder, View view) {
        for (OnItemEventListener eventListener : mItemEventListenerList) {
            eventListener.onClickItemView(viewHolder, view);
        }
    }

    public void notifyCustomItemEventListener(CommonRecyclerViewHolder viewHolder, int eventId, Object obj) {
        for (OnItemEventListener eventListener : mItemEventListenerList) {
            eventListener.onCustomItemEvent(viewHolder, eventId, obj);
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

        default void onClickItemView(CommonRecyclerViewHolder viewHolder, View view, Object obj) {
        }

        default void onClickItemView(CommonRecyclerViewHolder viewHolder, View view) {
        }

        default void onCustomItemEvent(CommonRecyclerViewHolder viewHolder, int eventId, Object obj) {
        }
    }

}
