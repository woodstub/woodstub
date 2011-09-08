/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.logic;

import org.junit.Assert;
import org.wooddog.woodstub.assertionpoint.AssertionBody;
import org.wooddog.woodstub.assertionpoint.AssertionHead;
import org.wooddog.woodstub.junit.StubEvent;

public class PointHead implements AssertionHead {
    private boolean validate;
    private String methodName;
    private Class expectedType;
    private AssertionBody body;

    public PointHead(Class expectedType,boolean validate) {
        this.expectedType = expectedType;
        this.validate = validate;
        this.body = new PointBody(expectedType);
    }

    @Override
    public AssertionBody toCall(String methodName) {
        this.methodName = methodName;
        return body;
    }

    @Override
    public AssertionBody toCallConstructor() {
        methodName = expectedType.getCanonicalName();
        return body;
    }

    @Override
    public AssertionBody toCallAnyMethod() {
        this.methodName=null;
        return body;
    }

    @Override
    public void cleanResources() {
        methodName=null;
        body.cleanResources();
        body=null;
    }

    @Override
    public void invoked(StubEvent event) {
        if (validate) {
            validateEvent(event);
        }

        if (methodShouldBeHandled(event)) {
            body.invokeBodyAndTail(event);
        }
    }

    private void validateEvent(StubEvent event) {
        Assert.assertEquals("Classname mismatch", expectedType.getCanonicalName(), event.getClassName());
        if (methodName!=null) {
            Assert.assertEquals("Methodname mismatch", methodName, event.getMethodName());
        }
    }

    private boolean methodShouldBeHandled(StubEvent event) {
        return methodName==null || event.getMethodName().equals(methodName);
    }
}
