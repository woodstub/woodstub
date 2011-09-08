/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;

public class ValueTail implements AssertionTail {
    private Object value;

    public ValueTail(Object value) {
        this.value = value;
    }

    @Override
    public void invokeTail(StubEvent event) {
        event.setResult(value);
    }

    @Override
    public void cleanResources() {
        value=null;
    }

}
