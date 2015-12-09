package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        primaryStage.setTitle("IMDB Search Engine");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 830)); // Dimensions de la fenêtre Largeur;Hauteur
        primaryStage.show();
        primaryStage.getIcons().add(new Image("http://martyraney.com/wp-content/uploads/2013/06/imdb1.png")); // Icone en haut de l'app
    }


    public static void main(String[] args) {

        // Ajoute l'icone au démarrage sur mac
        try {
            URL iconURL = Main.class.getResource("images/logoApp.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Won't work on Windows or Linux.
        }

        launch(args); // Démarrage de JavaFX

    }

}
