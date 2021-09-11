package me.msile.train.commonrecycleradapter.samedata;

import android.view.View;

import androidx.annotation.NonNull;

import me.msile.train.commonrecycleradapter.R;
import me.msile.train.commonrecycleradapter.adapter.holder.CommonRecyclerViewHolder;

public class SameDataViewHolder2 extends CommonRecyclerViewHolder<SameData> {

    public SameDataViewHolder2(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews(View itemView) {
    }

    @Override
    public void initData(SameData data) {

    }

    public static class Factory implements CommonRecyclerViewHolder.Factory<SameData> {

        @Override
        public CommonRecyclerViewHolder<SameData> createViewHolder(View view) {
            return new SameDataViewHolder2(view);
        }

        @Override
        public int getLayResId() {
            return R.layout.item_same_data_class_lay2;
        }

        @Override
        public Class<SameData> getItemDataClass() {
            return SameData.class;
        }

    }
}
