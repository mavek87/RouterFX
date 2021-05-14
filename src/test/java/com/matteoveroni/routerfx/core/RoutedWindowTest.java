package com.matteoveroni.routerfx.core;

import com.matteoveroni.routerfx.dto.WindowSize;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// Not a real junit test, but useful to test if the graphics is working
public class RoutedWindowTest {

    public static void main(String[] args) {
        Application.launch(TestCenterStageOnShownNotSet.class);
//        Application.launch(TestCenterStageOnShownSetToTrue.class);
//        Application.launch(TestCenterStageOnShownSetToFalse.class);
    }

    public static class TestCenterStageOnShownNotSet extends Application {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            BorderPane root = buildRootPane();

            // Set the stage to the x position 0
            primaryStage.setX(0);

            RoutedWindow routedWindow = RoutedWindow.builder(primaryStage)
                    .title(getClass().getSimpleName())
                    .resizableByDefault(true)
                    .windowSize(new WindowSize(1024, 768))
                    .build();
            RouterFX.init(routedWindow);
            RouterFX.when("firstScene", root);
            RouterFX.goTo("firstScene");
        }
    }

    public static class TestCenterStageOnShownSetToTrue extends Application {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            BorderPane root = buildRootPane();

            // Set the stage to the x position 0
            primaryStage.setX(0);

            RoutedWindow routedWindow = RoutedWindow.builder(primaryStage)
                    .title(getClass().getSimpleName())
                    .resizableByDefault(true)
                    .windowSize(new WindowSize(1024, 768))
                    .centerWindowsOnShown(true)
                    .build();
            RouterFX.init(routedWindow);
            RouterFX.when("firstScene", root);
            RouterFX.goTo("firstScene");
        }
    }

    public static class TestCenterStageOnShownSetToFalse extends Application {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            BorderPane root = buildRootPane();

            // Set the stage to the x position 0
            primaryStage.setX(0);

            RoutedWindow routedWindow = RoutedWindow.builder(primaryStage)
                    .title(getClass().getSimpleName())
                    .resizableByDefault(true)
                    .windowSize(new WindowSize(1024, 768))
                    .centerWindowsOnShown(false)
                    .build();
            RouterFX.init(routedWindow);
            RouterFX.when("firstScene", root);
            RouterFX.goTo("firstScene");
        }
    }

    private static BorderPane buildRootPane() {
        Button button = new Button("Press me");
        BorderPane root = new BorderPane();
        root.setCenter(button);
        return root;
    }
}