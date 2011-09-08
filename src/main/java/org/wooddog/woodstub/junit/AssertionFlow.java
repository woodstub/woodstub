/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import java.util.ArrayList;
import java.util.List;

public class AssertionFlow implements StubListener {
    private final List<StubListener> assertionList;
    private int index;
    private boolean running;

    public AssertionFlow() {
        this.assertionList = new ArrayList<StubListener>();
    }

    public void skip(int steps) {
        for (int i = 0; i < steps; i++) {
            assertionList.add(null);
        }
    }

    public void add(StubListener listener) {
        assertionList.add(listener);
    }

    public void clear() {
        assertionList.clear();
    }

    public void start() {
        index = 0;
        running = true;
    }

    public boolean isComplete() {
        return index == assertionList.size();
    }

    @Override
    public void invoked(StubEvent event) {
        if (!running) {
            return;
        }

        StubListener listener;
        if (index<assertionList.size()) {
            listener = assertionList.get(index);
        }  else {
            listener=null;
        }

        if (listener != null) {
            index ++;
            listener.invoked(event);
        }
    }
}
