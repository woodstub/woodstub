package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.builder.MethodBodyFieldKeeper;

/**
 * The body of a method.
 * Will only contain callback related code, and specialised return statement.
 * Cannot contain children!
 */
public class MethodBodyElement extends CodeElement {
    private String className;
    protected String argumentPrefix;
    private String methodName;
    private String returnType;
    private String[] parameterTypeNames;
    private String castName;
    private boolean isStatic;
    private Class[] exceptions;
    private static final String STUB_EVENT_CALL = "org.wooddog.woodstub.InternalStubEvent call = new org.wooddog.woodstub.InternalStubEvent(";
    private static final String NOTIFY_CALL = "\n org.wooddog.woodstub.junit.WoodRunner.notify(call); " +
            "\nif (call.getException() != null) {" +
            "\n    if (call.getException() instanceof RuntimeException) {" +
            "\n         throw (RuntimeException) call.getException();" +
            "\n    " + END_BRACKET + "\n";
    private static final String EXCEPTION_ERROR_MSG = "    throw new  org.wooddog.woodstub.StubException(call.getException().getClass().getCanonicalName() + \" can't be thrown from ";
    private static final String IF_EXCEPTION_IN_SIGNATURE = "    if (call.getException().getClass().getCanonicalName().equals(\"";
    private static final String VOID = "void";
    private static final String RETURN = "return (";
    private static final String GET_RESULT = " call.getResult();";
    private static final String CALL_SET_SOURCE_NULL = "call.setSource(null);";
    private static final String CALL_SET_SOURCE_THIS = "call.setSource(this);";
    private static final String CLASS = ".class";
    private static final String NEW_CLASS = "new Class[]{";
    private static final String THROW = "        throw (";
    private static final String CALL_GET_EXCEPTION = ") call.getException();";
    private static final String NEW_OBJECT_ARRAY = "new Object[]{";
    private static final String NULL = "null);";

    public MethodBodyElement(MethodBodyFieldKeeper keeper, String[] typeNames, String castName) {
        addDataFromKeeper(keeper);
        addCastName(keeper, castName);
        this.parameterTypeNames = typeNames;
    }

    protected MethodBodyElement(String className,String methodName,String returnType) {
        this.className = className;
        this.methodName=methodName;
        this.returnType=returnType;
        castName=EMPTY;
        argumentPrefix=EMPTY;
        parameterTypeNames= new String[0];
    }

    public void addChild(CodeElement e) {
        throw new IllegalArgumentException("Cannot contain children!");
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addCallbackInvoke(builder);
        addSetSource(builder);
        addNotifyCall(builder);
        addExceptionHandling(builder);
        addReturnStatement(builder);
        return builder.toString();

    }

    private void addCastName(MethodBodyFieldKeeper keeper, String castName) {
        if (keeper.isPrimitive()) {
            this.castName = castName;
        } else {
            this.castName = returnType;
        }
    }

    private void addDataFromKeeper(MethodBodyFieldKeeper keeper) {
        this.className = keeper.getClassName();
        this.argumentPrefix = keeper.getArgumentPrefix();
        this.methodName = keeper.getMethodName();
        this.returnType = keeper.getReturnType();
        this.isStatic = keeper.isStatic();
        this.exceptions = keeper.getExceptionTypes();
    }

    private void addCallbackInvoke(StringBuilder builder) {
        addPrologue(builder);
        addClassParameters(builder);
        addObjectParameters(builder);
    }

    private void addPrologue(StringBuilder builder) {
        builder.append(STUB_EVENT_CALL);
        builder.append("\"").append(className).append("\", ");
        builder.append("\"").append(returnType).append("\", ");
        builder.append("\"").append(methodName).append("\", ");
    }

    private void addClassParameters(StringBuilder builder) {
        if (parameterTypeNames.length > 0) {
            builder.append(NEW_CLASS);
            for (int i = 0; i < parameterTypeNames.length; i++) {
                if (i != 0) {
                    builder.append(", ");
                }

                builder.append(parameterTypeNames[i]).append(CLASS);
            }

            builder.append(END_BRACKET + ", ");
        } else {
            builder.append("null,");
        }
    }

    private void addObjectParameters(StringBuilder builder) {
        if (parameterTypeNames.length > 0) {
            builder.append(NEW_OBJECT_ARRAY);
            for (int i = 0; i < parameterTypeNames.length; i++) {
                if (i != 0) {
                    builder.append(", ");
                }
                builder.append(argumentPrefix).append(i + 1);
            }
            builder.append(END_BRACKET + ");");
        } else {
            builder.append(NULL);
        }

        builder.append(newLine);
    }

    private void addSetSource(StringBuilder builder) {
        if (isStatic) {
            builder.append(CALL_SET_SOURCE_NULL);
        } else {
            builder.append(CALL_SET_SOURCE_THIS);
        }
    }

    private void addNotifyCall(StringBuilder builder) {
        builder.append(NOTIFY_CALL);
    }

    private void addExceptionHandling(StringBuilder builder) {
        addExceptionValidation(builder);
        addInvalidExceptionHandling(builder);
    }

    private void addExceptionValidation(StringBuilder builder) {
        if (exceptions != null) {
            for (Class exception : exceptions) {
                builder.append(IF_EXCEPTION_IN_SIGNATURE).append(exception.getCanonicalName()).append("\")) {");
                builder.append(newLine);
                builder.append(THROW).append(exception.getCanonicalName()).append(CALL_GET_EXCEPTION);
                builder.append(newLine);
                builder.append(END_BRACKET);
            }
        }

        builder.append(newLine);
    }

    private void addInvalidExceptionHandling(StringBuilder builder) {
        builder.append(EXCEPTION_ERROR_MSG).append(className).append(".").append(methodName).append("\");");
        builder.append(newLine);
        builder.append(END_BRACKET);
        builder.append(newLine);
    }

    private void addReturnStatement(StringBuilder builder) {
        if (methodHasReturnType()) {
            builder.append(RETURN);
            builder.append(castName);
            builder.append(')');
            builder.append(GET_RESULT);
            builder.append(newLine);
        }
    }

    private boolean methodHasReturnType() {
        return isNotConstructor() && isNotVoid();
    }

    private boolean isNotVoid() {
        return !returnType.equals(VOID);
    }

    private boolean isNotConstructor() {
        return returnType != null && !returnType.equals(EMPTY);
    }
}