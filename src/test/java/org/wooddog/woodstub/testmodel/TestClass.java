/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.testmodel;

import org.junit.Ignore;

@Ignore
public class TestClass extends TestSuperClass {
    public TestClass(int i) {
        super(i);
    }

    public void setAnInt(int i) {
        //Lala
    }

    public void setAnotherInt(Integer i) {
        //Lala
    }

    public void doOutput(SomeObject o) {
    }
}