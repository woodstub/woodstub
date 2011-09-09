/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.DesignTester;
import org.wooddog.woodstub.builder.elements.ClassElement;
import org.wooddog.woodstub.builder.elements.CodeElement;
import org.wooddog.woodstub.builder.elements.MemberElement;
import org.wooddog.woodstub.testmodel.TestMethodClass;

import java.lang.reflect.Field;

public class FieldBuilderTest {

    @Test
    public void testBuild() {
        Field[] fields = TestMethodClass.class.getDeclaredFields();
        CodeBuilder builder = new FieldBuilder(fields[0]);
        CodeElement element=null;
        CodeElement classElement = new ClassElement();

        try {
            element = builder.build(classElement);
        } catch (CodeBuilderException e) {
            Assert.fail("Exception:"+e.getMessage());
        }

        Assert.assertTrue(element instanceof MemberElement);
        Assert.assertEquals(element,classElement.getLast());
    }

    @Test
    public void testDesign() {
       DesignTester.testBuilderDesign(FieldBuilder.class);
    }
}