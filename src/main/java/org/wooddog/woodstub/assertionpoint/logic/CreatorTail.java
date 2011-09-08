/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.StubHelper;
import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;

class CreatorTail implements AssertionTail {
    private Class expectedType;

    public CreatorTail(Class expectedType) {
        this.expectedType = expectedType;
    }

    @Override
    public void invokeTail(StubEvent event) {
        event.setResult(StubHelper.newInstance(expectedType));
    }

    @Override
    public void cleanResources() {
        expectedType = null;
    }
}
