package com.flybits.android.samples.vanilla.interfaces;

public interface IProgressDialog {

    void onStartProgress(String text, boolean isCancelable);

    void onStopProgress();
}
