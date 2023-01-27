package Interface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.ClassLoader.getSystemClassLoader;


public class App extends Application {
    static Scene scene ;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

        this.stage= primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 580, 340);
        primaryStage.setTitle("Clavardage");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static Stage getStage() {
        return stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch(args);
    }


}