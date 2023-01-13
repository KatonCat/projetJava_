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

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 580, 340);
        //Scene scene2 = new Scene(fxmlLoader2.load(), 580, 340);

        primaryStage.setTitle("java app");
        primaryStage.setScene(scene);
        primaryStage.show();

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
        /*Font font = Font.font("Comic Sans MS",16);

        VBox root = new VBox(10);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        HBox buttoncontainer = new HBox();

        title.setText("welcome");
        title.setFont(font);
        statusLabel.setText("Veillez rentrer votre nom d'utilisateur");
        statusLabel.setFont(font);
        TextArea textArea = new TextArea();
        // Set text
        textArea.setText("Enter username");
        // Get text
        String text= textArea.getText();
        addGF = new Button("connect");
        addGF.setOnAction(new ButtonHandler());
        addGF.setFont(font);
        //addGF.graphicProperty().bind(Bindings.when(addGF.hoverProperty()).then(flylikeImageView).otherwise(likeImageView));



        root.getChildren().addAll(title , statusLabel ,textArea , buttoncontainer );
        buttoncontainer.getChildren().addAll( addGF );
        Scene scene = new Scene(root , 400 ,200);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();

    }
    public void init() throws Exception{
        System.out.println("it's working");
    }


    private class ButtonHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e ){
            System.out.println("you entered a username");


        }
    }*/

}