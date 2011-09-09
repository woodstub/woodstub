/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.issues;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.TestClass;

/**
 * Demonstrated an issue with InternalStubEvent.getInt(...) which is now fixed.
 */
@Stubs(TestClass.class)
public class TestGetIntIssue extends WoodTestCase {
    private static final Logger LOGGER = Logger.getLogger(TestGetIntIssue.class);

    @Test
    public void testGetInt() {
        behaveAs(TestClass.class).toCall("setAnInt").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                int i = event.getInt(1);
                assertEquals(i,1);
            }
        });

        behaveAs(TestClass.class).toCall("setAnotherInt").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                int i = event.getInt(1);
                assertEquals(i,2);
            }
        });

        TestClass tc = new TestClass(1);
        tc.setAnInt(1);
        tc.setAnotherInt(2);
    }
}