/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.junit.Test;
import org.wooddog.woodstub.DesignTester;

/**
 * Created by Asbjørn Andersen
 * <p/>
 * User: denasa
 * Date: 12-08-2010
 * Time: 18:00:21
 */
public class ConstructorBodyElementTest {
    public void testGetCode() {
        
    }

    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(ConstructorBodyElement.class);
    }
}