package com.matteoveroni.routerfx.core;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @Author Matteo Veroni
 */
public class RouteScene extends Scene {

    private final String routeId;
    private final Object sceneController;

    public RouteScene(String routeId, Parent root, Object sceneController) {
        super(root);
        this.routeId = routeId;
        this.sceneController = sceneController;
    }

    public String getRouteId() {
        return this.routeId;
    }

    public Object getSceneController() {
        return this.sceneController;
    }

    public String toString() {
        return "RouteScene(routeId=" + this.getRouteId() + ", sceneController=" + this.getSceneController() + ")";
    }
}
