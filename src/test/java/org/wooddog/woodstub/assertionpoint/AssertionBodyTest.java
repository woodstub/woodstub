/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

import org.junit.Test;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.SomeObject;

@Stubs(SomeObject.class)
public class AssertionBodyTest extends WoodTestCase {
    boolean called;

    @Test
    public void testAnyMethod() {
        behaveAs(SomeObject.class).toCallAnyMethod().andReturn(50);
        assertEquals(50, new SomeObject().someMethod("arg"));
        assertEquals(50, new SomeObject().someOtherMethod("arg", "arg"));
    }

    @Test
    public void testConstructorAndOutput() {
        called = false;
        behaveAs(SomeObject.class).toCallConstructor().andOutput("Constructed").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                called = true;
            }
        });

        new SomeObject();
        assertTrue(called);
    }

    @Test
    public void testVerifiedBehavior() {
        expect(SomeObject.class).toCallConstructor();
        expect(SomeObject.class).toCall("someMethod").andReturn(2);
        expect(SomeObject.class).toCall("someOtherMethod").andReturn(3);

        SomeObject obj = new SomeObject();
        assertEquals(2, obj.someMethod("arg"));
        assertEquals(3, obj.someOtherMethod("arg", "arg"));
    }

    @Test(expected = AssertionFailedException.class)
    public void testFailingMethod() {
        behaveAs(SomeObject.class).toCall("someMethod").andFail();
        new SomeObject().someMethod("hej");
    }

    @Test()
    public void testFailingMethodWithMessage() {
        try {
            behaveAs(SomeObject.class).toCall("someMethod").andFail("Test");
            new SomeObject().someMethod("hej");
        } catch (AssertionFailedException e) {
            assertEquals("Test", e.getMessage());
        }
    }
}
