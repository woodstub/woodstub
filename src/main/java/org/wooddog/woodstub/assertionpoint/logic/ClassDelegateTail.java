/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.StubException;
import org.wooddog.woodstub.StubHelper;
import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;

public class ClassDelegateTail implements AssertionTail{
    private StubListener delegate;

    public ClassDelegateTail(Class callbackClass) {
        delegate = createStubListenerInstance(callbackClass);
    }

    @Override
    public void invokeTail(StubEvent event) {
        delegate.invoked(event);
    }

    @Override
    public void cleanResources() {
        delegate = null;
    }

    private StubListener createStubListenerInstance(Class callbackClass) {
        Class[] interfaces = callbackClass.getInterfaces();
        for (Class c : interfaces) {
            if (classIsStubListener(c)) {
                return createNewInstance(callbackClass);

            }
        }

        throw new StubException("Cannot delegate to class[" + callbackClass.getName() + "] since it does not implement StubListener");
    }

    private boolean classIsStubListener(Class c) {
        return c.getCanonicalName().equals(StubListener.class.getCanonicalName());
    }

    private StubListener createNewInstance(Class callbackClass) {
        return (StubListener) StubHelper.newInstance(callbackClass);
    }

}
