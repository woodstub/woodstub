/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.JUnitWoodRunner;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Stubs;

/**
 * Purpose of test:
 *
 *  Check whether if we successfully can replace a class in the class inheritance tree.
 *
 *  Check if method access level breaks operation.
 *
 *      If the original class has a projected method, implementer keeps the access level but the stub change the level
 *      to public would normally give a compilation error as the implementer would lower the access level.
 *
 *      how ever this seems to be acceptable by the linker and therefore stub methods doesn't need to have same access
 *      level as the original.
 *
 * Check if any throw clauses breaks operation.
 *
 *      Casting an exception without the capability to produce it would create a compilation error, but still again
 *      the linker seems to ignore this and therefore the stub's doesn't need to throw exceptions declared by the
 *      method that have been replaced.
 *
 * Check that methods in the main class is still used and methods in the implemented class is stubbed.
 */
@RunWith(JUnitWoodRunner.class)
@Stubs(AbstractBase.class)
public class AbstractTest {
    @Test
    public void testAbstractBaseMock() throws Exception {
        AbstractImplementation implementation;


        implementation = new AbstractImplementation();

        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertEquals("getD", event.getMethodName());
                event.setResult("D");
            }
        });

        Assert.assertEquals("NATIVE", implementation.getA());
        Assert.assertEquals("NATIVE", implementation.getB());
        Assert.assertEquals("NATIVE", implementation.getC());
        Assert.assertEquals("D", implementation.getD());
    }

}

abstract class AbstractBase {
    abstract String getA();

    protected abstract String getB() throws Exception;

    public abstract String getC();

    public String getD() {
        return "NATIVE";
    }
}

class AbstractImplementation extends AbstractBase {
    @Override
    String getA() {
        return "NATIVE";
    }

    @Override
    protected String getB() throws Exception {
        return "NATIVE";
    }

    @Override
    public String getC() {
        return "NATIVE";
    }
}