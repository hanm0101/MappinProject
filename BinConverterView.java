package mappingData.views;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mappingData.Communicator;
import mappingData.controllers.ConverterController;


/**
 * BinConverterView opens the binary converter.
 *
 * @author Hana Mizukami and Zeineb Bouchamaoui
 * @version 09-03-2020
 */

public class BinConverterView extends Application {
    private Communicator communicator = Communicator.getInstance();
    private static Scene binConverterScene;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            URL url = getClass().getResource("/views/ConverterView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            ConverterController currentController = loader.getController();
            currentController.setConverterType(ConverterController.ConverterType.BIN);

            // initiates scene in a stage
            binConverterScene = new Scene(root);

            binConverterScene.getStylesheets().add(communicator.getStylesheet());

            stage.setScene(binConverterScene);
            stage.setTitle("Binary Converter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return binConverterScene;
    }
}