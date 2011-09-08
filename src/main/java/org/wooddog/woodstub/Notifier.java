/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;

import java.util.ArrayList;
import java.util.List;

// TODO: make this class thread safe
class Notifier {
    private static final List<StubListener> LISTENERS = new ArrayList<StubListener>();

    public static void notify(StubEvent event) {
        for (org.wooddog.woodstub.junit.StubListener LISTENER : LISTENERS) {
            LISTENER.invoked(event);
        }
    }

    public static void addListener(StubListener listener) {
        LISTENERS.add(listener);
    }

    public static void removeListener(StubListener listener) {
        LISTENERS.remove(listener);
    }

    public static void clear() {
        LISTENERS.clear();
    }
}
