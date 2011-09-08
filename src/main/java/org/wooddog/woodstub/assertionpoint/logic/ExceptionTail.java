/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;

class ExceptionTail implements AssertionTail {
    private Throwable exception;

    public ExceptionTail(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public void cleanResources() {
        exception =null;
    }

    @Override
    public void invokeTail(StubEvent event) {
        event.raiseException(exception);
    }
}
