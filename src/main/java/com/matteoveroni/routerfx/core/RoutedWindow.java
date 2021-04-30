package com.matteoveroni.routerfx.core;

import com.matteoveroni.routerfx.dto.WindowSize;
import javafx.stage.Stage;

/**
 * @Author Matteo Veroni
 */
public final class RoutedWindow {

    private static final double DEFAULT_WIDTH = 640;
    private static final double DEFAULT_HEIGHT = 480;
    private static final boolean DEFAULT_IS_RESIZABLE = true;
    private static final boolean DEFAULT_IS_MAXIMIZED = false;
    private static final WindowSize DEFAULT_WINDOW_WINDOW_SIZE = new WindowSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    private final Stage stage;
    private final String title;
    private final WindowSize windowSize;
    private final boolean resizableByDefault;
    private final boolean maximizedByDefault;

    public static RoutedWindowBuilder builder(Stage stage) {
        return new RoutedWindowBuilder().stage(stage);
    }

    public RoutedWindow(Stage stage) {
        this(stage, null, null, null, null);
    }

    public RoutedWindow(Stage stage, String title) {
        this(stage, title, null, null, null);
    }

    public RoutedWindow(Stage stage, WindowSize windowSize) {
        this(stage, null, windowSize, null, null);
    }

    public RoutedWindow(Stage stage, boolean resizableByDefault, boolean maximizedByDefault) {
        this(stage, null, null, resizableByDefault, maximizedByDefault);
    }

    public RoutedWindow(Stage stage, String title, WindowSize windowSize) {
        this(stage, title, windowSize, null, null);
    }

    public RoutedWindow(Stage stage, String title, boolean resizableByDefault, boolean maximizedByDefault) {
        this(stage, title, null, resizableByDefault, maximizedByDefault);
    }

    public RoutedWindow(Stage stage, WindowSize windowSize, boolean resizableByDefault, boolean maximizedByDefault) {
        this(stage, null, windowSize, resizableByDefault, maximizedByDefault);
    }

    public RoutedWindow(Stage stage, String title, WindowSize windowSize, Boolean resizableByDefault, Boolean maximizedByDefault) {
        this.stage = stage;
        this.title = (title == null) ? "" : title;;
        this.windowSize = (windowSize == null) ? DEFAULT_WINDOW_WINDOW_SIZE : windowSize;
        this.resizableByDefault = (resizableByDefault == null) ? DEFAULT_IS_RESIZABLE : resizableByDefault;
        this.maximizedByDefault = (maximizedByDefault == null) ? DEFAULT_IS_MAXIMIZED : maximizedByDefault;

        stage.setTitle(this.title);
        stage.setWidth(this.windowSize.getWidth());
        stage.setHeight(this.windowSize.getHeight());
        stage.setResizable(this.resizableByDefault);
        stage.setMaximized(this.maximizedByDefault);
    }

    public boolean isResizableByDefault() {
        return resizableByDefault;
    }

    public boolean isMaximizedByDefault() {
        return maximizedByDefault;
    }

    public String getTitle() {
        return stage.getTitle();
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public double getWidth() {
        return stage.getWidth();
    }

    public void setWidth(double width) {
        stage.setWidth(width);
    }

    public double getHeight() {
        return stage.getHeight();
    }

    public void setHeight(double height) {
        stage.setHeight(height);
    }

    public boolean isShowing() {
        return stage.isShowing();
    }

    void showScene(RouteScene scene) {
        if (isMaximizedByDefault()) {
            stage.setMaximized(true);
        }
        show(scene);
    }

    void showScene(RouteScene scene, WindowSize windowSize) {
        if (windowSize != null) {
            setWidth(windowSize.getWidth());
            setHeight(windowSize.getHeight());
        }
        show(scene);
    }

    protected void show(RouteScene scene) {
        stage.setScene(scene);
        if(!stage.isShowing()) {
            stage.show();
        }
    }

    public static class RoutedWindowBuilder {
        private Stage stage;
        private String title;
        private WindowSize windowSize;
        private boolean resizableByDefault;
        private boolean maximizedByDefault;

        RoutedWindowBuilder() {
        }

        public RoutedWindowBuilder stage(Stage stage) {
            this.stage = stage;
            return this;
        }

        public RoutedWindowBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RoutedWindowBuilder windowSize(WindowSize windowSize) {
            this.windowSize = windowSize;
            return this;
        }

        public RoutedWindowBuilder resizableByDefault(boolean resizableByDefault) {
            this.resizableByDefault = resizableByDefault;
            return this;
        }

        public RoutedWindowBuilder maximizedByDefault(boolean maximizedByDefault) {
            this.maximizedByDefault = maximizedByDefault;
            return this;
        }

        public RoutedWindow build() {
            return new RoutedWindow(stage, title, windowSize, resizableByDefault, maximizedByDefault);
        }

        public String toString() {
            return "RoutedWindow.RoutedWindowBuilder(stage=" + this.stage + ", title=" + this.title + ", windowSize=" + this.windowSize + ", resizableByDefault=" + this.resizableByDefault + ", maximizedByDefault=" + this.maximizedByDefault + ")";
        }
    }
}
