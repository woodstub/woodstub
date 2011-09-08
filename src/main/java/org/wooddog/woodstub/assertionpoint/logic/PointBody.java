/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.assertionpoint.AssertionBody;
import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;

class PointBody implements AssertionBody {
    private AssertionTail tail;
    private String message;
    private Class expectedType;

    public PointBody(Class expectedType) {
        this.expectedType = expectedType;
    }

    @Override
    public AssertionBody andOutput(String message) {
        this.message = message;
        return this;
    }

    @Override
    public AssertionTail andDelegateTo(StubListener callback) {
        tail = new DelegateTail(callback);
        return tail;
    }

    @Override
    public AssertionTail andDelegateTo(Class callbackClass) {
        tail = new ClassDelegateTail(callbackClass);
        return tail;
    }

    @Override
    public AssertionTail andReturn(Object value) {
        tail = new ValueTail(value);
        return tail;
    }

    @Override
    public AssertionTail andThrow(Throwable exception) {
        tail = new ExceptionTail(exception);
        return tail;
    }

    @Override
    public AssertionTail andReturnNewInstance() {
        tail = new CreatorTail(expectedType);
        return tail;
    }

    @Override
    public AssertionTail andSendToRealClass() {
        tail = new RedirectTail();
        return tail;
    }

    @Override
    public void cleanResources() {
        if (tail!=null) {
            tail.cleanResources();
        }

        tail=null;
    }

    @Override
    public void invokeBodyAndTail(StubEvent event) {
        if (message != null) {
            outputMessage();
        }

        if (tail != null) {
            tail.invokeTail(event);
        }
    }

    private void outputMessage() {
        System.out.println("WoodLog[" + expectedType.getName() + "] - " + message);
    }

}
