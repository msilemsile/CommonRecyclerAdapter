package me.msile.train.commonrecycleradapter.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.msile.train.commonrecycleradapter.adapter.holder.CommonRecyclerViewHolder;
import me.msile.train.commonrecycleradapter.adapter.holder.UnknownRecyclerViewHolder;
import me.msile.train.commonrecycleradapter.adapter.model.RecyclerItemInfoBean;
import me.msile.train.commonrecycleradapter.adapter.model.RecyclerPlaceHolderInfoBean;
import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerItemViewModel;
import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerPlaceHolderViewModel;

/**
 * 通用recyclerAdapter
 */
public class CommonRecyclerAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    public static final int MATCH_WRAP = 0;
    public static final int WRAP_MATCH = 1;
    public static final int MATCH_MATCH = 2;
    public static final int WRAP_WRAP = 3;

    //每个item信息
    private SparseArray<RecyclerItemInfoBean> mItemInfoSA = new SparseArray<>();
    //列表数据
    private List<Object> mItemDataList = new ArrayList<>();
    //事件信息
    private Set<ItemEventListener> mItemEventListenerList = new HashSet<>();
    //item的布局参数
    private int itemLayoutParams;

    public CommonRecyclerAdapter() {
    }

    public void setItemLayoutParams(int itemLayoutParams) {
        this.itemLayoutParams = itemLayoutParams;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CommonRecyclerAdapter", "onCreateViewHolder");
        Context context = parent.getContext();
        if (viewType == 0) {
            return new UnknownRecyclerViewHolder(context);
        }
        RecyclerItemInfoBean itemInfoBean = mItemInfoSA.get(viewType);
        if (itemInfoBean == null) {
            return new UnknownRecyclerViewHolder(context);
        }
        Class<? extends RecyclerItemViewModel> viewModelClass = itemInfoBean.getItemViewModelClass();
        RecyclerItemViewModel viewModelImpl = newViewModelImpl(viewModelClass, context);
        if (viewModelImpl == null) {
            viewModelImpl = new RecyclerPlaceHolderViewModel(context);
        }
        viewModelImpl.inflateItemLayout(viewType);
        viewModelImpl.setDataAdapter(this);
        switch (itemLayoutParams) {
            case MATCH_MATCH:
                viewModelImpl.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
                break;
            case WRAP_WRAP:
                viewModelImpl.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                break;
            case WRAP_MATCH:
                viewModelImpl.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT));
                break;
            case MATCH_WRAP:
            default:
                viewModelImpl.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                break;
        }

        return new CommonRecyclerViewHolder(viewModelImpl);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder holder, int position) {
        Log.d("CommonRecyclerAdapter", "onBindViewHolder");
        RecyclerItemViewModel viewModelImpl = holder.getViewModelImpl();
        if (viewModelImpl == null) {
            return;
        }
        Object data = getData(position);
        if (data != null) {
            viewModelImpl.setData(data);
        }
    }

    private RecyclerItemViewModel newViewModelImpl(Class<? extends RecyclerItemViewModel> viewModelClass, Context context) {
        if (viewModelClass == null) {
            return null;
        }
        try {
            Constructor<? extends RecyclerItemViewModel> viewModelConstructor = viewModelClass.getConstructor(Context.class);
            return viewModelConstructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public <T> void addItemInfo(@LayoutRes int layoutId, @NonNull Class<T> dataClass, @NonNull Class<? extends RecyclerItemViewModel<T>> viewModelClass) {
        mItemInfoSA.put(layoutId, new RecyclerItemInfoBean<T>(layoutId, dataClass, viewModelClass));
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

    public void addLayout(@LayoutRes int layoutId, @NonNull Class<? extends RecyclerPlaceHolderViewModel> viewModelClass) {
        addLayoutWithTag(layoutId, viewModelClass, null);
    }

    public void addLayoutWithTag(@LayoutRes int layoutId, @NonNull Class<? extends RecyclerPlaceHolderViewModel> viewModelClass, Object tag) {
        mItemInfoSA.put(layoutId, new RecyclerPlaceHolderInfoBean(layoutId, viewModelClass));
        RecyclerPlaceHolderInfoBean placeHolderInfoBean = new RecyclerPlaceHolderInfoBean(layoutId, viewModelClass);
        placeHolderInfoBean.setTag(tag);
        addData(placeHolderInfoBean);
    }

    public void addData(Object obj) {
        if (obj != null) {
            mItemDataList.add(obj);
            int itemCount = mItemDataList.size();
            notifyItemInserted(itemCount - 1);
            for (ItemEventListener eventListener : mItemEventListenerList) {
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
                for (ItemEventListener eventListener : mItemEventListenerList) {
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
                for (ItemEventListener eventListener : mItemEventListenerList) {
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
                for (ItemEventListener eventListener : mItemEventListenerList) {
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
                for (ItemEventListener eventListener : mItemEventListenerList) {
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
        for (ItemEventListener eventListener : mItemEventListenerList) {
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

    public void addItemEventListener(ItemEventListener eventListener) {
        if (eventListener != null) {
            mItemEventListenerList.add(eventListener);
        }
    }

    public void removeItemEventListener(ItemEventListener eventListener) {
        if (eventListener != null) {
            mItemEventListenerList.remove(eventListener);
        }
    }

    public Set<ItemEventListener> getItemEventListenerList() {
        return mItemEventListenerList;
    }

    public interface ItemEventListener {
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
    }

}
