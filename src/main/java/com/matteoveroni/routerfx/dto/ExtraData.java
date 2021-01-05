package com.matteoveroni.routerfx.dto;

import lombok.Data;

/**
 * @Author Matteo Veroni
 */
@Data
public class ExtraData {

    private final Object data;
    private final Class<?> dataClass;
}
