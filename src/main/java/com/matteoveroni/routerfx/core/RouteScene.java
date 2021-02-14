package com.matteoveroni.routerfx.core;

import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Matteo Veroni
 */
@Getter
@ToString
public class RouteScene extends Scene {

    private final String routeId;
    private final Object sceneController;

    public RouteScene(String routeId, Parent root, Object sceneController) {
        super(root);
        this.routeId = routeId;
        this.sceneController = sceneController;
    }
}
