# RouterFX
A JavaFX Router for switching scenes easily

[![](https://jitpack.io/v/mavek87/RouterFX.svg)](https://jitpack.io/#mavek87/RouterFX)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Core Objects:

- RouterFX: The router used to switch scene
- RoutedWindow: A wrapper of the JavaFX stage used by RouterFX

## Basic usage:

### 1) Create a RoutedWindow

Wrap your stage into a RoutedWindow in the JavaFX start method

```java
RoutedWindow window = new RoutedWindow(primaryStage);
```

### 2) Init RouterFX

Before using RouterFX you need to call the `RouterFX.init` method first, otherwise you will receive an IllegalArgumentStateException. Usually the init method should be called in your start method after the window creation, and it takes the window as parameter.

```java
RouterFX.init(window);
```

### 3) Define the routes 

After the init is called you have to define the routes. To do it use the `RouterFX.when` method. You can speciy the view fxml using a string (the fxml will be searched inside src/main/resources):

```java
RouterFX.when("view1", "view1.fxml");
```

Or using an URL:

```java
RouterFX.when("view2", getClass().getClassLoader().getResource("view2.fxml");
```

### 4) Switch scene

Use the method `RouterFX.goTo` everywhere you want (e.g. from your controllers) to switch scene

```java
RouterFX.goTo("view1");
```

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
import com.matteoveroni.routerfx.*;     // import RouterFX classes

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // create the window wrapping the primary stage
        RoutedWindow window = new RoutedWindow(primaryStage, "App title", new RoutedWindowSize(640.0, 480.0));
        // init FXRouter
        RouterFX.init(window);  
        // set a route
        RouterFX.when("view", "view.fxml");           
        // switch to view scene
        RouterFX.goTo("view");                         
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

## Advanced usage:

### `RoutedWindow`

It is possible to build a `RoutedWindow` using several parameters:

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

You can create a `RoutedWindow` using the constructor with the desired parameters or a builder for a more fluent API:

```java
RoutedWindow window = RoutedWindow.builder(stage)
                .title("App title")
                .windowSize(new RoutedWindowSize(1024d, 768d))
                .maximizedByDefault(true)
                .resizableByDefault(true)
                .build();
```


----------------

### `RouterFX.init()`

If you have some controller with not empty constructor you should pass a controller factory (Callback<Class<?>, Object> controllerFactory) to the init method.  You can create the controller factory by hand or use a dependency injection framework.

```java
RouterFX.init(window, myControllerFactory);
```

----------------

### `RouterFX.goTo()`

The goTo method is overloaded and can be called using several parameters

#### RouterFX.goTo parameters
| Parameter name  | Parameter type | Mandatory | Description |
| ------------- | ------------- | ------------- | ------------- |
| routeId | String  | Yes | The name of the route |
| extraData | ExtraData  | No | Data to pass to the next scene controller
| animation | RouterAnimation  | No | An enum which allow to specify an animation
| windowSize | RoutedWindowSize  | No | The size of the next opened scene

----------------

### `RouterFX.goBack() and RouterFX.goForward()`

You can also use the methods `goBack()` and `goForward()` to navigate to the previous or next visited scene. RouterFX contains internally a history object which stores each time you switch scene. The history object has a similar API to the browser History API https://developer.mozilla.org/en-US/docs/Web/API/History_API

```java
RouterFX.goBack();
```

```java
RouterFX.goForward();
```

----------------

### `Passing data between scenes`

If you need to pass data between two scenes (controllers) you have to pass an `ExtraData` parameter in the `goTo()` method.
```java
String greeting = "Hello world!";
RouterFX.goTo("vista1", new ExtraData(greeting));
```

### `How to retrieve the extra data`

Suppose to call the goTo with extra data (our greeting) in controller1. To retrieve the extra data sent by the last goTo you have two choices:

1) Method 1: From controller2 you can receive the greeting with this code: 
```java
String receivedGreeting = (String) RouterFX.getExtraData();
```
2) Method2: Or you can implement the interface `RoutedController` in controller2

```java
public class Controller2 implements RoutedController {

    @Override
    public void routedControllerReady(Optional<ExtraData> dataFromPreviousRoute) {
        dataFromPreviousRoute.ifPresent(data -> System.out.println("data from previous route => " + data));
    }
```
The RoutedController interface is used by RouteFX to inject in your controllers the extra data sent in the last `RouterFX.goTo()`.

```java
public interface RoutedController {

    void routedControllerReady(Optional<ExtraData> dataFromPreviousRoute);
}
```
You can use both methods to retrieve the extra data because they store the same data. The only note is that you cannot call the `RouterFX.getExtraData()` from the javafx initialize method, so if you need to setup something before the scene is showed for the first time you should use the `RoutedController` callback aproach.

----------------

### `How to use a controller from another one`

You can retrieve each of your controllers from others using the code:

```java
MyViewController c = RouterFX.getRouteScene("myView").getController();
```


## credits

- Credits to Marco Trombino (https://github.com/Marcotrombino/FXRouter) for the idea. This router has more advanced functionalities but was influenced by FXRouter even though it is completely written from scratch and has conceptual differencies under the hood.
- Credits to web history API (https://developer.mozilla.org/en-US/docs/Web/API/History_API)

## License
[MIT](https://choosealicense.com/licenses/mit/)
