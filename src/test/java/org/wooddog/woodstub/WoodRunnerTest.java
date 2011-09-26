/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.junit.*;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.SomeObject;
import static org.junit.Assert.assertTrue;

@Stubs(SomeObject.class)
@RunWith (WoodRunner.class)
public class WoodRunnerTest {
    private static ClassLoader beforeClassClassLoader;
    private ClassLoader beforeClassLoader;
    private boolean called;

    @After
    public void tearDown() {
        WoodRunner.cleanup();
        beforeClassLoader=null;
   }

    @AfterClass
    public static void afterClass() {
        beforeClassClassLoader=null;
    }

    @BeforeClass
    public static void beforeClass() {
        beforeClassClassLoader = WoodRunnerTest.class.getClassLoader();
    }

    @Before
    public void before() {
        beforeClassLoader = getClass().getClassLoader();
        called=true;
    }

    @Test
    public void testAddListener() {
        SomeListener listener = new SomeListener();
        WoodRunner.addListener(listener);
        new SomeObject();
        assertTrue(called);
    }

    @Test
    public void testRemoveListener() {
        SomeListener listener = new SomeListener();
        WrongListener wrongListener = new WrongListener();
        WoodRunner.addListener(wrongListener);
        WoodRunner.addListener(listener);
        WoodRunner.removeListener(wrongListener);
        called=false;
        new SomeObject();
    }

    @Test
    public void testPreTest() {
        Assert.assertEquals(EndorsedClassLoader.class.getCanonicalName(), beforeClassLoader.getClass().getCanonicalName());
        Assert.assertEquals(EndorsedClassLoader.class.getCanonicalName(), beforeClassClassLoader.getClass().getCanonicalName());
    }

    class SomeListener implements StubListener {

        @Override
        public void invoked(StubEvent event) {
            called = true;
        }
    }

    class WrongListener implements StubListener {

        @Override
        public void invoked(StubEvent event) {
            throw new RuntimeException("Should not happen!");
        }
    }
}
