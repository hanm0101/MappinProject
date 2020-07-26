package controllers;

import java.io.File;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import static org.mockito.Mockito.when;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mappingData.Main;
import mappingData.utils.enums.ControllerEnum;
import mappingData.views.BinConverterView;

public class BinConverterPageTest extends ApplicationTest { 

    /**
     * Initialises the stage with the binary converter page.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        try{
            ApplicationTest.launch(Main.class);
            
            FxToolkit.setupSceneRoot(() -> {
                try {
                    Stage stage = FxToolkit.registerPrimaryStage();
                    BinConverterView binConverter = new BinConverterView();
                    binConverter.start(stage);
                    return binConverter.getScene().getRoot();
                } catch (Exception e) {
                    return null;
                }
            });

            FxToolkit.setupStage((stage) -> {
                stage.show();
                stage.toBack();
                stage.toFront();
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    /**
     * Test that the text on the convert button was
     * correctly initalised. 
     * @throws TimeoutException
     */
    @Test
    public void convertButtonTextTest() throws TimeoutException{
        Stage stage = FxToolkit.registerPrimaryStage();
        Parent rootNode = stage.getScene().getRoot();
        Button convertButton = from(rootNode).lookup("#convertButton").query();
        FxAssert.verifyThat(convertButton, hasText("Convert to BIN"));
    }

    /**
     * Test that the page does not change when 
     * the convert button is clicked without selecting
     * a file.
     */
    @Test
    public void emptyFile() throws TimeoutException {
        Stage stage = FxToolkit.registerPrimaryStage();
        Parent rootNode = stage.getScene().getRoot();
        Label filePathLbl = from(rootNode).lookup("#filePathLbl").query();
        Button convertButton = from(rootNode).lookup("#convertButton").query();

        FxAssert.verifyThat(filePathLbl, hasText("Selected folder: None"));
        FxAssert.verifyThat(window("Binary Converter"), WindowMatchers.isShowing());
        clickOn(convertButton);
        FxAssert.verifyThat(window("Binary Converter"), WindowMatchers.isShowing());
    }

    /**
     * Test that clicking on the logo
     * takes back to the landing page.
     */
    @Test
    public void logoTest() throws TimeoutException{
        Stage stage = FxToolkit.registerPrimaryStage();
        Parent rootNode = stage.getScene().getRoot();
        ImageView logoImg = from(rootNode).lookup("#logoImgView").query();

        FxAssert.verifyThat(window("Binary Converter"), WindowMatchers.isShowing());
        
        clickOn(logoImg);

        FxAssert.verifyThat(window("Mapping Data"), WindowMatchers.isShowing());
    }
}