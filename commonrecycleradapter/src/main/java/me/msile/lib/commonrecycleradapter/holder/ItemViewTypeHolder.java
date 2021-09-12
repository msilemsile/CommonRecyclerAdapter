package me.msile.lib.commonrecycleradapter.holder;

import androidx.annotation.LayoutRes;

public interface ItemViewTypeHolder {
    @LayoutRes
    int getItemViewType(Object obj);
}
