/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.*;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.EndorsedClassLoader;
import org.wooddog.woodstub.junit.WoodRunner;

@RunWith (WoodRunner.class)
public class WoodRunnerTest {
    private static ClassLoader beforeClassClassLoader;
    private ClassLoader beforeClassLoader;

    @After
    public void tearDown() {
        WoodRunner.cleanup();

        beforeClassLoader=null;
   }

    @AfterClass
    public static void afterClass() {
        beforeClassClassLoader=null;
    }

    @Test
    public void testA() {
        Assert.assertTrue(true);
    }

    @Test
    public void testPreTest() {
        Assert.assertEquals(EndorsedClassLoader.class.getCanonicalName(), beforeClassLoader.getClass().getCanonicalName());
        Assert.assertEquals(EndorsedClassLoader.class.getCanonicalName(), beforeClassClassLoader.getClass().getCanonicalName());
    }

    @BeforeClass
    public static void beforeClass() {
        beforeClassClassLoader = WoodRunnerTest.class.getClassLoader();
    }

    @Before
    public void before() {
        beforeClassLoader = getClass().getClassLoader();
    }
}
