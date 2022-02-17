package me.msile.train.commonrecycleradapter.samedata;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import me.msile.lib.commonrecycleradapter.holder.CommonRecyclerViewHolder;
import me.msile.train.commonrecycleradapter.R;

public class SameDataViewHolder2 extends CommonRecyclerViewHolder<SameData> {

    public SameDataViewHolder2(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews(View itemView) {
        Log.d("CRVH", "SameDataViewHolder2 initViews");
    }

    @Override
    public void initData(SameData data) {
        Log.d("CRVH", "SameDataViewHolder2 initData");
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
