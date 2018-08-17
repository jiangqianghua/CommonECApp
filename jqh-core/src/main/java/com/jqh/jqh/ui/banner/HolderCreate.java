package com.jqh.jqh.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

public class HolderCreate  implements CBViewHolderCreator<ImageHolder>{
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
