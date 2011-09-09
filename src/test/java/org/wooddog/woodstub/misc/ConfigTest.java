/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.wooddog.woodstub.junit.WoodTestCase;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ConfigTest extends WoodTestCase {

    
    @Test
    public void testLoad() {
        Document document;
        DocumentBuilder builder;
        NodeList nodeList;
        String builderFactory;
        String[] excludedClasses;
        String[] stubs;

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(getClass().getResourceAsStream("/woodstub-config.xml"));
            nodeList = document.getElementsByTagName("stub-builder");
            if (nodeList != null && nodeList.getLength() > 0) {
                builderFactory = nodeList.item(0).getTextContent().trim();
            }
                              
            nodeList = document.getElementsByTagName("class");
            if (nodeList != null) {
                excludedClasses = new String[nodeList.getLength()];
                for (int i = 0; i < excludedClasses.length; i++) {
                    excludedClasses[i] = nodeList.item(i).getTextContent().trim();
                }
            }

            nodeList = document.getElementsByTagName("stubs");
            if (nodeList != null) {
                stubs = new String[nodeList.getLength()];
                for (int i = 0; i < stubs.length; i++) {
                    stubs[i] = nodeList.item(i).getTextContent().trim();
                }
            }
        } catch (Exception x) {
            Assert.fail(x.getMessage());
            x.printStackTrace();
        }
    }

    @Before
    public void setup() {
        System.out.println("xFree at setup: "+Runtime.getRuntime().freeMemory()/1000+" KB");
    }
}
