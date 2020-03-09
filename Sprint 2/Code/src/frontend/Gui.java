package frontend;

import backend.GameMaster;
import backend.Player;
import backend.Tokens;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.Node;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
 * @author Jonathan Morris
 */
public class Gui extends Application {

    backend.GameMaster GM;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //setup dialogues
        int numPlayers = doDialoguePlayers();
        Tokens[] playerTokens = doPlayerTokens(numPlayers);
        GM = new backend.GameMaster();
        GM.setup(numPlayers, playerTokens);

        //Main gameplay screen
        BorderPane gameScreen = new BorderPane();
        //bottom setup
        HBox bottom = new HBox();
        gameScreen.setBottom(bottom);
        Button diceRollNextTurn = new Button("Roll dice");
        diceRollNextTurn.setPrefSize(220, 120);
        bottom.getChildren().add(diceRollNextTurn);
        bottom.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(100);
        bottom.setPadding(new Insets(20, 0, 20, 0));

        //Players tab
        //add active players and maybe highlight current turn player
        VBox playerTab = new VBox();
        gameScreen.setRight(playerTab);
        playerTab.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        playerTab.setPrefWidth(400);

        Label pTabTitle = new Label("Players:");
        playerTab.getChildren().add(pTabTitle);
        //playerTab.setAlignment(Pos.CENTER);


        //Game board
        GridPane gp = new GridPane();
        gameScreen.setCenter(gp);

        //board Tiles
        Border gameTileBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
        for(int i=0; i<40; i++) {
            Pane square = new Pane();
            Label label = new Label(GM.getBoard().getTile(i).getName());
            square.setPrefSize(100,100);
            square.setBorder(gameTileBorder);
            label.setAlignment(Pos.CENTER);
            square.getChildren().add(label);
            gp.add(square, coords(i)[0], coords(i)[1]);
        }

        gp.setGridLinesVisible(false);
        gp.setMaxSize(1000, 1000);
        gp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        //Scene
        Scene scene = new Scene(gameScreen, 900, 600);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Property Tycoon");
        primaryStage.getIcons().add(new Image("tile_go.png"));
        primaryStage.setScene(scene);
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.show();
        Stage stage = primaryStage;

        // Setting initial position of tokens
        Player[] players = GM.getPlayers();
        ImageView[] playerImages = new ImageView[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerImages[i] = tokenImage(players[i].getToken());
            Iterator<Node> children = gp.getChildren().iterator();
            while(children.hasNext()){
                Node n = children.next();
                if (gp.getRowIndex(n) == coords(0)[1] && gp.getColumnIndex(n) == coords(0)[0]){
                    ((Pane) n).getChildren().add(playerImages[i]);
                }
            }
        }

        diceRollNextTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public synchronized void handle(ActionEvent event) {
                if(!GM.isCanNextTurn()) {
                    ImageView playerSprite = playerImages[GM.getCurTurn()];
                    int[] oldCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());
                    Iterator<Node> children = gp.getChildren().iterator();
                    while (children.hasNext()) {
                        Node n = children.next();
                        if (gp.getRowIndex(n) == oldCoords[1] && gp.getColumnIndex(n) == oldCoords[0]) {
                            ((Pane) n).getChildren().remove(playerSprite);
                            break;
                        }
                    }
                    GM.moveNextPiece();
                    int[] newCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());
                    //Look at each Pane() object within gridPane()
                    while (children.hasNext()) {
                        Node n = children.next();
                        if (gp.getRowIndex(n) == newCoords[1] && gp.getColumnIndex(n) == newCoords[0]) { //
                            ((Pane) n).getChildren().add(playerSprite);
                            break;
                        }
                        else {

                            try {
                                wait(500);
                                ((Pane) n).getChildren().add(playerSprite);
                                wait(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                System.out.println("ahhh");
                            }
                            ((Pane) n).getChildren().remove(playerSprite);
                        }
                        if(!children.hasNext()) {
                            children = gp.getChildren().iterator();
                        }
                    }
                    diceRollNextTurn.setText("Next Turn");
                }
                else {
                    GM.nextTurn();
                    diceRollNextTurn.setText("Roll Dice");
                }

            }
        });
    }

    /**
     * Takes the player token and returns an ImageView for that Token
     * @param playerToken
     */
    public ImageView tokenImage(Tokens playerToken){
        ImageView out = null;
        if(playerToken.equals(Tokens.Boot)){
            Image img = new Image("player_boot.png", 30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.Goblet)){
            Image img = new Image("player_goblet.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.Cat)){
            Image img = new Image("player_cat.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.HatStand)){
            Image img = new Image("player_hatstand.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.SmartPhone)){
            Image img = new Image("player_smartphone.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.Spoon)){
            Image img = new Image("player_spoon.png",30, 30, false, true);
            out = new ImageView(img);
        }
        return out;
    }

    //gm.getPlayer(gm.getCurTurn()).getPos()
    //instance of images seperate and use them in if statements to give players a token image
    public Player getActivePlayer(int currentRound, Player[] players){
        return players[currentRound];
    }

    /**
     * returns current position in grid pane
     * @param position
     * @return int[]
     * @author Joe C
     * @author Joe L
     */
    public int[] coords(int position){
        if (position <= 9){ return new int[]{0,10 - position % 10}; }
        else if (position <= 19){ return new int[]{position % 10, 0}; }
        else if (position <= 29){ return new int[]{10, position % 10};}
        else if (position <= 39){ return new int[]{10 - position % 10, 10};}
        else {return new int[]{0,0};}
    }

    /**
     * This is the first screen of the game. Returns the number of players
     * @return int
     * @author Jonathan Morris
     * @author Alex Homer
     * @author Joe Corbett
     */
    private int doDialoguePlayers(){
        //Initialization
        Dialog<Integer> dialogNumPlayers = new Dialog<>();
        Stage localStage = (Stage) dialogNumPlayers.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("tile_go.png"));
        Label playerLabel = new Label("Player Count: ");
        ComboBox<Integer> playerCombo = new ComboBox<>();
        HBox hboxPlayerCount = new HBox();
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        //modifying of initialized variables
        dialogNumPlayers.setTitle("Property Tycoon");
        dialogNumPlayers.setHeaderText("Select the number of players.");
        dialogNumPlayers.setResizable(false);
        playerCombo.getItems().addAll(2,3, 4, 5, 6);
        playerCombo.setValue(2);
        HBox.setHgrow(playerLabel, Priority.ALWAYS);
        HBox.setHgrow(playerCombo, Priority.ALWAYS);
        hboxPlayerCount.setMaxHeight(50);
        hboxPlayerCount.setMinHeight(50);
        hboxPlayerCount.setSpacing(15);
        hboxPlayerCount.setAlignment(Pos.CENTER);
        hboxPlayerCount.getChildren().addAll(playerLabel, playerCombo);
        dialogNumPlayers.getDialogPane().setContent(hboxPlayerCount);
        dialogNumPlayers.getDialogPane().getButtonTypes().add(buttonTypeOk);
        //lambda expressions
        dialogNumPlayers.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                System.out.println(playerCombo.getValue());
                return playerCombo.getValue();
            }
            return null;
        });
        //wait for return
        dialogNumPlayers.showAndWait();
        //return value
        return dialogNumPlayers.getResult();
        // end of player number dialog box
    }

    /**
     * Takes an integer and creates a dialogue box for assigning tokens to players
     * @param players
     * @return Token array
     * @author Jonathan Morris
     * @author Alex Homer
     * @author Joe Corbett
     */
    private Tokens[] doPlayerTokens(int players){
        //initializing
        Dialog<Tokens[]> dialogPlayerTokens = new Dialog<>();
        Stage localStage = (Stage) dialogPlayerTokens.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("tile_go.png"));
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Label playerLabel;
        Tokens[] allTokens = {Tokens.Boot, Tokens.Cat, Tokens.Goblet, Tokens.HatStand, Tokens.SmartPhone, Tokens.Spoon};
        VBox hBoxHolder = new VBox();
        HBox[] hBoxArray = new HBox[players];
        ComboBox[] tokenCombos = new ComboBox[players];

        //modifying initialized variables
        dialogPlayerTokens.setTitle("Property Tycoon");
        dialogPlayerTokens.setHeaderText("Select the tokens you want to play with.");
        dialogPlayerTokens.setResizable(false);
        //creating box's for each player
        for (int i =0; i<players ;i++){
            playerLabel = new Label("Player " + (i+1));
            hBoxArray[i] = new HBox();
            hBoxArray[i].setMaxHeight(50);
            hBoxArray[i].setMinHeight(50);
            hBoxArray[i].setSpacing(15);
            hBoxArray[i].setAlignment(Pos.CENTER);
            tokenCombos[i]= new ComboBox<Tokens>();
            tokenCombos[i].getItems().addAll(allTokens);
            tokenCombos[i].setValue(allTokens[i]);
            hBoxArray[i].getChildren().addAll(playerLabel, tokenCombos[i]);
        }
        //adding as children
        hBoxHolder.getChildren().addAll(hBoxArray);
        dialogPlayerTokens.getDialogPane().setContent(hBoxHolder);
        dialogPlayerTokens.getDialogPane().getButtonTypes().add(buttonTypeOk);
        //lambda expression
        dialogPlayerTokens.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                Tokens[] outputList = new Tokens[players];
                for (int i =0; i<players ;i++){
                    outputList[i] = (Tokens) tokenCombos[i].getValue();
                }
                return outputList;
            }
            return null;
        });
        //wait for response
        dialogPlayerTokens.showAndWait();
        //return values
        return dialogPlayerTokens.getResult();
        // end of player token dialog box
    }
}