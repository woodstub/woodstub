/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.test;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.DesignTester;
import org.wooddog.woodstub.error.CodeBuilderException;
import org.wooddog.woodstub.generator.builder.CodeBuilder;
import org.wooddog.woodstub.generator.builder.constructor.ConstructorBuilder;
import org.wooddog.woodstub.generator.builder.elements.ClassElement;
import org.wooddog.woodstub.generator.builder.elements.CodeElement;
import org.wooddog.woodstub.generator.builder.elements.ConstructorElement;
import org.wooddog.woodstub.testmodel.TestClass;
import java.lang.reflect.Constructor;

public class ConstructorBuilderTest {

    @Test
    public void testBuild() {
         Constructor[] constructors = TestClass.class.getDeclaredConstructors();
        CodeBuilder builder = new ConstructorBuilder(constructors[0]);
        CodeElement element=null;
        CodeElement classElement = new ClassElement();

        try {
            element = builder.build(classElement);
        } catch (CodeBuilderException e) {
            Assert.fail("Exception:"+e.getMessage());
        }

        Assert.assertTrue(element instanceof ConstructorElement);
        Assert.assertEquals(element,classElement.getLast());
    }

    @Test
    public void testDesign() {
       DesignTester.testBuilderDesign(ConstructorBuilder.class);
    }
}