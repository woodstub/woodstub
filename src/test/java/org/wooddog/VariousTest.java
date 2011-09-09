/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.wooddog.woodstub.Primitive;
import org.wooddog.woodstub.junit.WoodRunner;

import java.lang.reflect.Constructor;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
@Ignore
public class VariousTest {
    @After
    public void tearDown() {
        WoodRunner.cleanup();
    }

    @Test
    public void testParameter() {
        Assert.assertEquals("long", format(long.class));
        Assert.assertEquals("long[]", format(long[].class));
        Assert.assertEquals("java.lang.String", format(String.class));
        Assert.assertEquals("java.lang.String[]", format(String[].class));
    }

    @Test
    public void testReturn() throws Exception {
        Assert.assertEquals("java.lang.Long", Primitive.getWrapper(long.class).getCanonicalName());
        Assert.assertEquals("java.lang.Long[]", Primitive.getWrapper(long[].class).getCanonicalName());
        Assert.assertEquals("java.lang.String", String.class.getCanonicalName());
        Assert.assertEquals("java.lang.String[]", String[].class.getCanonicalName());

        Constructor constructor = getClass().getConstructor();
        constructor.setAccessible(true);


    }

    public String format(Class clazz) {
        return (clazz.getCanonicalName());
    }


    @Test
    public void testThrowA() {
        try {
            trowA();
        } catch (TestException x) {
            x.printStackTrace();
        }

    }

    public void trowA() throws TestException {
        try {
            raise();
        } catch (TestException x) {
            throw new TestException("2", x);
        }
    }

    public void raise() throws TestException {
        throw new TestException("1");
    }

    @Test
    public void testPrimitive() {
        int i = 10;
        Object[] params = new Object[1];

        params[0] = i;


        Assert.assertEquals(10, ((Integer) params[0]).intValue());
    }
}

@Ignore
class TestException extends Exception {
    public TestException() {
        super();
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(Throwable cause) {
        super(cause);
    }
}