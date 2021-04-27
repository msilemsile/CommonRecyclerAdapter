package me.msile.train.commonrecycleradapter.adapter.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerItemViewModel;

/**
 * 通用recyclerView viewHolder
 */
public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItemViewModel mViewModelImpl;

    public CommonRecyclerViewHolder(RecyclerItemViewModel baseViewModel) {
        this(baseViewModel.getView());
        this.mViewModelImpl = baseViewModel;
    }

    CommonRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public RecyclerItemViewModel getViewModelImpl() {
        return mViewModelImpl;
    }
}
