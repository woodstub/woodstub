/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.demo;

import org.junit.Test;
import org.wooddog.woodstub.assertionpoint.proxy.ProxyCreator;
import org.wooddog.woodstub.junit.WoodTestCase;

public class ProxyTest extends WoodTestCase {
    @Test
    public void testWithProxy() {
        TestInterface inter = ProxyCreator.create(TestInterface.class);
        behaveAs(TestInterface.class).toCall("getStuff").andReturn(1);
        assertEquals(1,inter.getStuff());
    }
}
