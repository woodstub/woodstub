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
import org.wooddog.woodstub.junit.AssertionFlow;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodRunner;
import org.wooddog.woodstub.junit.annotation.Stubs;

/**
 * Purpose of test
 *
 * Check if the Stubbed class implements interfaces declared by its predecessor, and if any cast problems etc. is raised
 * when using them.
 *
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
@RunWith(WoodRunner.class)
@Stubs({Implementer.class})
public class InterfaceTest {
    @After
    public void tearDown() {
        WoodRunner.cleanup();
   }
    @Test
    public void testImplementUser() {
        ImplementUser implementUser;

        AssertionFlow flow = new AssertionFlow();
        implementUser = new ImplementUser();

        WoodRunner.addListener(flow);

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertTrue("getIOne".equals(event.getMethodName()));
                event.setResult(new iOne(){
                    @Override
                    public int getX() {
                        return 10;
                    }
                });

            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertTrue("getITwo".equals(event.getMethodName()));
                event.setResult(new iTwo(){
                    @Override
                    public int getY() {
                        return 20;
                    }
                });

            }
        });

        flow.start();

        Assert.assertTrue(implementUser.getIOne() instanceof iOne);
        Assert.assertTrue(implementUser.getITwo() instanceof iTwo);
    }
}


interface iOne {
    int getX();
}

interface iTwo {
    int getY();
}

class Implementer implements iOne, iTwo {
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}

class ImplementUser {
    private Implementer implementer = new Implementer();

    public iOne getIOne() {
        return implementer;
    }

    public iTwo getITwo() {
        return implementer;
    }
}