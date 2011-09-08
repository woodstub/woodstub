/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Danmark
 * All rights reserved.
 */

package org.wooddog.woodstub.junit.annotation;

import org.apache.log4j.Logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Excludes {
    String[] value();
}
