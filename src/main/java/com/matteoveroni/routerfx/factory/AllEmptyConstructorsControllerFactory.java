package com.matteoveroni.routerfx.factory;

import javafx.util.Callback;

/**
 * @Author Matteo Veroni
 */
public class AllEmptyConstructorsControllerFactory implements Callback<Class<?>, Object> {

    @Override
    public Object call(Class<?> param) {
        try {
            return param.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Impossible to create a controller of class '" + param + "'. Maybe a controller for this class doesn't exists or it has a not empty constructor! In that case create an appropriate controller factory and pass it to the RouterFX.init method in the beginning");
        }
    }
}