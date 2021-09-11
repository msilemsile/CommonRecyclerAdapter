package me.msile.train.commonrecycleradapter.custom;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import me.msile.train.commonrecycleradapter.R;
import me.msile.train.commonrecycleradapter.adapter.holder.CommonRecyclerViewHolder;

public class CustomDataViewHolder extends CommonRecyclerViewHolder<CustomData> {

    private TextView tvCustomData;

    public CustomDataViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews(View itemView) {
        tvCustomData = itemView.findViewById(R.id.tv_custom_data);
    }

    @Override
    public void initData(CustomData data) {
        tvCustomData.setText("自定义数据布局: " + data.getCustomData());
    }

    public static class Factory implements CommonRecyclerViewHolder.Factory<CustomData> {

        @Override
        public CommonRecyclerViewHolder<CustomData> createViewHolder(View view) {
            return new CustomDataViewHolder(view);
        }

        @Override
        public int getLayResId() {
            return R.layout.item_custom_data;
        }

        @Override
        public Class<CustomData> getItemDataClass() {
            return CustomData.class;
        }

    }
}
