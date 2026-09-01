package com.javarush.island.zelenin.util;

public final class Const {
    public static final String PROPERTIES_YAML = "/zelenin/properties.yaml";
    public static final String UNSUPPORTED_OPERATION = "Unsupported operation";
    public static final String UNKNOWN_TYPE = "Unknown type";

    private Const() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }
}
