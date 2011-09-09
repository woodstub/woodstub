/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

import org.junit.Before;
import org.junit.Test;
import org.wooddog.woodstub.StubException;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.SomeListenerClass;
import org.wooddog.woodstub.testmodel.SomeObject;
import org.wooddog.woodstub.testmodel.TestProxyClass;
import org.wooddog.woodstub.testmodel.TestSingletonClass;

@Stubs({SomeObject.class,TestSingletonClass.class,TestProxyClass.class})
public class AssertionTailTest extends WoodTestCase {

    private SomeObject someObject;

    @Before
    public void setUp() {
        someObject = new SomeObject();
    }

    @Test
    public void testReturnValue() {
        behaveAs(SomeObject.class).toCall("someMethod").andReturn(1);
        assertEquals(1, someObject.someMethod("arg"));
    }

    @Test
    public void testDelegate() {
        behaveAs(SomeObject.class).toCall("someMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                String arg = (String) event.getObject(1);
                int result = Integer.parseInt(arg);
                event.setResult(result);
            }
        });

        assertEquals(100,someObject.someMethod("100"));
    }

    @Test
    public void testDelegateToClass() {
        behaveAs(SomeObject.class).toCall("someMethod").andDelegateTo(SomeListenerClass.class);
        assertEquals(100,someObject.someMethod("arg"));
    }

    @Test
    public void testDelegateToWrongClass() {
        try {
            behaveAs(SomeObject.class).toCall("someMethod").andDelegateTo(SomeObject.class);
            fail("Expected an exception");
        } catch (StubException e) {
            assertEquals("Cannot delegate to class[org.wooddog.woodstub.testmodel.SomeObject] since it does not implement StubListener",e.getMessage());
        }
    }

    @Test
    public void testException() {
        behaveAs(SomeObject.class).toCall("someMethod").andThrow(new RuntimeException("Test"));
        try {
            new SomeObject().someMethod("arg");
            fail("Expected exception");
        } catch (RuntimeException e) {
            assertEquals("Test",e.getMessage());
        }
    }

    @Test
    public void testNewInstance() {
        behaveAs(TestSingletonClass.class).toCall("getInstance").andReturnNewInstance();
        TestSingletonClass singleton = TestSingletonClass.getInstance();
        assertNotNull(singleton);
    }

    @Test
    public void testSendToReal() {
        behaveAs(TestProxyClass.class).toCall("getSomeString").andSendToRealClass();
        String str = new TestProxyClass().getSomeString();
        assertEquals("Some string",str);
    }
}
