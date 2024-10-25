package projects.ruclinic.enhancedgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("HELP");
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 344, 600);
        stage.setTitle("RU Clinic");
        stage.setScene(scene);
        stage.show();
        System.out.println("HELP2");
    }

    public static void main(String[] args) {
        launch();
    }
}