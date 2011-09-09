/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.testmodel;

import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;

public class SomeListenerClass implements StubListener{
    @Override
    public void invoked(StubEvent event) {
        event.setResult(100);
    }
}
