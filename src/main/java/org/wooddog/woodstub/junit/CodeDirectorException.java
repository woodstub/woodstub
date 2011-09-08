/*
 * Copyright (c) 2010.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.junit;

import org.wooddog.woodstub.builder.CodeBuilderException;

/**
 * Created by Asbjørn Andersen
 * <p/>
 * User: denasa
 * Date: 11-08-2010
 * Time: 13:57:19
 */
public class CodeDirectorException extends Throwable {
    public CodeDirectorException(CodeBuilderException source) {
        super(source);
    }
}