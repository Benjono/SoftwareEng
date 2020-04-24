package frontend;

import backend.Tokens;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        Scene scene = new Scene(gameScreen, 1024, 768);
        primaryStage.setTitle("Property Tycoon");
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setScene(scene);
        // XGA screen size 1024 x 768
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}