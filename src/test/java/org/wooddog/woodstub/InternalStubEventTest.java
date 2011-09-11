/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.junit.Test;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.testmodel.SomeObject;

public class InternalStubEventTest extends WoodTestCase{
    @Test
    public void testInt() {
        behaveAs(SomeObject.class).toCall("intMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                int i= event.getInt(1);
                assertEquals(1,i);
            }
        });

        new SomeObject().intMethod(1);
    }

    @Test
    public void testLong() {
        behaveAs(SomeObject.class).toCall("longMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                long i= event.getLong(1);
                assertEquals(1,i);
            }
        });

        new SomeObject().longMethod(1);
    }

    @Test
    public void testByte() {
        behaveAs(SomeObject.class).toCall("byteMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                byte i= event.getByte(1);
                assertEquals(1,i);
            }
        });

        new SomeObject().byteMethod((byte) 1);
    }

    @Test
    public void testShort() {
        behaveAs(SomeObject.class).toCall("shortMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                short i= event.getShort(1);
                assertEquals(1,i);
            }
        });

        new SomeObject().shortMethod((short) 1);
    }

    @Test
    public void testFloat() {
        behaveAs(SomeObject.class).toCall("floatMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                float i= event.getFloat(1);
                assertEquals(1,i);
            }
        });

        new SomeObject().floatMethod(1);
    }

    @Test
    public void testDouble() {
        behaveAs(SomeObject.class).toCall("doubleMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                double i = event.getDouble(1);
                assertEquals(1,i);
            }
        });

        new SomeObject().doubleMethod(1);
    }

    @Test
    public void testChar() {
        behaveAs(SomeObject.class).toCall("charMethod").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                char c = event.getChar(1);
                assertEquals('c',c);
            }
        });

        new SomeObject().charMethod('c');
    }
}
