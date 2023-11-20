package com.me.mseotsanyana.mande.infrastructure.services;

import android.os.Handler;
import android.os.Looper;

import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;

public class CMainThreadImpl implements IMainThread {
    private static IMainThread mainThread;

    private final Handler handler;

    private CMainThreadImpl() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    public static IMainThread getInstance() {
        if (mainThread == null) {
            mainThread = new CMainThreadImpl();
        }
        return mainThread;
    }
}
