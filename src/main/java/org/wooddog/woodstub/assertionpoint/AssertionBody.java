/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;

public interface AssertionBody extends Cleanable{
    public AssertionBody andOutput(String message);
    public AssertionTail andDelegateTo(StubListener callback);
    public AssertionTail andDelegateTo(Class callbackClass);
    public AssertionTail andReturn(Object value);
    public AssertionTail andThrow(Throwable exception);
    public AssertionTail andReturnNewInstance();
    public AssertionTail andSendToRealClass();
    void invokeBodyAndTail(StubEvent event);
}
