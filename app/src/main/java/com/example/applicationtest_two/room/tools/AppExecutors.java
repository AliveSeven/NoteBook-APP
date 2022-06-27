/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.applicationtest_two.room.tools;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 线程池类 */

public class AppExecutors {
    /**磁盘IO线程池**/
    private final Executor mDiskIO;
    /**网络IO线程池**/
    private final Executor mNetworkIO;
    /**UI线程**/
    private final Executor mMainThread;

    private volatile static AppExecutors appExecutors;

    public static AppExecutors getInstance() {
        if (appExecutors == null) {
            synchronized (AppExecutors.class) {
                if (appExecutors == null) {
                    appExecutors = new AppExecutors();
                }
            }
        }
        return appExecutors;
    }

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    /* 磁盘IO线程池（单线程）：与磁盘操作有关的进行使用此线程(如读写数据库,读写文件)
     * 禁止延迟,避免等待，此线程不用考虑同步问题*/
    public Executor diskIO() {
        return mDiskIO;
    }

    /* 网络IO线程池，网络请求,异步任务等适用此线程
     * 不建议在这个线程 sleep 或者 wait*/
    public Executor networkIO() {
        return mNetworkIO;
    }

    /* UI线程，Android 的MainThread，UI线程不能做的事情这个都不能做*/
    public Executor mainThread() {
        return mMainThread;
    }

    //从线程池中取一个线程，来执行
    private static Executor diskIoExecutor() {
        return new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    //从线程池中取一个线程，来执行
    private static Executor networkExecutor() {
        return new ThreadPoolExecutor(3, 6, 1000,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    // 主线程
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        //从线程池中取一个线程，来执行
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
