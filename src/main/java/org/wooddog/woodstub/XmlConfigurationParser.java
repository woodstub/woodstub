/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

class XmlConfigurationParser {
    private static List<Class> ORIG_STUB_LIST;
    private static List<String> ORIG_EXCLUDE_LIST;
    private String configPath;
    private Document configDocument;
    private String systemProperty;

    public XmlConfigurationParser(String configPath, String systemProperty) {
        this.configPath = configPath;
        this.systemProperty = systemProperty;

        if (configDocument == null) {
            loadConfiguration();
        }

        if (ORIG_STUB_LIST == null) {
            ORIG_STUB_LIST = new LinkedList<Class>();
        }

        if (ORIG_EXCLUDE_LIST == null) {
            ORIG_EXCLUDE_LIST = new LinkedList<String>();
        }
    }

    public List<Class> getParsedStubList() {
        return ORIG_STUB_LIST;
    }

    public List<String> getParsedExcludeList() {
        return ORIG_EXCLUDE_LIST;
    }

    public void parse() {
        parseExcludesFromXml();
        parseStubsFromXml();
    }

    private void parseStubsFromXml() {
        NodeList nodeList = configDocument.getElementsByTagName("stub");
        if (nodeList != null) {
            addStubsFromXml(nodeList);
        }
    }

    private void parseExcludesFromXml() {
        NodeList nodeList = configDocument.getElementsByTagName("exclude");
        if (nodeList != null) {
            addExcludesFromXml(nodeList);
        }
    }

    private void addExcludesFromXml(NodeList nodeList) {
        ORIG_EXCLUDE_LIST.clear();
        for (int i = 0; i < nodeList.getLength(); i++) {
            ORIG_EXCLUDE_LIST.add(nodeList.item(i).getTextContent().trim());
        }
    }

    private void addStubsFromXml(NodeList nodeList) {
        ORIG_STUB_LIST.clear();
        for (int i = 0; i < nodeList.getLength(); i++) {
            try {
                Node node = nodeList.item(i);
                addClassFromNode(node);
            } catch (ClassNotFoundException x) {
                throw new StubException("configuration error, class to stub \"" + nodeList.item(0).getTextContent().trim() + "\" not found in classpath");
            }
        }
    }

    private void addClassFromNode(Node node) throws ClassNotFoundException {
        String className = node.getTextContent().trim();
        Class<?> stubbedClass = Class.forName(className);
        ORIG_STUB_LIST.add(stubbedClass);
    }

    private void loadConfiguration() {
        loadFromXmlFile();
        loadFromSystemProperty();
    }

    private void loadFromXmlFile() {
        InputStream resource = Config.class.getResourceAsStream(configPath);
        if (resource != null) {
            try {
                load(resource);
            } catch (SAXException x) {
                throw new StubException("configuration document /woodstub-config.xml is not a valid XML document", x);
            } catch (IOException x) {
                throw new StubException("unable to load configuration document /woodstub-config.xml", x);
            }
        }
    }

    private void loadFromSystemProperty() {
        try {
            loadPropertyFromStream();
        } catch (SAXException x) {
            throw new StubException("configuration document " + System.getProperty(systemProperty) + " is not a valid XML document", x);
        } catch (IOException x) {
            throw new StubException("unable to load configuration document " + System.getProperty(systemProperty), x);
        }

    }

    private void loadPropertyFromStream() throws IOException, SAXException {
        String property = System.getProperty(systemProperty);
        if (property != null) {
            InputStream resource = new FileInputStream(property);
            load(resource);
        }
    }

    private void load(InputStream resource) throws IOException, SAXException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            configDocument = builder.parse(resource);
        } catch (ParserConfigurationException x) {
            throw new StubException("unable to create XML builder for reading configuration", x);
        }
    }
}
