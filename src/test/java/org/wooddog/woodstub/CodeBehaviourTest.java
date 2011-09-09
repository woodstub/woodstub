/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.*;

/**
 * The purpose of this test is to verify the default behaviour of stubs.
 * The following is verified:
 * numerical values default to 0
 * boolean defaults to false
 * Objects default to null, including in getInstance.
 * StubHelper can be used to create instances with private default constructors
 * The usage of AssertionPoint (through protected methods) to create instance and invoke exceptions
 */
@Stubs({TestSingletonClass.class, TestClass.class})
public class CodeBehaviourTest extends WoodTestCase {
    private static TestSingletonClass instance;

    @Test
    public void testSingleton() {
        Assert.assertNull(TestSingletonClass.getInstance());
        instance = StubHelper.newInstance(TestSingletonClass.class);
        Assert.assertNotNull(instance);
    }

    @Test
    public void testDefaultValues() {
        instance = StubHelper.newInstance(TestSingletonClass.class);
        Assert.assertEquals(0, instance.getMyInt());
        Assert.assertEquals(0L, instance.getMyLong());
        Assert.assertEquals(false, instance.isMyBool());
        Assert.assertTrue(0.0 == instance.getMyDouble());
        Assert.assertNull(instance.getMyString());
    }

    @Test
    public void testAssertPoint() {
        behaveAs(TestSingletonClass.class).toCall("getInstance").andReturnNewInstance();
        behaveAs(TestSingletonClass.class).toCall("getMyInt").andReturn(1);
        Assert.assertEquals(1, TestSingletonClass.getInstance().getMyInt());
    }

    @Test
    public void testException() {
        behaveAs(TestSingletonClass.class).toCall("getInstance").andReturnNewInstance();
        behaveAs(TestSingletonClass.class).toCall("raiseException").andThrow(new InterruptedException("My Exception"));

        try {
            TestSingletonClass.getInstance().raiseException();
            fail("Expected exception");
        } catch (InterruptedException e) {
            assertEquals("My Exception", e.getMessage());
        }
    }

    @Test
    public void testFlow() {
        expect(TestSingletonClass.class).toCall("getInstance").andReturnNewInstance();
        expect(TestSingletonClass.class).toCallConstructor();
        expect(TestSingletonClass.class).toCall("getMyInt").andReturn(1);
        expect(TestSingletonClass.class).toCall("getMyString").andReturn("Hello World");

        TestSingletonClass single = TestSingletonClass.getInstance();
        Assert.assertEquals(1, single.getMyInt());
        Assert.assertEquals("Hello World", single.getMyString());
    }

    @Test
    public void testAssertionFlow() {
        expect(TestSingletonClass.class).toCall("getInstance").andReturnNewInstance();
        expect(TestSingletonClass.class).toCallConstructor();
        expect(TestSingletonClass.class).toCall("getMyInt").andReturn(1);
        expect(TestSingletonClass.class).toCall("getMyString").andReturn("Hello World");

        TestSingletonClass single = TestSingletonClass.getInstance();
        Assert.assertEquals(1, single.getMyInt());
        Assert.assertEquals("Hello World", single.getMyString());
    }

    /**
     * Shows an example of the proxy-functionality working
     */
    @Test
    public void testProxy() {
        behaveAs(TestProxyClass.class).toCall("doOutput").andSendToRealClass();
        TestProxyClass tc = new TestProxyClass();
        tc.doOutput();
    }

    /**
     * Shows an example of the proxy-functionality working with a parameter
     */
    @Test
    public void testProxyWithParameter() {
        behaveAs(TestProxyClass.class).toCall("doDifferentOutput").andSendToRealClass();
        TestProxyClass tc = new TestProxyClass();
        tc.doDifferentOutput(new SomeObject());
    }

    @Test
    public void testProxyWithAdvancedParameter() {
        try {
            behaveAs(TestProxyClass.class).toCall("doFailingOutput").andSendToRealClass();
            TestProxyClass tc = new TestProxyClass();
            tc.doFailingOutput(TestFactoryClass.create("hej"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testProxyWithLocalConstruct() {
        try {
            behaveAs(TestProxyClass.class).toCall("doLocalConstruction").andSendToRealClass();
            TestProxyClass tc = new TestProxyClass();
            LocalConstructed lc = tc.doLocalConstruction();
            assertNotNull(lc);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInterfaceStub() {
        behaveAs(ProxyTestInterface.class).toCall("testMethod").andReturn(35);
        ProxyTestInterface test = stub(ProxyTestInterface.class);
        assertEquals(35,test.testMethod("hej"));

    }
}