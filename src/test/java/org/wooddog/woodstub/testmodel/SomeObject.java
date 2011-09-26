/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.testmodel;

public class SomeObject {
    public int someMethod(String arg) {
        return 0;
    }

    public int someOtherMethod(String arg, String otherArg) {
        return 100;
    }

    public void aMethodWithExceptions() throws RuntimeException, Exception {}

    public void intMethod(int i) {}
    public void charMethod(char c) {}
    public void shortMethod(short i) {}
    public void byteMethod(byte i) {}
    public void longMethod(long i) {}
    public void floatMethod(float i) {}
    public void doubleMethod(double i) {}

    public int returnIntMethod() {return 0;}
    public short returnShortMethod() {return 0;}
    public long returnLongMethod() {return 0;}
    public float returnFloatMethod() {return 0;}
    public double returnDoubleMethod() {return 0;}

    public String returnStringMethod() {
        return "";
    }

    public ValueClass returnValueObject() {
        return null;
    }
}