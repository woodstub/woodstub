/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.StubException;
import org.wooddog.woodstub.junit.JUnitWoodRunner;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Stubs;

import java.io.IOException;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
@RunWith(JUnitWoodRunner.class)
@Stubs(DummyClass.class)
public class ExceptionTest {
    @After
    public void tearDown() {
        WoodRunner.cleanup();
   }

    /**
     * Tests raising a checked exception within a stubbed method
     */
    @Test
    public void testMethodCheckedException() {
        DummyClass dummyClass = new DummyClass();

        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                event.raiseException(new IOException("Hello World"));
            }
        });

        try {
            dummyClass.checked();
        } catch (IOException x) {
            return;
        }

        Assert.fail("no IOException thrown");
    }

    /**
     * Tests raising a runtime exception within a stubbed method
     */
    @Test
    public void testMethodRuntimeException() {
        DummyClass dummyClass;

        dummyClass = new DummyClass();

        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                event.raiseException(new NullPointerException("Hello World"));
            }
        });

        try {
            dummyClass.unchecked();
        } catch (NullPointerException x) {
            return;
        }

        Assert.fail("no NullPointerException thrown");
    }

    /**
     * Tests raising a runtime exception within a stubbed method
     */
    @Test
    public void testMethodUnThrowableException() throws IOException {
        DummyClass dummyClass;

        dummyClass = new DummyClass();

        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                event.raiseException(new IOException("Hello World"));
            }
        });

        try {
            dummyClass.unchecked();
        } catch (StubException x) {
            return;
        }

        Assert.fail("not failed for unthrowable exception");
    }

    /**
     * Tests raising a checked exception within a stubbed method
     */
    @Test
    public void testConstructorCheckedException() {
        DummyClass dummyClass;

        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                event.raiseException(new IOException("Hello World"));
            }
        });

        try {
            dummyClass = new DummyClass(null);
        } catch (IOException x) {
            return;
        }

        Assert.fail("no IOException thrown");
    }

    /**
     * Tests raising a runtime exception within a stubbed method
     */
    @Test
    public void testConstructorRuntimeException() {
        DummyClass dummyClass;

        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                event.raiseException(new NullPointerException("Hello World"));
            }
        });

        try {
            dummyClass = new DummyClass();
        } catch (NullPointerException x) {
            return;
        }

        Assert.fail("no NullPointerException thrown");
    }

    /**
     * Tests raising a runtime exception within a stubbed method
     */
    @Test
    public void testConstructorUnThrowableException() throws IOException {
        DummyClass dummyClass;


        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                event.raiseException(new IOException("Hello World"));
            }
        });       
               
        try {
            dummyClass = new DummyClass();
        } catch (StubException x) {
            return;
        }

        Assert.fail("not failed for unthrowable exception");
    }

}

class DummyClass {
    public DummyClass() {
    }

    public DummyClass(IOException exception) throws IOException {
    }

    public void checked() throws IOException {
    }

    public void unchecked() {
    }
}