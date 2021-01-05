# RouterFX
A JavaFX Router for switching scenes easily

## Core Objects:

- RouterFX: The router used to switch scene
- RoutedWindow: A wrapper of the JavaFX stage used by RouterFX

## Basic usage:

### 1) Create a RoutedWindow

Wrap your stage into a RoutedWindow in the JavaFX start method

```RoutedWindow window = new RoutedWindow(primaryStage);```

### 2) Init RouterFX

Before using RouterFX you need to call the `RouterFX.init` method first, otherwise you will receive an IllegalArgumentStateException. Usually the init method should be called in your start method after the window creation, and it takes the window as parameter.

```RouterFX.init(window);```

### 3) Define the routes 

After the init is called you have to define the routes. To do it use the `RouterFX.when` method. You can speciy the view fxml using a string (the fxml will be searched inside src/main/resources):

```RouterFX.when("view1", "view1.fxml");```

Or using an URL:

```RouterFX.when("view2", getClass().getClassLoader().getResource("view2.fxml");```

### 4) Switch scene

Use the method `RouterFX.goTo` everywhere you want (e.g. from your controllers) to switch scene

```RouterFX.goTo("view1");```

## Examples

#### Plain JavaFX without RouterFX

```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view.fxml"));
        primaryStage.setTitle("App title");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

#### Using RouterFX 
```java
import javafx.application.Application;
import javafx.stage.Stage;
import com.matteoveroni.routerfx.router.*;     // import RouterFX classes

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        RoutedWindow window = new RoutedWindow(primaryStage, "App title", new RoutedWindowSize(640.0, 480.0));  // create the window wrapping the primary stage
        RouterFX.init(window);  // init FXRouter
        RouterFX.when("view", "view.fxml");            // set "view" route
        RouterFX.goTo("view");                         // switch to view scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

## Advanced usage:

### `RoutedWindow`

A RoutedWindow has several optional parameters. It is possible to create a RoutedWindow using the constructor with the desired parameters or a builder for a more fluent API:

```
RoutedWindow window = RoutedWindow.builder(stage)
                .title("App title")
                .windowSize(new RoutedWindowSize(1024d, 768d))
                .maximizedByDefault(true)
                .resizableByDefault(true)
                .build();
```

#### RoutedWindow parameters
| Parameter name  | Parameter type | Mandatory | Description |
| ------------- | ------------- | ------------- | ------------- |
| title | String  | Yes | The title of the window |
| windowSize | RoutedWindowSize  | No | The size of the window (width x height)
| resizableByDefault | Boolean  | No | If true the window is resizable or else not resizable
| maximizedByDefault | Boolean  | No | If true the window is maximized by default or esle not maximized by default

#### RoutedWindowSize parameters
| Parameter name  | Parameter type | Mandatory | Description |
| ------------- | ------------- | ------------- | ------------- |
| width | double  | Yes | The width of the window |
| height | double  | No | The height of the window

----------------

### `RouterFX.init`

If you have some controller with not empty constructor you should pass a controller factory (Callback<Class<?>, Object> controllerFactory) to the init method.  You can create the controller factory by hand or use a dependency injection framework.

```RouterFX.init(window, myControllerFactory);```

----------------

### `RouterFX.goTo`

The goTo method can be called using several parameters

#### RouterFX.goTo parameters
| Parameter name  | Parameter type | Mandatory | Description |
| ------------- | ------------- | ------------- | ------------- |
| routeId | String  | Yes | The name of the route |
| extraData | ExtraData  | No | Data to pass to the next scene controller
| animation | RouterAnimation  | No | An enum which allow to specify an animation
| windowSize | RoutedWindowSize  | No | The size of the next opened scene

----------------

### `RouterFX.goBack and RouterFXgoForward`

You can also use the methods `goBack()` and `goForward()` to navigate to the previous or next visited scene. RouterFX contains a history object which stores each time you switch scene. The history object has a similar API to the browser History API https://developer.mozilla.org/en-US/docs/Web/API/History_API

```RouterFX.goBack();```
```RouterFX.goForward();```



## License
[MIT](https://choosealicense.com/licenses/mit/)
