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
}
