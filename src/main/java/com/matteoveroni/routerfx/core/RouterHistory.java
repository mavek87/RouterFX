package com.matteoveroni.routerfx.core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private boolean canGoBackward;
    private boolean canGoForward;
    private RouteScene currentScene;

    // JavaFX Observables
    private final ObservableList<String> observableBreadcrumb = FXCollections.observableList(breadcrumb);

    // JavaFX Properties
    private final SimpleIntegerProperty backwardHistoryListSizeProperty = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty forwardHistoryListSizeProperty = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty canGoBackwardProperty = new SimpleBooleanProperty();
    private final SimpleBooleanProperty canGoForwardProperty = new SimpleBooleanProperty();
    private final SimpleObjectProperty<RouteScene> currentSceneProperty = new SimpleObjectProperty<>();

    public RouterHistory() {
        canGoBackward = canGoBackward();
        canGoBackwardProperty.set(canGoBackward);
        backwardHistoryListSizeProperty.addListener((observableValue, oldValue, newValue) -> {
            canGoBackward = canGoBackward();
            canGoBackwardProperty.set(canGoBackward);
        });

        canGoForward = canGoForward();
        canGoForwardProperty.set(canGoForward);
        forwardHistoryListSizeProperty.addListener((observableValue, oldValue, newValue) -> {
            canGoForward = canGoForward();
            canGoForwardProperty.set(canGoForward);
        });
    }

    /**
     * Add a route scene to router history
     * @param routeScene The route scene to add
     */
    void pushState(RouteScene routeScene) {
        currentScene = routeScene;
        currentSceneProperty.set(currentScene);

        backwardHistoryList.push(routeScene);
        backwardHistoryListSizeProperty.set(backwardHistoryList.size());

        forwardHistoryList.clear();
        forwardHistoryListSizeProperty.set(0);

        breadcrumb.addLast(routeScene.getRouteId());
    }

    /**
     * Register goBack action to router history
     * @return The current route scene after the go back action as an Optional, or Optional.empty if it's not possible to go back.
     */
    public Optional<RouteScene> goBack() {
        if (canGoBackward()) {
            RouteScene previousScene = backwardHistoryList.pop();
            backwardHistoryListSizeProperty.set(backwardHistoryList.size());

            forwardHistoryList.push(previousScene);
            forwardHistoryListSizeProperty.set(forwardHistoryList.size());

            breadcrumb.removeLast();
            return Optional.of(backwardHistoryList.getFirst());
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
            RouteScene scene = forwardHistoryList.pop();
            forwardHistoryListSizeProperty.set(forwardHistoryList.size());

            backwardHistoryList.push(scene);
            backwardHistoryListSizeProperty.set(backwardHistoryList.size());

            breadcrumb.addLast(scene.getRouteId());
            return Optional.of(scene);
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

    public SimpleObjectProperty<RouteScene> currentSceneProperty() {
        return currentSceneProperty;
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
