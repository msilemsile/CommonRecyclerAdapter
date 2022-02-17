package me.msile.train.commonrecycleradapter.samedata;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import me.msile.lib.commonrecycleradapter.holder.CommonRecyclerViewHolder;
import me.msile.train.commonrecycleradapter.R;

public class SameDataViewHolder3 extends CommonRecyclerViewHolder<SameData> {

    public SameDataViewHolder3(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews(View itemView) {
        Log.d("CRVH", "SameDataViewHolder3 initViews");
    }

    @Override
    public void initData(SameData data) {
        Log.d("CRVH", "SameDataViewHolder3 initData");
    }

    public static class Factory implements CommonRecyclerViewHolder.Factory<SameData> {

        @Override
        public CommonRecyclerViewHolder<SameData> createViewHolder(View view) {
            return new SameDataViewHolder3(view);
        }

        @Override
        public int getLayResId() {
            return R.layout.item_same_data_class_lay3;
        }

        @Override
        public Class<SameData> getItemDataClass() {
            return SameData.class;
        }

    }
}
