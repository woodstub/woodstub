/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.wooddog.woodstub.ClassLoaderWrapper;
import org.wooddog.woodstub.Config;
import org.wooddog.woodstub.generator.CodeDirector;
import java.util.LinkedList;
import java.util.List;

public class WoodRunner {
    private static final List<StubListener> LISTENERS = new LinkedList<StubListener>();

    private WoodRunner() {
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
     * @param event The event to notify with
     */
    public static void notify(StubEvent event) {
        for (StubListener listener : LISTENERS) {
            listener.invoked(event);
        }

        if (event.getRedirectToRealClass()) {
            redirectCallToRealClass(event);
        }
    }

    private static void redirectCallToRealClass(StubEvent event) {
        Object result = new OriginalInvoker(event).invoke();
        event.setResult(result);
    }

}
