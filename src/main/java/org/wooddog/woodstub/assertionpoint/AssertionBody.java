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
    /**
     * Output a message in System Out when invoked.
     */
    public AssertionBody andOutput(String message);

    /**
     * Delegate to a StubListener
     */
    public AssertionTail andDelegateTo(StubListener callback);

    /**
     * Delegate to an uninitialized StubListener
     */
    public AssertionTail andDelegateTo(Class callbackClass);

    /**
     * Return a specific value.
     */
    public AssertionTail andReturn(Object value);

    /**
     * Throw a specific exception when invoked.
     */
    public AssertionTail andThrow(Throwable exception);

    /**
     * Fail with exception when invoked
     * @throws AssertionFailedException
     */
    public AssertionTail andFail();

    /**
     * Fail with exception with custom message when invoked
     * @throws AssertionFailedException
     */
    public AssertionTail andFail(String message);

    /**
     * Return a new instance of the stubbed class.
     */
    public AssertionTail andReturnNewInstance();

    /**
     * Invoke method on un-stubbed class.
     */
    public AssertionTail andSendToRealClass();

    /**
     * Do not use. For internal logic only.
     */
    void invokeBodyAndTail(StubEvent event);
}
