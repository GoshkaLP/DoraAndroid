package com.dorawarranty.dora.qranalyzer.listeners;

public interface QrListener {
    void QrOnSuccess(String result);
    void QrOnFailure();
}
