package com.matteoveroni.routerfx.core;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @Author Matteo Veroni
 */
public class RouteScene extends Scene {

    private final Object sceneController;

    public RouteScene(Parent root, Object sceneController) {
        super(root);
        this.sceneController = sceneController;
    }

    public Object getSceneController() {
        return sceneController;
    }

    @Override
    public String toString() {
        return "ControlledScene{" +
                "sceneController=" + sceneController +
                '}';
    }
}
