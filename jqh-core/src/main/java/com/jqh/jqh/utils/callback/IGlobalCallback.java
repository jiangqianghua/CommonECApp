package com.jqh.jqh.utils.callback;

import android.support.annotation.Nullable;

public interface IGlobalCallback<T> {
    void executeCallback(@Nullable T args);
}
