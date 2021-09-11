package me.msile.train.commonrecycleradapter.adapter.holder;

import androidx.annotation.LayoutRes;

public interface ItemViewTypeHolder {
    @LayoutRes
    int getItemViewType(Object obj);
}
