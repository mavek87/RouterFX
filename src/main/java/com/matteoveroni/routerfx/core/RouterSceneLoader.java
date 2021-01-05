package com.matteoveroni.routerfx.core;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

/**
 * @Author Matteo Veroni
 */
public final class RouterSceneLoader {

    public RouteScene loadScene(String sceneLocation, Callback<Class<?>, Object> controllerFactory) throws IOException {
        return loadScene(getClass().getClassLoader().getResource(sceneLocation), controllerFactory);
    }

    public RouteScene loadScene(URL sceneLocation, Callback<Class<?>, Object> controllerFactory) throws IOException {
        FXMLLoader loader = new FXMLLoader(sceneLocation);
        if (controllerFactory != null) {
            loader.setControllerFactory(controllerFactory);
        }
        Parent root = loader.load();
        Object controller = loader.getController();
        return new RouteScene(root, controller);
    }
}
