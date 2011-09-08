/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

import org.wooddog.woodstub.junit.StubEvent;

public interface AssertionTail extends Cleanable {
    void invokeTail(StubEvent event);
}
