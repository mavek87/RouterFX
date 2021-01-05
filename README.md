# RouterFX
A JavaFX Router for switching scenes easily

## Core Objects:

- RouterFX: The router used to switch scene
- RoutedWindow: A wrapper of the JavaFX stage used by RouterFX

## Basic usage:

### 1) Create a `RoutedWindow`

Wrap your stage into a RoutedWindow in the JavaFX start method

```RoutedWindow window = new RoutedWindow(primaryStage);```

A RoutedWindow has several optional parameters other than the required stage (String title, RoutedWindowSize windowSize, Boolean resizableByDefault, Boolean maximizedByDefault)
Is it possible to create a RoutedWindow using the constructor with the desired parameters or a builder for a more fluent API:

```
RoutedWindow window = RoutedWindow.builder(stage)
                .title("App title")
                .windowSize(new RoutedWindowSize(1024d, 768d))
                .maximizedByDefault(true)
                .resizableByDefault(true)
                .build();
```

### 2) Init RouterFX

Before using RouterFX you need to call the RouterFX.init method first, otherwise you will receive an IllegalArgumentStateException.
Usually the init method should be called in your start method after the window creation, and it takes the window as parameter.

```RouterFX.init(window);```

If you have some controller with not empty constructor you can also pass a controller factory (Callback<Class<?>, Object> controllerFactory) to the init method. You can create the controller factory by hand or use a dependency injection framework.

```RouterFX.init(window, myControllerFactory);```

### 3) Define the routes 

After the init is called you have to define the routes. To do it use the RouterFX.when method.
You can speciy the view fxml using a string (the fxml will be searched inside src/main/resources):

```RouterFX.when("view1", "view1.fxml");```

Or using an URL:

```RouterFX.when("view2", getClass().getClassLoader().getResource("view2.fxml");```

### 4) Switch scene

Use the method RouterFX.goTo to switch scene

```RouterFX.goTo("view1");```

## License
[MIT](https://choosealicense.com/licenses/mit/)
