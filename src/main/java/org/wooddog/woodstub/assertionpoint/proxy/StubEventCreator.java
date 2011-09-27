/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.proxy;

import org.wooddog.woodstub.InternalStubEvent;
import java.lang.reflect.Method;

class StubEventCreator {
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
