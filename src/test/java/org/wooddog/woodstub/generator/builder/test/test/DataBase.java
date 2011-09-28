/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.test.test;

import org.apache.log4j.Logger;

import java.io.Serializable;

public class DataBase extends BaseDataBase implements Serializable {
    public static final Logger LOG = Logger.getLogger(DataBase.class);
    private static DataBase instance;
    private int myInt;

    public static DataBase getInstance() {
        return new DataBase();
    }

    public String execute(String sql) {
        return "native toCall";
    }

    public int getCount() {
        return 1;
    }

    public void sayHi(Types.MyTypes aType) {

    }

    public static void MyWay() {

    }

    public void flippetiFlopFloo(Object... objs) {

    }

    public DataBase clone() {
        return new DataBase();
    }

    private class InnerDatabase {
        void onInner() {

        }

    }

}