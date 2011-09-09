/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.issues;

import org.junit.Test;
import org.wooddog.demo.DataEnum;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;

@Stubs(DataEnum.class)
public class TestStubEnum extends WoodTestCase {
    @Test
    public void testEnumStubbing() {
        behaveAs(DataEnum.class).toCall("getIntValue").andReturn(666);

        DataEnum en = DataEnum.GoodData;
        assertEquals(DataEnum.BadData,DataEnum.valueOf("BadData"));
        assertEquals(666,DataEnum.getIntValue(en));
    }



    @Test
    public void testException() {
        behaveAs(DataEnum.class).toCall("getIntValue").andDelegateTo(new StubListener(){
            @Override
            public void invoked(StubEvent event) {
                event.setResult(667);
            }
        });
        DataEnum en = DataEnum.GoodData;
        assertEquals(DataEnum.BadData,DataEnum.valueOf("BadData"));
        assertEquals(667,DataEnum.getIntValue(en));
    }
}