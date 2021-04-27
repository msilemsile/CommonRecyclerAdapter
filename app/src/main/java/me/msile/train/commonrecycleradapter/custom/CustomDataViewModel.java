package me.msile.train.commonrecycleradapter.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.msile.train.commonrecycleradapter.R;
import me.msile.train.commonrecycleradapter.adapter.viewmodel.RecyclerItemViewModel;

public class CustomDataViewModel extends RecyclerItemViewModel<CustomData> {

    private TextView tvCustomData;

    public CustomDataViewModel(@NonNull Context context) {
        super(context);
    }

    public CustomDataViewModel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDataViewModel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void findViews() {
        tvCustomData = findViewById(R.id.tv_custom_data);
    }

    @Override
    protected void setDataInner(CustomData data) {
        tvCustomData.setText("自定义数据布局: " + data.getCustomData());
    }
}
