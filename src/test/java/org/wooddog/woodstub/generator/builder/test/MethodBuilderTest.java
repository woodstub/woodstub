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
import org.wooddog.woodstub.generator.builder.elements.MethodElement;
import org.wooddog.woodstub.generator.builder.methods.MethodBuilder;
import org.wooddog.woodstub.testmodel.TestExceptionClass;
import org.wooddog.woodstub.testmodel.TestMethodClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import static junit.framework.Assert.assertEquals;

public class MethodBuilderTest {
    String expectedCodeWithReturn = "public java.lang.Object returnObject() {\n" +
            "org.wooddog.woodstub.InternalStubEvent call = new org.wooddog.woodstub.InternalStubEvent(\"org.wooddog.woodstub.testmodel.TestMethodClass\", " +
            "\"java.lang.Object\", \"returnObject\", null,null);\n\n" +
            " org.wooddog.woodstub.junit.WoodRunner.notify(call); \n" +
            "if (call.getException() != null) {\n" +
            "    if (call.getException() instanceof RuntimeException) {\n" +
            "         throw (RuntimeException) call.getException();\n" +
            "    }\n" +
            "\n" +
            "    throw new  org.wooddog.woodstub.StubException(call.getException().getClass().getCanonicalName() + \" can't be thrown from org.wooddog.woodstub.testmodel.TestMethodClass.returnObject\");\n" +
            "}\n" +
            "return (java.lang.Object) call.getResult();\n" +
            "\n" +
            "}";
    @Test
    public void testBuild() {
        Method[] methods = TestMethodClass.class.getDeclaredMethods();

            CodeBuilder builder = new MethodBuilder(methods[1]);
            CodeElement element=null;
            CodeElement classElement = new ClassElement();

            try {
                element = builder.build(classElement);
                assertEquals(expectedCodeWithReturn,element.getCode().trim());
            } catch (CodeBuilderException e) {
                Assert.fail("Exception:"+e.getMessage());
            }

            Assert.assertTrue(element instanceof MethodElement);
            Assert.assertEquals(element,classElement.getLast());
    }

    @Test
    public void testException() {
        Constructor[] methods = TestExceptionClass.class.getDeclaredConstructors();
        CodeBuilder builder = new ConstructorBuilder(methods[0]);
        CodeElement element=null;
        CodeElement classElement = new ClassElement();
        boolean exception=false;
        try {
            element = builder.build(classElement);
        } catch (CodeBuilderException e) {
            exception=true;
        }

        Assert.assertTrue("Expected an exception to be thrown",exception);
    }

    @Test
    public void testDesign() {
       DesignTester.testBuilderDesign(MethodBuilder.class);
    }
}