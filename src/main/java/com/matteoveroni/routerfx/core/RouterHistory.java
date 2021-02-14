package com.matteoveroni.routerfx.core;

import java.util.LinkedList;
import java.util.Optional;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @Author Matteo Veroni
 */
public final class RouterHistory {

    private final LinkedList<RouteScene> backwardHistoryList = new LinkedList<>();
    private final LinkedList<RouteScene> forwardHistoryList = new LinkedList<>();
    private final LinkedList<String> breadcrumb = new LinkedList<>();
    private final ObservableList<String> observableBreadcrumb = FXCollections.observableList(breadcrumb);
    private final SimpleListProperty<String> breadcrumbProperty = new SimpleListProperty<>(observableBreadcrumb);
    private boolean canGoBackward;
    private boolean canGoForward;
    private final SimpleBooleanProperty canGoBackwardProperty = new SimpleBooleanProperty(canGoBackward);
    private final SimpleBooleanProperty canGoForwardProperty = new SimpleBooleanProperty(canGoForward);
    private RouteScene currentScene;

    public RouterHistory() {
        canGoForward = canGoForward();
        canGoBackward = canGoBackward();

        SimpleListProperty<RouteScene> backwardHistoryListProperty = new SimpleListProperty<>(FXCollections.observableList(backwardHistoryList));
        backwardHistoryListProperty.addListener((listValue, oldList, newList) -> canGoBackward = canGoBackward());

        SimpleListProperty<RouteScene> forwardHistoryListProperty = new SimpleListProperty<>(FXCollections.observableList(backwardHistoryList));
        forwardHistoryListProperty.addListener((listValue, oldList, newList) -> canGoForward = canGoForward());
    }

    void pushState(RouteScene routeScene) {
        currentScene = routeScene;
        backwardHistoryList.push(routeScene);
        forwardHistoryList.clear();
        breadcrumb.addLast(routeScene.getRouteId());
    }

    public Optional<RouteScene> goBack() {
        if (canGoBackward()) {
            RouteScene previousScene = backwardHistoryList.pop();
            forwardHistoryList.push(previousScene);
            breadcrumb.removeLast();
            return Optional.of(backwardHistoryList.getFirst());
        } else {
            return Optional.empty();
        }
    }

    public Optional<RouteScene> goForward() {
        if (canGoForward()) {
            RouteScene scene = forwardHistoryList.pop();
            backwardHistoryList.push(scene);
            breadcrumb.addLast(scene.getRouteId());
            return Optional.of(scene);
        } else {
            return Optional.empty();
        }
    }

    public RouteScene getCurrentScene() {
        return currentScene;
    }

    LinkedList<String> getBreadcrumb() {
        return this.breadcrumb;
    }

    public String getFormattedBreadcrumbWithDelimiter(String delimiter) {
        return String.join(delimiter, breadcrumb);
    }

    public ObservableList<String> getObservableBreadcrumb() {
        return observableBreadcrumb;
    }

    public SimpleListProperty<String> getBreadcrumbProperty() {
        return breadcrumbProperty;
    }

    public final void printHistory(String header) {
        System.out.println(header.toUpperCase());
        System.out.println("currentScene: " + currentScene);
        System.out.println("backwardHistoryList: " + backwardHistoryList);
        System.out.println("forwardHistoryList: " + forwardHistoryList);
        System.out.println("----------------------------------------------------\n");
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
