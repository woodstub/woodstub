/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.junit.Assert;
import org.wooddog.woodstub.generator.builder.elements.CodeElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DesignTester {
    public static void testBuilderDesign(Class builder) {
        Class[] interfaces = builder.getInterfaces();
        if (interfaces.length==0) {
            Assert.fail("Builder classes must implement the CodeBuilder interface.");
        }

        boolean found=false;
        for (Class c : interfaces) {
            if (c.getCanonicalName().endsWith("CodeBuilder")) {
                found = true;
                break;
            }
        }

        Assert.assertTrue("Builder classes must implement the CodeBuilder interface.",found);

        if (interfaces.length>1) {
            Assert.fail("Builder classes must not implement more than the CodeBuilder interface.");
        }

        Class superClass = builder.getSuperclass();
        if (!superClass.getCanonicalName().endsWith(".Object")) {
            Assert.fail("Builder classes must not extend from classes. In this case["+superClass.getCanonicalName()+"]");
        }

         for (Method met : builder.getDeclaredMethods()) {
            if (isPublic(met) && met.getName().startsWith("set")) {
                Assert.fail("Builder classes must not have setters. ["+met.getName()+"]");
            }

            if (isPublic(met) && met.getName().startsWith("get")) {
                Assert.fail("Builder classes must not have getters. ["+met.getName()+"])");
            }

             if (Modifier.isStatic(met.getModifiers()) && isPublic(met)) {
                Assert.fail("Builder classes must not public static methods.["+met.getName()+"])");
            }
         }

    }

    public static void testBuilderFactoryDesign(Class factory) {
        Class superClass = factory.getSuperclass();
        if (!superClass.getCanonicalName().endsWith(".Object") &&
            !superClass.getCanonicalName().endsWith(".BuilderFactory")) {
            Assert.fail("Builder Factory must not extend other classes, like["+superClass.getCanonicalName()+"]");
        }

        if (factory.getInterfaces().length>0) {
            Assert.fail("Builder Factory must not implement interfaces.");
        }

        for (Method met : factory.getDeclaredMethods()) {
            if (isPublic(met) && met.getName().startsWith("set")) {
                Assert.fail("Builder Factory must not have setters.");
            }

            if (isPublic(met) && met.getName().startsWith("get")) {
                Assert.fail("Builder Factory must not have getters.");
            }

            if (isPublic(met) && isNotALegalName(met)) {
                Assert.fail("Builder Factory must not have public methods beside createBuilder, like["+met.getName()+"]");
            }
        }
    }

    private static boolean isPublic(Method met) {
        return Modifier.isPublic(met.getModifiers());
    }

    private static boolean isNotALegalName(Method met) {
        return !met.getName().equals("createBuilder") && !met.getName().equals("cleanup")
                && !met.getName().equals("createDefaultConstructorBuilder");
    }

    public static void testCodeElementDesign(Class element) {
        int steps=0;
        Class superClass = element.getSuperclass();

        if (element.getInterfaces().length>0) {
            Assert.fail("CodeElement classes must not implement interfaces");
        }

        while (!superClass.getCanonicalName().equals(CodeElement.class.getCanonicalName())) {
            steps++;    
            superClass = superClass.getSuperclass();
                if (superClass == null) {
                    Assert.fail("CodeElement classes must inherit from CodeElement");
                }
        }

        Assert.assertTrue("CodeElement classes must inherit from CodeElement with max 2 degrees of separation.",steps<3);

        if (steps ==2) {
            Assert.assertTrue("CodeElement classes, at 2 degrees of separation from CodeElement, must be final", Modifier.isFinal(element.getModifiers()));
        }


        for (Method method : element.getDeclaredMethods()) {
            if (isPublic(method)) {
                if (method.getName().equals("getCode")) {
                    if (method.getParameterTypes().length>0) {
                        Assert.fail("CodeElement classes must implement getCode() with no parameters.");
                    }
                } else  if (method.getName().equals("addChild")) {
                    if (method.getParameterTypes().length!=1) {
                        Assert.fail("CodeElement classes must implement getCode() with only 1 parameter.");
                    }
                    Assert.assertEquals("CodeElement classes must not overload addChild",CodeElement.class,method.getParameterTypes()[0]);
                } else {
                    Assert.fail("CodeElement classes must not have any other public methods than addChild and getCode.");
                }
            }
        }
    }
}