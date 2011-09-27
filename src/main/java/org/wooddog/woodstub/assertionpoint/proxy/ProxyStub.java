/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.proxy;

import org.wooddog.woodstub.InternalStubEvent;
import org.wooddog.woodstub.junit.WoodRunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Handles method-calls in Proxies by using the Woodstub callback-logic
 */
class ProxyStub implements InvocationHandler {
    private InternalStubEvent call;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        call = createStubEvent(method, args);
        performCallback();
        return getResult();
    }

    private InternalStubEvent createStubEvent(Method method, Object[] args) {
        return new StubEventCreator(method, args).create();
    }

    private void performCallback() throws Throwable {
        WoodRunner.notify(call);
        handleExceptions();
    }

    private Object getResult() {
        return call.getResult();
    }

    private void handleExceptions() throws Throwable {
        if (call.getException() != null) {
            throw call.getException();
        }
    }

}
