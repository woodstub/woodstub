/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.annotation.Stubs;

@Ignore
@RunWith(JUnitWoodRunner.class)
@Stubs(Logger.class)
// suffers by issue WOODSTUB-15       
public class Log4JDelegateTest {
    public static final Logger LOG = Logger.getLogger(Log4JDelegateTest.class);


    @Test
    public void testDelegate() {
        WoodRunner.addListener(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        LOG.error("Hello World");
    }

    public static void main(String[] args) throws CodeDirectorException {
        
    }
}
