/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Stubs;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
@RunWith(WoodRunner.class)
@Stubs({Head.class})
public class OverLoadTest {
    /**
     * Tests whether overloaded methods with different return types works.
     *
     * The TempStubBuilder would, if not handled properly will be confused by overloaded
     * methods with different return types as <code>Method#getDeclaredMethods()</code>
     * fetches all method overloaded methods rather than just what actual declared
     * in the class.
     */
    @Test
    public void testOverLoad() {
        Head head;

        head = new Head();

        WoodRunner.addListener(new StubListener() {
            public void invoked(StubEvent event) {
                event.setResult("mocked value");
            }
        });
        Assert.assertEquals("mocked value", head.getValue());
    }
}

class Base {
    public Object getValue() {
        return "native base value";
    }
}

class Head extends Base {
    public String getValue() {
        return "native head value";
    }
}