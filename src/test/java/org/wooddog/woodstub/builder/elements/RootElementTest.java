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

public class RootElementTest {
    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(MethodDefinitionElement.class);
    }

    @Test
    public void testThatMethodElementCannotBeAddedToRoot() throws Exception {
        try {
            new RootElement().addChild(new MethodDefinitionElement("","","",new Class[0]));
            fail("Expected an exception");
        }catch (IllegalArgumentException e) {
            assertEquals("Only ClassElement or EnumElement can be added to root!",e.getMessage());
        }
    }
}