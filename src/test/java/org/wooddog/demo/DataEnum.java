/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.demo;

/**
 * Created by Asbjørn Andersen
 *
 * User: denasa
 * Date: 10-08-2010
 * Time: 14:12:54
 */
public enum DataEnum {
    GoodData,BadData;
    
    public static int getIntValue(DataEnum data) {
        if (data == GoodData) {
            return 1;
        }
        return -1;
    }
}