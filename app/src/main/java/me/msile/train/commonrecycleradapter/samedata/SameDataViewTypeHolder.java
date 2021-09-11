package me.msile.train.commonrecycleradapter.samedata;

import me.msile.train.commonrecycleradapter.R;
import me.msile.train.commonrecycleradapter.adapter.holder.ItemViewTypeHolder;

public class SameDataViewTypeHolder implements ItemViewTypeHolder {
    @Override
    public int getItemViewType(Object obj) {
        SameData sameData = (SameData) obj;
        switch (sameData.getType()) {
            case 1:
                return R.layout.item_same_data_class_lay1;
            case 2:
                return R.layout.item_same_data_class_lay2;
            case 3:
                return R.layout.item_same_data_class_lay3;
        }
        return R.layout.item_same_data_class_lay1;
    }
}
