/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

class MethodBodyCodeSnippets {
    protected static final String STUB_EVENT_CALL = "org.wooddog.woodstub.InternalStubEvent call = new org.wooddog.woodstub.InternalStubEvent(";
    protected static final String NOTIFY_CALL = "\n org.wooddog.woodstub.junit.WoodRunner.notify(call); " +
            "\nif (call.getException() != null) {" +
            "\n    if (call.getException() instanceof RuntimeException) {" +
            "\n         throw (RuntimeException) call.getException();" +
            "\n    }\n";
    protected static final String EXCEPTION_ERROR_MSG = "    throw new  org.wooddog.woodstub.StubException(call.getException().getClass().getCanonicalName() + \" can't be thrown from ";
    protected static final String IF_EXCEPTION_IN_SIGNATURE = "    if (call.getException().getClass().getCanonicalName().equals(\"";
    protected static final String VOID = "void";
    protected static final String RETURN = "return (";
    protected static final String GET_RESULT = " call.getResult();";
    protected static final String CLASS = ".class";
    protected static final String NEW_CLASS_ARRAY = "new Class[]{";
    protected static final String THROW = "        throw (";
    protected static final String CALL_GET_EXCEPTION = ") call.getException();";
    protected static final String NEW_OBJECT_ARRAY = "new Object[]{";
    protected static final String NULL = "null);";
}
