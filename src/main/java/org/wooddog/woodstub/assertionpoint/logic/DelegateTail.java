/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;

class DelegateTail implements AssertionTail {
    private StubListener callback;

    public DelegateTail(StubListener callback) {
        this.callback = callback;
    }

    @Override
    public void invokeTail(StubEvent event) {
        callback.invoked(event);
    }

    @Override
    public void cleanResources() {
        callback=null;
    }
}
