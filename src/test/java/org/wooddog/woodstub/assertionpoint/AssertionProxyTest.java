/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

import org.junit.Test;
import org.wooddog.woodstub.assertionpoint.proxy.ProxyException;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.testmodel.ProxyTestInterface;
import org.wooddog.woodstub.testmodel.SomeObject;

public class AssertionProxyTest extends WoodTestCase {

    @Test
    public void testInterfaceConstruction() {
        ProxyTestInterface test =  stub(ProxyTestInterface.class);
        assertNotNull(test);
    }

    @Test
    public void testFailingConstruction() {
        try {
        SomeObject test =  stub(SomeObject.class);
        fail("Expected exception");
        } catch (ProxyException e) {
            assertEquals("Proxies can only be created from an Interface",e.getMessage());
        }
    }

    @Test
    public void testReturnValue() {
        ProxyTestInterface test =  stub(ProxyTestInterface.class);
        behaveAs(ProxyTestInterface.class).toCall("testMethod").andReturn(1);
        assertEquals(1,test.testMethod("arg"));
    }

    @Test
    public void testException() {
        ProxyTestInterface test =  stub(ProxyTestInterface.class);
        behaveAs(ProxyTestInterface.class).toCall("testMethod").andThrow(new RuntimeException("Test"));
        try {
            test.testMethod("arg");
            fail("Expected exception");
        } catch (RuntimeException e) {
            assertEquals("Test",e.getMessage());
        }
    }
}
