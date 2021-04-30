package com.matteoveroni.routerfx.dto;

/**
 * @Author Matteo Veroni
 */
public class ExtraData {

    private final Object data;
    private final Class<?> dataClass;

    public ExtraData(Object data) {
        this.data = data;
        this.dataClass = data.getClass();
    }

    public Object getData() {
        return this.data;
    }

    public Class<?> getDataClass() {
        return this.dataClass;
    }

    public String toString() {
        return "ExtraData(data=" + this.getData() + ", dataClass=" + this.getDataClass() + ")";
    }
}
