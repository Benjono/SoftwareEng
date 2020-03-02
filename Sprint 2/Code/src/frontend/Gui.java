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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //setup dialogues
        int numPlayers = doDialoguePlayers();
        Tokens[] playerTokens = doPlayerTokens(numPlayers);

        //Main gameplay screen
        BorderPane gameScreen = new BorderPane();
        //bottom setup
        HBox bottom = new HBox();
        gameScreen.setBottom(bottom);
        Button diceRoll = new Button("Roll dice");
        diceRoll.setPrefSize(220, 120);
        Button nextTurn = new Button("Next turn");
        nextTurn.setPrefSize(220, 120);
        bottom.getChildren().addAll(diceRoll, nextTurn);
        bottom.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(1000);
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
        //make is 11 x 11 possibly?

        Border gameTileBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
        //Top row
        Pane sq9 = new Pane();
        Label l9 = new Label("Tile" + 9);
        sq9.setPrefSize(100, 100);
        sq9.setBorder(gameTileBorder);
        l9.setAlignment(Pos.CENTER);
        Pane sq10 = new Pane();
        Label l10 = new Label("Tile" + 10);
        sq10.setPrefSize(100, 100);
        sq10.setBorder(gameTileBorder);
        l10.setAlignment(Pos.CENTER);
        Pane sq11 = new Pane();
        Label l11 = new Label("Tile" + 11);
        sq11.setPrefSize(100, 100);
        sq11.setBorder(gameTileBorder);
        l11.setAlignment(Pos.CENTER);
        Pane sq12 = new Pane();
        Label l12 = new Label("Tile" + 12);
        sq12.setPrefSize(100, 100);
        sq12.setBorder(gameTileBorder);
        l12.setAlignment(Pos.CENTER);
        Pane sq13 = new Pane();
        Label l13 = new Label("Tile" + 13);
        sq13.setPrefSize(100, 100);
        sq13.setBorder(gameTileBorder);
        l13.setAlignment(Pos.CENTER);
        Pane sq14 = new Pane();
        Label l14 = new Label("Tile" + 14);
        sq14.setPrefSize(100, 100);
        sq14.setBorder(gameTileBorder);
        l14.setAlignment(Pos.CENTER);
        Pane sq15 = new Pane();
        Label l15 = new Label("Tile" + 15);
        sq15.setPrefSize(100, 100);
        sq15.setBorder(gameTileBorder);
        l15.setAlignment(Pos.CENTER);
        Pane sq16 = new Pane();
        Label l16 = new Label("Tile" + 16);
        sq16.setPrefSize(100, 100);
        sq16.setBorder(gameTileBorder);
        l16.setAlignment(Pos.CENTER);
        Pane sq17 = new Pane();
        Label l17 = new Label("Tile" + 17);
        sq17.setPrefSize(100, 100);
        sq17.setBorder(gameTileBorder);
        l17.setAlignment(Pos.CENTER);
        Pane sq18 = new Pane();
        Label l18 = new Label("Tile" + 18);
        sq18.setPrefSize(100, 100);
        sq18.setBorder(gameTileBorder);
        l18.setAlignment(Pos.CENTER);


        gp.add(sq9, 0, 0);
        gp.add(sq10, 1, 0);
        gp.add(sq11, 2, 0);
        gp.add(sq12, 3, 0);
        gp.add(sq13, 4, 0);
        gp.add(sq14, 5, 0);
        gp.add(sq15, 6, 0);
        gp.add(sq16, 7, 0);
        gp.add(sq17, 8, 0);
        gp.add(sq18, 9, 0);

        //Right column
        Pane sq19 = new Pane();
        Label l19 = new Label("Tile" + 19);
        sq19.setPrefSize(100, 100);
        sq19.setBorder(gameTileBorder);
        l19.setAlignment(Pos.CENTER);
        Pane sq20 = new Pane();
        Label l20 = new Label("Tile" + 20);
        sq20.setPrefSize(100, 100);
        sq20.setBorder(gameTileBorder);
        l20.setAlignment(Pos.CENTER);
        Pane sq21 = new Pane();
        Label l21 = new Label("Tile" + 21);
        sq21.setPrefSize(100, 100);
        sq21.setBorder(gameTileBorder);
        l21.setAlignment(Pos.CENTER);
        Pane sq22 = new Pane();
        Label l22 = new Label("Tile" + 22);
        sq22.setPrefSize(100, 100);
        sq22.setBorder(gameTileBorder);
        l22.setAlignment(Pos.CENTER);
        Pane sq23 = new Pane();
        Label l23 = new Label("Tile" + 23);
        sq23.setPrefSize(100, 100);
        sq23.setBorder(gameTileBorder);
        l23.setAlignment(Pos.CENTER);
        Pane sq24 = new Pane();
        Label l24 = new Label("Tile" + 24);
        sq24.setPrefSize(100, 100);
        sq24.setBorder(gameTileBorder);
        l24.setAlignment(Pos.CENTER);
        Pane sq25 = new Pane();
        Label l25 = new Label("Tile" + 25);
        sq25.setPrefSize(100, 100);
        sq25.setBorder(gameTileBorder);
        l25.setAlignment(Pos.CENTER);
        Pane sq26 = new Pane();
        Label l26 = new Label("Tile" + 26);
        sq26.setPrefSize(100, 100);
        sq26.setBorder(gameTileBorder);
        l26.setAlignment(Pos.CENTER);
        Pane sq27 = new Pane();
        Label l27 = new Label("Tile" + 27);
        sq27.setPrefSize(100, 100);
        sq27.setBorder(gameTileBorder);
        l27.setAlignment(Pos.CENTER);

        gp.add(sq19, 9, 1);
        gp.add(sq20, 9, 2);
        gp.add(sq21, 9, 3);
        gp.add(sq22, 9, 4);
        gp.add(sq23, 9, 5);
        gp.add(sq24, 9, 6);
        gp.add(sq25, 9, 7);
        gp.add(sq26, 9, 8);
        gp.add(sq27, 9, 9);
        //bottom row
        Pane sq28 = new Pane();
        Label l28 = new Label("Tile" + 28);
        sq28.setPrefSize(100, 100);
        sq28.setBorder(gameTileBorder);
        l28.setAlignment(Pos.CENTER);
        Pane sq29 = new Pane();
        Label l29 = new Label("Tile" + 29);
        sq29.setPrefSize(100, 100);
        sq29.setBorder(gameTileBorder);
        l29.setAlignment(Pos.CENTER);
        Pane sq30 = new Pane();
        Label l30 = new Label("Tile" + 30);
        sq30.setPrefSize(100, 100);
        sq30.setBorder(gameTileBorder);
        l30.setAlignment(Pos.CENTER);
        Pane sq31 = new Pane();
        Label l31 = new Label("Tile" + 31);
        sq31.setPrefSize(100, 100);
        sq31.setBorder(gameTileBorder);
        l31.setAlignment(Pos.CENTER);
        Pane sq32 = new Pane();
        Label l32 = new Label("Tile" + 32);
        sq32.setPrefSize(100, 100);
        sq32.setBorder(gameTileBorder);
        l32.setAlignment(Pos.CENTER);
        Pane sq33 = new Pane();
        Label l33 = new Label("Tile" + 33);
        sq33.setPrefSize(100, 100);
        sq33.setBorder(gameTileBorder);
        l33.setAlignment(Pos.CENTER);
        Pane sq34 = new Pane();
        Label l34 = new Label("Tile" + 34);
        sq34.setPrefSize(100, 100);
        sq34.setBorder(gameTileBorder);
        l34.setAlignment(Pos.CENTER);
        Pane sq35 = new Pane();
        Label l35 = new Label("Tile" + 35);
        sq35.setPrefSize(100, 100);
        sq35.setBorder(gameTileBorder);
        l35.setAlignment(Pos.CENTER);
        Pane sq0 = new Pane();
        Label l0 = new Label("Tile" + 0);
        sq0.getChildren().add(l0);
        sq0.setPrefSize(100, 100);
        sq0.setBorder(gameTileBorder);
        l0.setAlignment(Pos.CENTER);

        gp.add(sq0, 0, 9);
        gp.add(sq35, 1, 9);
        gp.add(sq34, 2, 9);
        gp.add(sq33, 3, 9);
        gp.add(sq32, 4, 9);
        gp.add(sq31, 5, 9);
        gp.add(sq30, 6, 9);
        gp.add(sq29, 7, 9);
        gp.add(sq28, 8, 9);
        //change rest of squares to be panes
        //Left column
        Pane sq1 = new Pane();
        Label l1 = new Label("Tile" + 1);
        sq1.getChildren().add(l1);
        sq1.setPrefSize(100, 100);
        sq1.setBorder(gameTileBorder);
        l1.setAlignment(Pos.CENTER);
        Pane sq2 = new Pane();
        Label l2 = new Label("Tile" + 2);
        sq2.getChildren().add(l2);
        sq2.setPrefSize(100, 100);
        sq2.setBorder(gameTileBorder);
        l2.setAlignment(Pos.CENTER);
        Pane sq3 = new Pane();
        Label l3 = new Label("Tile" + 3);
        sq3.getChildren().add(l3);
        sq3.setPrefSize(100, 100);
        sq3.setBorder(gameTileBorder);
        l3.setAlignment(Pos.CENTER);
        Pane sq4 = new Pane();
        Label l4 = new Label("Tile" + 4);
        sq4.getChildren().add(l4);
        sq4.setPrefSize(100, 100);
        sq4.setBorder(gameTileBorder);
        l4.setAlignment(Pos.CENTER);
        Pane sq5 = new Pane();
        Label l5 = new Label("Tile" + 5);
        sq5.getChildren().add(l5);
        sq5.setPrefSize(100, 100);
        sq5.setBorder(gameTileBorder);
        l5.setAlignment(Pos.CENTER);
        Pane sq6 = new Pane();
        Label l6 = new Label("Tile" + 6);
        sq6.getChildren().add(l6);
        sq6.setPrefSize(100, 100);
        sq6.setBorder(gameTileBorder);
        l6.setAlignment(Pos.CENTER);
        Pane sq7 = new Pane();
        Label l7 = new Label("Tile" + 7);
        sq7.getChildren().add(l7);
        sq7.setPrefSize(100, 100);
        sq7.setBorder(gameTileBorder);
        l7.setAlignment(Pos.CENTER);
        Pane sq8 = new Pane();
        Label l8 = new Label("Tile" + 8);
        sq8.getChildren().add(l8);
        sq8.setPrefSize(100, 100);
        sq8.setBorder(gameTileBorder);
        l8.setAlignment(Pos.CENTER);

        gp.add(sq8, 0, 1);
        gp.add(sq7, 0, 2);
        gp.add(sq6, 0, 3);
        gp.add(sq5, 0, 4);
        gp.add(sq4, 0, 5);
        gp.add(sq3, 0, 6);
        gp.add(sq2, 0, 7);
        gp.add(sq1, 0, 8);

        gp.setGridLinesVisible(false);

        gp.setMaxSize(1000, 1000);
        gp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));


        //Scene
        Scene scene = new Scene(gameScreen, 900, 600);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Property Tycoon");
        primaryStage.setScene(scene);
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.show();
        Stage stage = primaryStage;


        backend.GameMaster GM = new backend.GameMaster();
        GM.setup(numPlayers, playerTokens);
        Player[] players = GM.getPlayers();
        ImageView[] playerImages = new ImageView[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerImages[i] = tokenImage(players[i].getToken());
            sq0.getChildren().add(playerImages[i]);
        }

        diceRoll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ImageView playerSprite = playerImages[GM.getCurTurn()];
                int[] oldCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());
                Iterator<Node> children = gp.getChildren().iterator();
                while(children.hasNext()){
                    Node n = children.next();
                    if (gp.getRowIndex(n) == oldCoords[0] && gp.getColumnIndex(n) == oldCoords[1]){
                        ((Pane) n).getChildren().remove(playerSprite);
                    }
                }
                GM.moveNextPiece();
                children = gp.getChildren().iterator();
                int[] newCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());
                //Look at each Pane() object within gridPane()
                while(children.hasNext()){
                    Node n = children.next();
                    if (gp.getRowIndex(n) == newCoords[0] && gp.getColumnIndex(n) == newCoords[1]){ //
                        ((Pane) n).getChildren().add(playerSprite);
                    }
                }
            }
        });
        //display which player's turn it is
        nextTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GM.nextTurn();
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
        Player activePlayer = null;
        switch (currentRound){
            case 0:
                activePlayer = players[0];
                break;
            case 1:
                activePlayer = players[1];
                break;
            case 2:
                activePlayer = players[2];
                break;
            case 3:
                activePlayer = players[3];
                break;
            case 4:
                activePlayer = players[4];
                break;
            case 5:
                activePlayer = players[5];
                break;
        }
        return activePlayer;
    }
    //add switch statements from seperate class
    public int[] coords(int position){
        int[] coPos = new int[2];
        switch(position) {
            case 0:
                coPos[0] = 9;
                coPos[1] = 0;
                break;
            case 1:
                coPos[0] = 8;
                coPos[1] = 0;
                break;
            case 2:
                coPos[0] = 7;
                coPos[1] = 0;
                break;
            case 3:
                coPos[0] = 6;
                coPos[1] = 0;
                break;
            case 4:
                coPos[0] = 5;
                coPos[1] = 0;
                break;
            case 5:
                coPos[0] = 4;
                coPos[1] = 0;
                break;
            case 6:
                coPos[0] = 3;
                coPos[1] = 0;
                break;
            case 7:
                coPos[0] = 2;
                coPos[1] = 0;
                break;
            case 8:
                coPos[0] = 1;
                coPos[1] = 0;
                break;
            case 9:
                coPos[0] = 0;
                coPos[1] = 0;
                break;
            case 10:
                coPos[0] = 0;
                coPos[1] = 1;
                break;
            case 11:
                coPos[0] = 0;
                coPos[1] = 2;
                break;
            case 12:
                coPos[0] = 0;
                coPos[1] = 3;
                break;
            case 13:
                coPos[0] = 0;
                coPos[1] = 4;
                break;
            case 14:
                coPos[0] = 0;
                coPos[1] = 5;
                break;
            case 15:
                coPos[0] = 0;
                coPos[1] = 6;
                break;
            case 16:
                coPos[0] = 0;
                coPos[1] = 7;
                break;
            case 17:
                coPos[0] = 0;
                coPos[1] = 8;
                break;
            case 18:
                coPos[0] = 0;
                coPos[1] = 9;
                break;
            case 19:
                coPos[0] = 1;
                coPos[1] = 9;
                break;
            case 20:
                coPos[0] = 2;
                coPos[1] = 9;
                break;
            case 21:
                coPos[0] = 3;
                coPos[1] = 9;
                break;
            case 22:
                coPos[0] = 4;
                coPos[1] = 9;
                break;
            case 23:
                coPos[0] = 5;
                coPos[1] = 9;
                break;
            case 24:
                coPos[0] = 6;
                coPos[1] = 9;
                break;
            case 25:
                coPos[0] = 7;
                coPos[1] = 9;
                break;
            case 26:
                coPos[0] = 8;
                coPos[1] = 9;
                break;
            case 27:
                coPos[0] = 9;
                coPos[1] = 9;
                break;
            case 28:
                coPos[0] = 9;
                coPos[1] = 8;
                break;
            case 29:
                coPos[0] = 9;
                coPos[1] = 7;
                break;
            case 30:
                coPos[0] = 9;
                coPos[1] = 6;
                break;
            case 31:
                coPos[0] = 9;
                coPos[1] = 5;
                break;
            case 32:
                coPos[0] = 9;
                coPos[1] = 4;
                break;
            case 33:
                coPos[0] = 9;
                coPos[1] = 3;
                break;
            case 34:
                coPos[0] = 9;
                coPos[1] = 2;
                break;
            case 35:
                coPos[0] = 9;
                coPos[1] = 1;
                break;
            default:
                coPos[0]=0;
                coPos[1]=0;
        }
        System.out.println(coPos[0]+" "+coPos[1]);
        return coPos;
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
            System.out.println("loop"); //testing print
            playerLabel = new Label("Player " + (i+1));
            hBoxArray[i] = new HBox();
            hBoxArray[i].setMaxHeight(50);
            hBoxArray[i].setMinHeight(50);
            hBoxArray[i].setSpacing(15);
            hBoxArray[i].setAlignment(Pos.CENTER);
            tokenCombos[i]= new ComboBox<Tokens>();
            tokenCombos[i].getItems().addAll(allTokens);
            tokenCombos[i].setValue(Tokens.Boot);
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