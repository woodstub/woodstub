/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;

@Stubs({Logger.class, Category.class})
public class ConstructorTest extends WoodTestCase {

    @Test
    public void testMe() throws ClassNotFoundException {
        behaveAs(Logger.class).toCall("getLogger").andOutput("Hejmeddig").andReturnNewInstance();
        behaveAs(Category.class).toCall("info").andDelegateTo(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertEquals("test", event.getObject(1));
                System.out.println("Stubbed logger["+event.getObject(1)+"]");
            }
        });

        Logger LOGGER = Logger.getLogger(ConstructorTest.class);
        LOGGER.info("test");
    }
}