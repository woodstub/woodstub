/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

import org.junit.Test;
import org.wooddog.woodstub.DesignTester;
import org.wooddog.woodstub.error.CodeBuilderException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class MethodDefinitionElementTest {
    @Test
    public void testException() {
        try {
        MethodDefinitionElement element = new MethodDefinitionElement("method","public","void",null);
        element.addChild(new MethodElement(null,null));
            fail("Expected an exception");
        } catch (CodeBuilderException e) {
            assertEquals("Element must be an ArgumentElement",e.getMessage());
        }
    }

    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(MethodDefinitionElement.class);
    }
}