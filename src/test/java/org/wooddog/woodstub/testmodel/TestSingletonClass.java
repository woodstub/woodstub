/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.testmodel;

import org.junit.Ignore;

@Ignore
public class TestSingletonClass {
    private static TestSingletonClass instance = new TestSingletonClass();
    private boolean myBool=true;
    private int myInt = 100;
    private long myLong = 100L;
    private double myDouble = 100.0;

    public String getMyString() {
        return myString;
    }

    private String myString = "";

    public double getMyDouble() {
        return myDouble;
    }

    public int getMyInt() {
        return myInt;
    }

    public long getMyLong() {
        return myLong;
    }

    public boolean isMyBool() {
        return myBool;
    }

    public static TestSingletonClass getInstance() {
        return instance;
    }

    private TestSingletonClass() {
    }

    public void raiseException() throws InterruptedException {
        throw new InterruptedException("Exeception raied");
    }
}