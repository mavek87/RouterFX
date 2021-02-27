package com.matteoveroni.routerfx.core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.Optional;

/**
 * This class handles the history of the router
 *
 * @Author Matteo Veroni
 */
public final class RouterHistory {

    private final LinkedList<RouteScene> backwardHistoryList = new LinkedList<>();
    private final LinkedList<RouteScene> forwardHistoryList = new LinkedList<>();
    private final LinkedList<String> breadcrumb = new LinkedList<>();
    private RouteScene currentScene;

    // JavaFX Observables
    private final ObservableList<String> observableBreadcrumb = FXCollections.observableList(breadcrumb);

    // JavaFX Properties
    private final SimpleBooleanProperty canGoBackwardProperty = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty canGoForwardProperty = new SimpleBooleanProperty(false);
    private final SimpleStringProperty currentRouteProperty = new SimpleStringProperty();

    /**
     * Add a route scene to router history
     * @param routeScene The route scene to add
     */
    void pushState(RouteScene routeScene) {
        currentScene = routeScene;
        backwardHistoryList.push(currentScene);
        canGoBackwardProperty.set(canGoBackward());
        forwardHistoryList.clear();
        canGoForwardProperty.set(canGoForward());
        breadcrumb.addLast(currentScene.getRouteId());
        currentRouteProperty.set(currentScene.getRouteId());
    }

    /**
     * Register goBack action to router history
     * @return The current (Optional) RouteScene after the go back action, or Optional.empty if it's not possible to go back.
     */
    public Optional<RouteScene> goBack() {
        if (canGoBackward()) {
            RouteScene previousScene = backwardHistoryList.pop();
            canGoBackwardProperty.set(canGoBackward());
            forwardHistoryList.push(previousScene);
            canGoForwardProperty.set(true);
            currentScene = backwardHistoryList.getFirst();
            breadcrumb.removeLast();
            currentRouteProperty.set(currentScene.getRouteId());
            return Optional.of(currentScene);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Register go forward action to router history
     * @return The current route scene after the go forward action as an Optional, or Optional.empty if it's not possible to go forward.
     */
    public Optional<RouteScene> goForward() {
        if (canGoForward()) {
            currentScene = forwardHistoryList.pop();
            canGoForwardProperty.set(canGoForward());
            backwardHistoryList.push(currentScene);
            canGoBackwardProperty.set(true);
            breadcrumb.addLast(currentScene.getRouteId());
            currentRouteProperty.set(currentScene.getRouteId());
            return Optional.of(currentScene);
        } else {
            return Optional.empty();
        }
    }

    public ObservableList<String> getBreadcrumb() {
        return observableBreadcrumb;
    }

    public String getFormattedBreadcrumbWithDelimiter(String delimiter) {
        return String.join(delimiter, breadcrumb);
    }

    public RouteScene getCurrentScene() {
        return currentScene;
    }

    public String getCurrentRoute() {
        return currentRouteProperty.get();
    }

    public SimpleStringProperty currentRouteProperty() {
        return currentRouteProperty;
    }

    public SimpleBooleanProperty canGoBackwardProperty() {
        return canGoBackwardProperty;
    }

    public SimpleBooleanProperty canGoForwardProperty() {
        return canGoForwardProperty;
    }

    private boolean canGoBackward() {
        return backwardHistoryList.size() > 1;
    }

    private boolean canGoForward() {
        return !forwardHistoryList.isEmpty();
    }
}
