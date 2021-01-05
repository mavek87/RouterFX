package com.matteoveroni.routerfx.core;

import java.util.LinkedList;
import java.util.Optional;

/**
 * @Author Matteo Veroni
 */
public final class RouterHistory {

    private final LinkedList<RouteScene> backwardHistoryList = new LinkedList<>();
    private final LinkedList<RouteScene> forwardHistoryList = new LinkedList<>();
    private RouteScene currentScene;

    void pushState(RouteScene routeScene) {
        currentScene = routeScene;
        backwardHistoryList.push(routeScene);
    }

    Optional<RouteScene> goBack() {
        if (backwardHistoryList.size() > 1) {
            RouteScene previousScene = backwardHistoryList.pop();
            forwardHistoryList.push(previousScene);
            return Optional.of(backwardHistoryList.getFirst());
        } else {
            return Optional.empty();
        }
    }

    Optional<RouteScene> goForward() {
        if (!forwardHistoryList.isEmpty()) {
            RouteScene scene = forwardHistoryList.pop();
            backwardHistoryList.push(scene);
            return Optional.of(scene);
        } else {
            return Optional.empty();
        }
    }

    RouteScene getCurrentScene() {
        return currentScene;
    }

    public final void printHistory(String header) {
        System.out.println(header.toUpperCase());
        System.out.println("currentScene: " + currentScene);
        System.out.println("backwardHistoryList: " + backwardHistoryList);
        System.out.println("forwardHistoryList: " + forwardHistoryList);
        System.out.println("----------------------------------------------------\n");
    }

}
