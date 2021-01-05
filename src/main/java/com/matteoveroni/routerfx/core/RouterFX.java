package com.matteoveroni.routerfx.core;

import com.matteoveroni.routerfx.dto.ExtraData;
import com.matteoveroni.routerfx.interfaces.RoutedController;
import com.matteoveroni.routerfx.dto.WindowSize;
import com.matteoveroni.routerfx.factory.AllEmptyConstructorsControllerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * @Author Matteo Veroni
 */
public final class RouterFX {

    private static final Map<String, RouteScene> routes = new HashMap<>();

    private static RoutedWindow window = null;
    private static Callback<Class<?>, Object> controllerFactory = null;
    private static final RouterSceneLoader sceneLoader = new RouterSceneLoader();
    private static final RouterHistory routerHistory = new RouterHistory();
    private static ExtraData extraData;
    private static RouterAnimation globalAnimation;

    private RouterFX() {
    }

    public static void init(RoutedWindow window) {
        init(window, null);
    }

    public static void init(RoutedWindow window, Callback<Class<?>, Object> controllerFactory) {
        if (window == null) {
            throw new IllegalArgumentException("RoutedWindow cannot be null!");
        }
        RouterFX.window = window;
        RouterFX.controllerFactory = (controllerFactory == null) ? new AllEmptyConstructorsControllerFactory() : controllerFactory;
    }

    public static void when(String routeId, String fxmlSceneLocation) throws IOException {
        checkIfInitCalledOrThrowIllegalStateException();
        routes.put(routeId, sceneLoader.loadScene(fxmlSceneLocation, controllerFactory));
    }

    public static void when(String routeId, URL fxmlSceneLocation) throws IOException {
        checkIfInitCalledOrThrowIllegalStateException();
        routes.put(routeId, sceneLoader.loadScene(fxmlSceneLocation, controllerFactory));
    }

    public static void goTo(String routeId) {
        goTo(routeId, null, null, null);
    }

    public static void goTo(String routeId, ExtraData extraData) {
        goTo(routeId, extraData, null, null);
    }

    public static void goTo(String routeId, RouterAnimation animation) {
        goTo(routeId, null, animation, null);
    }

    public static void goTo(String routeId, WindowSize windowSize) {
        goTo(routeId, null, null, windowSize);
    }

    public static void goTo(String routeId, ExtraData extraData, RouterAnimation animation) {
        goTo(routeId, extraData, animation, null);
    }

    public static void goTo(String routeId, ExtraData extraData, WindowSize windowSize) {
        goTo(routeId, extraData, null, windowSize);
    }

    public static void goTo(String routeId, RouterAnimation animation, WindowSize windowSize) {
        goTo(routeId, null, animation, windowSize);
    }

    public static void goTo(String routeId, ExtraData extraData, RouterAnimation animation, WindowSize windowSize) {
        checkIfInitCalledOrThrowIllegalStateException();
        if (routes.containsKey(routeId)) {
            RouteScene scene = routes.get(routeId);
            Object sceneController = scene.getSceneController();

            // Handling router history
            routerHistory.pushState(scene);

            // Handling the extra data if present
            RouterFX.extraData = extraData;
            if (sceneController instanceof RoutedController) {
                ((RoutedController) sceneController).routedControllerReady(Optional.ofNullable(extraData));
            }

            // Handling the animation if present
            if (animation != null) {
                animateSceneTransition(scene.getRoot(), animation);
            } else if (globalAnimation != null) {
                animateSceneTransition(scene.getRoot(), globalAnimation);
            }

            // Show the new scene
            if (windowSize == null) {
                window.showScene(scene);
            } else {
                window.showScene(scene, windowSize);
            }
        } else {
            throw new IllegalArgumentException("Route id '" + routeId + "' not found!");
        }
    }

    public static void goBack() {
        checkIfInitCalledOrThrowIllegalStateException();
        routerHistory.goBack().ifPresent(window::showScene);
    }

    public static void goForward() {
        checkIfInitCalledOrThrowIllegalStateException();
        routerHistory.goForward().ifPresent(window::showScene);
    }

    public static RouteScene getRouteScene(String routeId) {
        checkIfInitCalledOrThrowIllegalStateException();
        return routes.get(routeId);
    }

    public static RouteScene getCurrentScene() {
        checkIfInitCalledOrThrowIllegalStateException();
        return routerHistory.getCurrentScene();
    }

    public static RouterHistory getRouterHistory() {
        checkIfInitCalledOrThrowIllegalStateException();
        return routerHistory;
    }

    public static RoutedWindow getWindow() {
        checkIfInitCalledOrThrowIllegalStateException();
        return window;
    }

    public static ExtraData getExtraData() {
        checkIfInitCalledOrThrowIllegalStateException();
        return RouterFX.extraData;
    }

    public static void setGlobalAnimation(RouterAnimation globalAnimation) {
        checkIfInitCalledOrThrowIllegalStateException();
        RouterFX.globalAnimation = globalAnimation;
    }

    private static void checkIfInitCalledOrThrowIllegalStateException() {
        if (window == null) {
            throw new IllegalStateException("Init method is not being called yet! You must call the init method first!");
        }
    }

    private static void animateSceneTransition(Parent node, RouterAnimation animation) {
        switch (animation) {
            case FADE_LONG:
            case FADE_MEDIUM:
            case FADE_SHORT:
                FadeTransition ftCurrent = new FadeTransition(Duration.millis(animation.getDuration()), node);
                ftCurrent.setFromValue(0.0);
                ftCurrent.setToValue(1.0);
                ftCurrent.play();
                break;
            default:
                break;
        }
    }
}
