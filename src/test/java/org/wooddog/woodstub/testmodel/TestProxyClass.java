/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.testmodel;

import org.junit.Ignore;

@Ignore
public class TestProxyClass {
    public TestProxyClass() {
        
    }

    public void doOutput() {
        System.out.println("Out");
    }

    public void doDifferentOutput(SomeObject o) {
        System.out.println("Out:"+o);
    }

    public String getSomeString() {
        return "Some string";
    }

    public void doFailingOutput(TestFactoryClass testClass) {
        testClass.doSomething();
    }

    public LocalConstructed doLocalConstruction() {
        return new LocalConstructed("Proxy!");
    }
}