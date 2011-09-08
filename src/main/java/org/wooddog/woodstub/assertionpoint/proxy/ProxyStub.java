/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

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

    private class StubEventCreator {
        private Method method;
        private Object[] args;
        private Class[] types;

        public StubEventCreator(Method method, Object[] args) {
            this.method = method;
            this.args = args;
            this.types = addTypesFromArguments();
        }

        public InternalStubEvent create() {
            return new InternalStubEvent(getClassName(),
                    getReturnType(),
                    getMethodName(),
                    types, args);
        }

        private Class[] addTypesFromArguments() {
            if (args == null) {
                return null;
            }

            return findTypesFromArguments();
        }

        private Class[] findTypesFromArguments() {
            Class[] types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }

            return types;
        }

        private String getReturnType() {
            return method.getReturnType() == null ? null : method.getReturnType().getCanonicalName();
        }

        private String getMethodName() {
            return method.getName();
        }

        private String getClassName() {
            return method.getDeclaringClass().getCanonicalName();
        }
    }
}
