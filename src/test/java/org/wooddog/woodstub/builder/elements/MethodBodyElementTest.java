/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.junit.Test;
import org.wooddog.woodstub.DesignTester;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class MethodBodyElementTest {
    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(MethodBodyElement.class);
    }

    @Test
    public void testThatMethodBodyCannotHaveChildren() throws Exception {
        try {
            new MethodBodyElement("","","").addChild(new RootElement());
            fail("Expected an exception");
        }catch (IllegalArgumentException e) {
            assertEquals("MethodBodyElement cannot contain children!",e.getMessage());
        }
    }
}