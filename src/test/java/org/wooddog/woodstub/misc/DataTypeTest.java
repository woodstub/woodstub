/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Stubs;

/**
 * Purpose of test:
 *
 *  Check parameter values can be passed from stub to listener.
 *
 *  Check return value can be passed from listener to stub.
 *
 *  Check primitive values are passed correctly, keeping the type of class during passing (not ending up as a wrapper
 *  class)
 *
 *  Check Arrays are passed correctly with content of either primitives or Objects.
 *
 *  Check primitives do not cause cast exceptions when no return value is set in the listener.
 *
 */
@RunWith(WoodRunner.class)
@Stubs(DataType.class)
public class DataTypeTest {
    private int calls;

    @After
    public void tearDown() {
        WoodRunner.cleanup();
   }

    /**
     * Passing primitive int value from and to the stub.
     */
    @Test
    public void testPrimitiveInt() {
        DataType dataType;

        dataType = new DataType();

        WoodRunner.addListener(new StubListener() {
            public void invoked(StubEvent event) {
                if (event.getMethodName().equals("doInt")) {
                    Assert.assertEquals(Integer.MAX_VALUE, event.getInt(1));
                    event.setResult(Integer.MIN_VALUE);
                }
            }
        });

        Assert.assertEquals(Integer.MIN_VALUE, dataType.doInt(Integer.MAX_VALUE));
    }

    /**
     * Passing primitive int array from and to the stub.
     */
    @Test
    public void testPrimitiveIntArray() {
        DataType dataType;

        dataType = new DataType();

        WoodRunner.addListener(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertArrayEquals(new int[]{3, 4}, (int[]) event.getObject(1));
                event.setResult(new int[]{1, 2});
            }
        });

        Assert.assertArrayEquals(new int[]{1, 2}, dataType.doIntArray(new int[]{3, 4}));
    }


    /**
     * Passing object value from and to the stub.
     */
    @Test
    public void testString() {
        DataType dataType;

        dataType = new DataType();

        WoodRunner.addListener(new StubListener() {
            public void invoked(StubEvent event) {
                if (event.getMethodName().equals("doString")) {
                    Assert.assertEquals("MY OBJECT", event.getObject(1));
                    event.setResult("my object");
                }
            }
        });

        Assert.assertEquals("my object", dataType.doString("MY OBJECT"));
    }

    /**
     * Passing Object array from and to the stub.
     */
    @Test
    public void testStringArray() {
        DataType dataType;

        dataType = new DataType();

        WoodRunner.addListener(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertArrayEquals(new String[]{"3", "4"}, (String[]) event.getObject(1));
                event.setResult(new String[]{"1", "2"});
            }
        });

        Assert.assertArrayEquals(new String[]{"1", "2"}, dataType.doStringArray(new String[]{"3", "4"}));
    }

    /**
     * Checking primitives do not throw cast exceptions when unset.
     */
    @Test
    public void testPrimitiveUnset() {
        DataType dataType;

        dataType = new DataType();

        Assert.assertEquals(false, dataType.doBoolean(true));
        Assert.assertEquals((char) 0, dataType.doChar((char) '1'));
        Assert.assertEquals((byte) 0, dataType.doByte((byte) 1));
        Assert.assertEquals((short) 0, dataType.doShort((short) 1));
        Assert.assertEquals((int) 0, dataType.doInt((int) 1));
        Assert.assertEquals((long) 0, dataType.doLong((long) 1));
        Assert.assertTrue(dataType.doFloat((float) 1) == 0);
        Assert.assertTrue(dataType.doDouble((double) 1) == 0);
        Assert.assertEquals(null, dataType.doString("1"));
        Assert.assertArrayEquals(null, dataType.doIntArray(new int[]{}));
        Assert.assertArrayEquals(null, dataType.doStringArray(new String[]{}));
    }
}

class DataType {
    public boolean doBoolean(boolean b) {
        return true;
    }

    public char doChar(char c) {
        return 'c';
    }

    public byte doByte(byte b) {
        return 'b';
    }

    public short doShort(short s) {
        return 1;
    }

    public int doInt(int i) {
        return 2;
    }

    public long doLong(long l) {
        return 3;
    }

    public float doFloat(float f) {
        return 4;
    }

    public double doDouble(double d) {
        return 5;
    }

    public String doString(String string) {
        return "S";
    }

    public String[] doStringArray(String[] values) {
        return new String[]{"s1", "s2"};
    }

    public int[] doIntArray(int[] values) {
        return new int[]{1, 2};
    }

    public org.wooddog.woodstub.misc.DataType doInstance(org.wooddog.woodstub.misc.DataType dataType) {
        return new org.wooddog.woodstub.misc.DataType();
    }
}