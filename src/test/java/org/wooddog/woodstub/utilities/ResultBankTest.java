/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.SomeObject;
import org.wooddog.woodstub.testmodel.ValueClass;

@Stubs(SomeObject.class)
public class ResultBankTest extends WoodTestCase {

    private ResultBank bank;

    @Before
    public void setUp() {
        bank = new ResultBank();
        behaveAs(SomeObject.class).toCallAnyMethod().andDelegateTo(bank);
    }

    @After
    public void tearDown() {
        bank.clearAll();
    }

    @Test
    public void testInt() {
        bank.add(1);
        bank.add(2);
        assertEquals(1, new SomeObject().returnIntMethod());
        assertEquals(2, new SomeObject().returnIntMethod());
    }

    @Test
    public void testLazyInt() {
        bank.add(1);
        assertEquals(1, new SomeObject().returnIntMethod());
        bank.add(2);
        assertEquals(2, new SomeObject().returnIntMethod());
    }

    @Test
    public void testCircularInt() {
        bank.add(1);
        assertEquals(1, new SomeObject().returnIntMethod());
        bank.add(2);
        assertEquals(2, new SomeObject().returnIntMethod());
        assertEquals(1, new SomeObject().returnIntMethod());
    }

    @Test
    public void testDouble() {
        bank.add(1.1);
        bank.add(1.2);
        assertEquals(1.1, new SomeObject().returnDoubleMethod());
        assertEquals(1.2, new SomeObject().returnDoubleMethod());
    }

    @Test
    public void testFloat() {
        bank.add(1.0f);
        bank.add(2.0f);
        assertEquals(1.0f, new SomeObject().returnFloatMethod());
        assertEquals(2.0f, new SomeObject().returnFloatMethod());
    }

    @Test
    public void testString() {
        bank.add("Hej");
        bank.add("Hej2");
        assertEquals("Hej", new SomeObject().returnStringMethod());
        assertEquals("Hej2", new SomeObject().returnStringMethod());
    }

    @Test
    public void testObject() {
        bank.add(new ValueClass(1));
        bank.add(new ValueClass(2));
        assertEquals(1, new SomeObject().returnValueObject().getValue());
        assertEquals(2, new SomeObject().returnValueObject().getValue());
    }
}
