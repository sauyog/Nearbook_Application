package org.briarproject.bramble.lifecycle;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.briarproject.bramble.api.lifecycle.IoExecutor;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.bramble.api.lifecycle.ShutdownManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LifecycleModule {

    @Provides
    @Singleton
    ShutdownManager provideShutdownManager() {
        return new ShutdownManagerImpl();
    }

    @Provides
    @Singleton
    LifecycleManager provideLifecycleManager(
            LifecycleManagerImpl lifecycleManager) {
        return lifecycleManager;
    }

    @Provides
    @Singleton
    @IoExecutor
    Executor provideIoExecutor(LifecycleManager lifecycleManager,
                               ThreadFactory threadFactory) {
        // The thread pool is unbounded, so use direct handoff
        BlockingQueue<Runnable> queue = new SynchronousQueue<>();
        // Discard tasks that are submitted during shutdown
        RejectedExecutionHandler policy =
                new ThreadPoolExecutor.DiscardPolicy();
        // Create threads as required and keep them in the pool for 60 seconds
        ExecutorService ioExecutor = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE, 60, SECONDS, queue, threadFactory, policy);
        lifecycleManager.registerForShutdown(ioExecutor);
        return ioExecutor;
    }

    public static class EagerSingletons {
        @Inject
        @IoExecutor
        Executor executor;
    }
}
