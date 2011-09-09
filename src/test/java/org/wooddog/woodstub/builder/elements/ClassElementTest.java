/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.DesignTester;

public class ClassElementTest {
    @Test
    public void testGetCode() {
        ClassElement classEl = new ClassElement("MyClass",new String[] {"Serializable"},false,"public","org.wooddog.woodstub.test","","class");

        String result = "package org.wooddog.woodstub.test;\n\n"+
                               "public class MyClass implements Serializable {\n\n"+
                               "}\n";

        Assert.assertEquals(result.trim(),classEl.getCode());
    }

    @Test
    public void testAddChild() {
        ClassElement classEl = new ClassElement();
        try {
            classEl.addChild(new MethodElement(new MethodBodyElement("","",""),
                                                new MethodDefinitionElement("","","",null)));
        } catch (IllegalArgumentException e) {
            Assert.fail("Got exception when adding method:"+e.getMessage());
        }

        try {
            classEl.addChild(new ConstructorElement(new ConstructorDefinitionElement(""), new ConstructorBodyElement("")
            ));
        } catch (IllegalArgumentException e) {
            Assert.fail("Got exception when adding constructor:"+e.getMessage());
        }

         try {
            classEl.addChild(new MemberElement("","",""));
        } catch (IllegalArgumentException e) {
            Assert.fail("Got exception when adding member:"+e.getMessage());
        }
    }

    @Test
    public void testException() {
        try {
            ClassElement classEl = new ClassElement();
            classEl.addChild(new RootElement());
            Assert.fail("Expected an exception");
        } catch (IllegalArgumentException e) {
        }

        try {
            ClassElement classEl = new ClassElement();
            classEl.addChild(new ArgumentElement("",""));
            Assert.fail("Expected an exception");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testDesign() {
        DesignTester.testCodeElementDesign(ClassElement.class);
    }
}