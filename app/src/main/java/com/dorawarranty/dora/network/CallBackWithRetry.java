package com.dorawarranty.dora.network;

import android.util.Log;

import retrofit2.Callback;
import retrofit2.Call;


abstract class CallbackWithRetry<T> implements Callback<T> {

    private static final int TOTAL_RETRIES = 10;
    private static final String TAG = CallbackWithRetry.class.getSimpleName();
    private int retryCount = 0;

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, t.getLocalizedMessage());
        if (retryCount++ < TOTAL_RETRIES) {
            Log.v(TAG, "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
            retry(call);
        }
    }

    private void retry(Call<T> call) {
        call.clone().enqueue(this);
    }
}