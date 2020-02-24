package frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 */
public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //start of player number dialog box
        Dialog<Integer> dialogNumPlayers = new Dialog<>();
        dialogNumPlayers.setTitle("Property Tycoon");
        dialogNumPlayers.setHeaderText("Select the number of players.");
        dialogNumPlayers.setResizable(false);
        Label playerLabel = new Label("Player Count: ");
        ComboBox<Integer> playerCombo = new ComboBox<>();
        // change later
        playerCombo.getItems().addAll(2,3);
        playerCombo.setValue(2);
        HBox hboxPlayerCount = new HBox();
        HBox.setHgrow(playerLabel, Priority.ALWAYS);
        HBox.setHgrow(playerCombo, Priority.ALWAYS);
        hboxPlayerCount.setMaxHeight(50);
        hboxPlayerCount.setMinHeight(50);
        hboxPlayerCount.setSpacing(15);
        hboxPlayerCount.setAlignment(Pos.CENTER);
        hboxPlayerCount.getChildren().addAll(playerLabel, playerCombo);
        dialogNumPlayers.getDialogPane().setContent(hboxPlayerCount);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogNumPlayers.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialogNumPlayers.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                return playerCombo.getValue();
            }
            return null;
        });
        dialogNumPlayers.showAndWait();
        // end of player number dialog box



        //start of player selection dialog box
        Dialog<Integer[]> dialogPlayerTokens = new Dialog<>();
        dialogPlayerTokens.setTitle("Property Tycoon");
        dialogPlayerTokens.setHeaderText("Select the tokens you want to play with.");
        dialogPlayerTokens.setResizable(false);
        VBox hBoxHolder = new VBox();
        HBox[] hBoxArray = new HBox[dialogNumPlayers.getResult()];
        Label[] labelArray = new Label [dialogNumPlayers.getResult()];
        ComboBox[] dropDownArray = new ComboBox[dialogNumPlayers.getResult()];
        for (int i =0; i<dialogNumPlayers.getResult() ;i++){
            hBoxArray[i] = new HBox();
            hboxPlayerCount.setMaxHeight(50);
            hboxPlayerCount.setMinHeight(50);
            hboxPlayerCount.setSpacing(15);
            hboxPlayerCount.setAlignment(Pos.CENTER);
            hboxPlayerCount.getChildren().addAll(playerLabel, playerCombo);
            dialogNumPlayers.getDialogPane().setContent(hboxPlayerCount);


        }

        Label playerLabel = new Label("Player Count: ");
        ComboBox<Integer> playerCombo = new ComboBox<>();
        // change later
        playerCombo.getItems().addAll(2,3);
        playerCombo.setValue(2);
        HBox hboxPlayerCount = new HBox();
        HBox.setHgrow(playerLabel, Priority.ALWAYS);
        HBox.setHgrow(playerCombo, Priority.ALWAYS);
        hboxPlayerCount.setMaxHeight(50);
        hboxPlayerCount.setMinHeight(50);
        hboxPlayerCount.setSpacing(15);
        hboxPlayerCount.setAlignment(Pos.CENTER);
        hboxPlayerCount.getChildren().addAll(playerLabel, playerCombo);
        dialogNumPlayers.getDialogPane().setContent(hboxPlayerCount);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogNumPlayers.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialogNumPlayers.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                return playerCombo.getValue();
            }
            return null;
        });
        dialogNumPlayers.showAndWait();
        // end of player token dialog box
        Scene scene = new Scene(playerLabel, 900, 600);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Property Tycoon");
        primaryStage.setScene(scene);
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.show();
        Stage stage = primaryStage;
    }
}
