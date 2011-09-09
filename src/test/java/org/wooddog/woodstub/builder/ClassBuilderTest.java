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
import org.wooddog.woodstub.builder.elements.RootElement;
import org.wooddog.woodstub.testmodel.TestClass;

public class ClassBuilderTest {
    @Test
    public void testBuild() {
        ClassBuilder builder = new ClassBuilder(TestClass.class);
        RootElement root = new RootElement();
        CodeElement element = builder.build(root);

        Assert.assertNotNull(element);
        Assert.assertTrue(element instanceof ClassElement);
        Assert.assertEquals(element,root.getLast());
    }

    @Test
    public void testDesign() {
       DesignTester.testBuilderDesign(ClassBuilder.class);
    }
}