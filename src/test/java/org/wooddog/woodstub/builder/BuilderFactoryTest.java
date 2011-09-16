/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.demo.DataBase;
import org.wooddog.demo.SomeSubClass;
import org.wooddog.woodstub.DesignTester;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.TestMethodClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Stubs({DataBase.class, SomeSubClass.class})
public class BuilderFactoryTest extends WoodTestCase {
    
    @Test
    public void testCreation() {
        Class testClass = TestMethodClass.class;
        Assert.assertTrue(new BuilderFactory().createBuilder(testClass) instanceof ClassBuilder);

        Method[] methods = testClass.getDeclaredMethods();
        Assert.assertTrue(new BuilderFactory().createBuilder(methods[0]) instanceof MethodBuilder);

        Field[] fields = testClass.getDeclaredFields();
        Assert.assertTrue(new BuilderFactory().createBuilder(fields[0]) instanceof FieldBuilder);

        Constructor[] constructors = testClass.getDeclaredConstructors();
        Assert.assertTrue(new BuilderFactory().createBuilder(constructors[0]) instanceof ConstructorBuilder);
    }

    @Test
    public void testDesign() {
        DesignTester.testBuilderFactoryDesign(BuilderFactory.class);
    }
}