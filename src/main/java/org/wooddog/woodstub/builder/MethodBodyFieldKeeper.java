/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MethodBodyFieldKeeper {
    private String className;
    private String returnType;
    private boolean primitive;
    private String methodName;
    private Class[] exceptionTypes;
    private String ARG_PREFIX="arg";

    MethodBodyFieldKeeper(Method method) {
        className=method.getDeclaringClass().getName().replace('$', '.');
        returnType=method.getReturnType().getCanonicalName();
        primitive=method.getReturnType().isPrimitive();
        methodName=method.getName();
        exceptionTypes = method.getExceptionTypes();
    }

    MethodBodyFieldKeeper(Constructor method) {
        className=method.getDeclaringClass().getName().replace('$', '.');
        returnType=null;
        primitive=false;
        methodName=method.getName();
        exceptionTypes = method.getExceptionTypes();
    }

    public String getReturnType() {
        return returnType;
    }

    public String getClassName() {
        return className;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getExceptionTypes() {
        return exceptionTypes;
    }

    public String getArgumentPrefix() {
        return ARG_PREFIX;
    }
}
