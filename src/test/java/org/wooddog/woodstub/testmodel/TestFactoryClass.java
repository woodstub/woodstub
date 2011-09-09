/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.testmodel;

import org.junit.Ignore;

@Ignore
public class TestFactoryClass {
    private TestFactoryClass(String hug) {

    }

    public static TestFactoryClass create(String h) {
        return new TestFactoryClass(h);
    }

    public void doSomething() {
        
    }
}