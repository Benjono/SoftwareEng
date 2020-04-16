package frontend;

import backend.GameMaster;
import backend.InvalidHouseSetupException;
import backend.Tokens;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
 * @author Jonathan Morris
 */
public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //setup dialogues and GameMaster
        int numPlayers = (int) (new NumberOfPlayersDialog()).getR();
        Tokens[] playerTokens = (Tokens[]) (new SelectingTokensDialog(numPlayers)).getR();
        GameMasterGui GM = new GameMasterGui(numPlayers, playerTokens);

        //Main gameplay screen
        BorderPane gameScreen = new BorderPane();
        VBox alignV = new VBox();
        HBox alignH = new HBox();
        alignV.setAlignment(Pos.CENTER);
        alignH.setAlignment(Pos.CENTER);
        alignV.getChildren().add(alignH);
        gameScreen.setCenter(alignV);
        //Game board
        BoardGui boardGui = new BoardGui(GM,numPlayers);
        alignH.getChildren().add(boardGui);
        //Right tab
        gameScreen.setRight(new SideTabGui(GM,boardGui, numPlayers));

        //Scene
        Scene scene = new Scene(gameScreen, 1280, 768);
        primaryStage.setTitle("Property Tycoon");
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setScene(scene);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}