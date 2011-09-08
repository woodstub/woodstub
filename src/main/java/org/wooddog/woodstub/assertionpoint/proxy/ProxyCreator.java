/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint.proxy;

import java.lang.reflect.Proxy;

/**
 * Factory for creating Proxies that utilise the Woodstub callback-logic
 */
public class ProxyCreator {

    /**
     * The provided class MUST be an interface, or the method will fail.
     */
    public static <T> T create(Class<T>  clazz) {
        if (clazz.isInterface()) {
            return createProxyFromInterface(clazz);
        }

        throw new ProxyException("Proxies can only be created from an Interface");
    }

    private static <T> T createProxyFromInterface(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyStub());
    }
}
