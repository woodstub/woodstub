/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.DesignTester;
import org.wooddog.woodstub.error.CodeBuilderException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class ArgumentElementTest {
    @Test
    public void testGetCode() {
        MethodDefinitionElement methodEl = new MethodDefinitionElement("", "", "", null);
        ArgumentElement element = new ArgumentElement("first", "FirstType");
        ArgumentElement element2 = new ArgumentElement("second", "SecondType");
        methodEl.addChild(element);
        String firstResult = "FirstType first";
        Assert.assertEquals(firstResult, element.getCode());

        methodEl.addChild(element2);
        String secondResult = "FirstType first, ";
        Assert.assertEquals(secondResult, element.getCode());

        String thirdResult = "SecondType second";
        Assert.assertEquals(thirdResult, element2.getCode());
    }

    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(ArgumentElement.class);
    }

    @Test
    public void testException() {
        try {
            ArgumentElement element = new ArgumentElement("first", "FirstType");
            element.addChild(new ArgumentElement("second", "SecondType"));
            fail("expected an exception");
        } catch (CodeBuilderException e) {
            assertEquals("ArgumentElement cannot have children.", e.getMessage());
        }
    }
}