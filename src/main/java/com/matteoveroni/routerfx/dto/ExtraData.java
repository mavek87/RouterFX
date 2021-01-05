package com.matteoveroni.routerfx.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * @Author Matteo Veroni
 */
@Getter
@ToString
public class ExtraData {

    private final Object data;
    private final Class<?> dataClass;

    public ExtraData(Object data) {
        this.data = data;
        this.dataClass = data.getClass();
    }
}
