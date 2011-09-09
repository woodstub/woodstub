/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.wooddog.woodstub.*;

import java.util.LinkedList;
import java.util.List;

public class WoodRunner extends BlockJUnit4ClassRunner {
    private static final List<StubListener> LISTENERS = new LinkedList<StubListener>();

    public WoodRunner(Class<?> clazz) throws InitializationError {
        super(init(clazz));
    }

    /**
     * Adds a listener which will be notified with every method call.
     * @param listener The listener to register
     */
    public static void addListener(StubListener listener) {
        LISTENERS.add(listener);
    }

    /**
     * Unregisters a listener.
     * @param listener The listener to unregister
     */
    public static void removeListener(StubListener listener) {
        LISTENERS.remove(listener);
    }

    /**
     * Cleans up all resources.
     */
    public static void cleanup() {
        ClassLoaderWrapper.cleanup();
        LISTENERS.clear();
        Config.cleanUp();
        CodeDirector.cleanUp();
        Thread.currentThread().setContextClassLoader(null);
    }

    /**
     * Part of the callback logic. Not to be invoked manually.
     * @param event
     */
    public static void notify(StubEvent event) {
        for (StubListener listener : LISTENERS) {
            listener.invoked(event);
        }

        if (event.getRedirectToRealClass()) {
            redirectCallToRealClass(event);
        }
    }

    /**
     * JUnit method. Not to be invoked manually.
     */
    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        ClassLoader ccl = getOriginalContextClassLoader();
        changeContextClassLoaderToEndorsed();

        try {
            super.runChild(method, notifier);
        } catch (Exception x) {
            throw new StubException("internal error, please report", x);
        } finally {
            putBackOriginalClassLoader(ccl);
        }
    }

    private static Class init(Class<?> clazz) throws InitializationError {
        return new StubCompiler(clazz).compileAndInitialise();
    }

    private static void redirectCallToRealClass(StubEvent event) {
        Object result = new OriginalInvoker(event).invoke();
        event.setResult(result);
    }

    private ClassLoader getOriginalContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private void changeContextClassLoaderToEndorsed() {
        Thread.currentThread().setContextClassLoader(getTestClass().getJavaClass().getClassLoader());
    }

    private void putBackOriginalClassLoader(ClassLoader ccl) {
        Thread.currentThread().setContextClassLoader(ccl);
    }

}
