/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.junit.Before;
import org.junit.Test;
import org.wooddog.woodstub.assertionpoint.proxy.ProxyCreator;
import static org.junit.Assert.assertTrue;

public class WoodRunnerTest {
    private boolean invoked;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testNotifyInvokedListeners() throws Exception {
        StubListener listener = new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                invoked = true;
            }
        };

        WoodRunner.addListener(listener);
        WoodRunner.notify(ProxyCreator.create(StubEvent.class));
        assertTrue(invoked);
    }
}
