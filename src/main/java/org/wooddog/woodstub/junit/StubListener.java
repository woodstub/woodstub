/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

/**
 * The interface that allows communication with the backend logic.
 * Implementations can be registered with WoodRunner, to be notified of method invocations.
 */
public interface StubListener {
    void invoked(StubEvent event);
}
