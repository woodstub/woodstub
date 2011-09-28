/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

import org.junit.Test;
import org.wooddog.woodstub.DesignTester;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class EnumElementTest {
    @Test
    public void testThatMethodBodyElementCannotBeAddedToEnum() throws Exception {
        try {
            new EnumElement("name","modifier","blah",new String[0]).addChild(new MethodBodyElement("","",""));
            fail("Expected an exception");
        }catch (IllegalArgumentException e) {
            assertEquals("Element had an invalid type:class org.wooddog.woodstub.generator.builder.elements.MethodBodyElement",e.getMessage());
        }
    }

    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(EnumElement.class);
    }
}
