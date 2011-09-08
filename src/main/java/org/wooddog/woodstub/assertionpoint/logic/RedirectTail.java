/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.logic;

import org.wooddog.woodstub.assertionpoint.AssertionTail;
import org.wooddog.woodstub.junit.StubEvent;

class RedirectTail implements AssertionTail {
    @Override
    public void invokeTail(StubEvent event) {
        event.setRedirectToRealClass(true);
    }

    @Override
    public void cleanResources() {
        //Nothing to do
    }
}
