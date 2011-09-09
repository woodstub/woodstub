/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.demo;

public enum DataEnum {
    GoodData,BadData;
    
    public static int getIntValue(DataEnum data) {
        if (data == GoodData) {
            return 1;
        }
        return -1;
    }

    public static final boolean isGood(DataEnum data) {
        return data == GoodData;
    }
    public void giveVoid(Void v) {
            System.out.println(v.toString());
    }

}