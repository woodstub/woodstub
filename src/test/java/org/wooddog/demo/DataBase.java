/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.demo;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class DataBase {

    public static DataBase getInstance() {
        return new DataBase();
    }

    public void openConnection() {
        System.out.println("opening database connection");
    }

    public Object queryForObject(String sql) {
        System.out.println("executing " + sql + " " + getClass().getClassLoader());
        return "native call";
    }

    public void closeConnection() {
        System.out.println("closing database connection");
    }
}
