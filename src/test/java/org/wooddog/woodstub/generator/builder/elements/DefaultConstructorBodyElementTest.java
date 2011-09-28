/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

import org.junit.Test;
import org.wooddog.woodstub.DesignTester;

public class DefaultConstructorBodyElementTest {
    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(DefaultConstructorBodyElement.class);
    }
}