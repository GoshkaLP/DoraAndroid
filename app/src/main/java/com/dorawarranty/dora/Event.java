package com.dorawarranty.dora;

import androidx.annotation.Nullable;

public class Event<T> {

    private T mContent;

    private boolean hasBeenHandled = false;


    public Event(T content) {
        if (content == null) {
            throw new IllegalArgumentException("null values in Event are not allowed.");
        }
        mContent = content;
    }

    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return mContent;
        }
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
