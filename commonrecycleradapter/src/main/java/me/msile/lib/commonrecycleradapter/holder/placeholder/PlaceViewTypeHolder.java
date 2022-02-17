package me.msile.lib.commonrecycleradapter.holder.placeholder;

import me.msile.lib.commonrecycleradapter.holder.ItemViewTypeHolder;

public class PlaceViewTypeHolder implements ItemViewTypeHolder {

    @Override
    public int getItemViewType(Object obj) {
        if (obj instanceof PlaceModel) {
            PlaceModel holderModel = (PlaceModel) obj;
            return holderModel.getLayoutResId();
        }
        return 0;
    }
}
