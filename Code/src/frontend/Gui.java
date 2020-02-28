package frontend;

import backend.GameMaster;
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

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
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
        playerCombo.getItems().addAll(2,3, 4, 5, 6);
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
                System.out.println(playerCombo.getValue());
                return playerCombo.getValue();
            }
            return null;
        });
        dialogNumPlayers.showAndWait();
        // end of player number dialog box



        //start of player selection dialog box
        HBox hboxPlayerTokens = new HBox();
        Dialog<Tokens[]> dialogPlayerTokens = new Dialog<>();
        dialogPlayerTokens.setTitle("Property Tycoon");
        dialogPlayerTokens.setHeaderText("Select the tokens you want to play with.");
        dialogPlayerTokens.setResizable(false);
        Tokens[] allTokens = {Tokens.Boot, Tokens.Cat, Tokens.Goblet, Tokens.HatStand, Tokens.SmartPhone, Tokens.Spoon};
        VBox hBoxHolder = new VBox();
        HBox[] hBoxArray = new HBox[dialogNumPlayers.getResult()];
        ComboBox[] tokenCombos = new ComboBox[dialogNumPlayers.getResult()];
        for (int i =0; i<dialogNumPlayers.getResult() ;i++){
            System.out.println("loop");
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
        hBoxHolder.getChildren().addAll(hBoxArray);
        dialogPlayerTokens.getDialogPane().setContent(hBoxHolder);


        dialogPlayerTokens.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialogPlayerTokens.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                Tokens[] outputList = new Tokens[dialogNumPlayers.getResult()];
                for (int i =0; i<dialogNumPlayers.getResult() ;i++){
                    outputList[i] = (Tokens) tokenCombos[i].getValue();
                }
                return outputList;
            }
            return null;
        });
        dialogPlayerTokens.showAndWait();
        // end of player token dialog box



        //Main gameplay screen
        BorderPane gameScreen = new BorderPane();
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

        Border gameTileBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
        //Top row
        Label sq9 = new Label("Tile" + 9);
        sq9.setPrefSize(100, 100);
        sq9.setBorder(gameTileBorder);
        sq9.setAlignment(Pos.CENTER);
        Label sq10 = new Label("Tile" + 10);
        sq10.setPrefSize(100, 100);
        sq10.setBorder(gameTileBorder);
        sq10.setAlignment(Pos.CENTER);
        Label sq11 = new Label("Tile" + 11);
        sq11.setPrefSize(100, 100);
        sq11.setBorder(gameTileBorder);
        sq11.setAlignment(Pos.CENTER);
        Label sq12 = new Label("Tile" + 12);
        sq12.setPrefSize(100, 100);
        sq12.setBorder(gameTileBorder);
        sq12.setAlignment(Pos.CENTER);
        Label sq13 = new Label("Tile" + 13);
        sq13.setPrefSize(100, 100);
        sq13.setBorder(gameTileBorder);
        sq13.setAlignment(Pos.CENTER);
        Label sq14 = new Label("Tile" + 14);
        sq14.setPrefSize(100, 100);
        sq14.setBorder(gameTileBorder);
        sq14.setAlignment(Pos.CENTER);
        Label sq15 = new Label("Tile" + 15);
        sq15.setPrefSize(100, 100);
        sq15.setBorder(gameTileBorder);
        sq15.setAlignment(Pos.CENTER);
        Label sq16 = new Label("Tile" + 16);
        sq16.setPrefSize(100, 100);
        sq16.setBorder(gameTileBorder);
        sq16.setAlignment(Pos.CENTER);
        Label sq17 = new Label("Tile" + 17);
        sq17.setPrefSize(100, 100);
        sq17.setBorder(gameTileBorder);
        sq17.setAlignment(Pos.CENTER);
        Label sq18 = new Label("Tile" + 18);
        sq18.setPrefSize(100, 100);
        sq18.setBorder(gameTileBorder);
        sq18.setAlignment(Pos.CENTER);


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
        Label sq19 = new Label("Tile" + 19);
        sq19.setPrefSize(100, 100);
        sq19.setBorder(gameTileBorder);
        sq19.setAlignment(Pos.CENTER);
        Label sq20 = new Label("Tile" + 20);
        sq20.setPrefSize(100, 100);
        sq20.setBorder(gameTileBorder);
        sq20.setAlignment(Pos.CENTER);
        Label sq21 = new Label("Tile" + 21);
        sq21.setPrefSize(100, 100);
        sq21.setBorder(gameTileBorder);
        sq21.setAlignment(Pos.CENTER);
        Label sq22 = new Label("Tile" + 22);
        sq22.setPrefSize(100, 100);
        sq22.setBorder(gameTileBorder);
        sq22.setAlignment(Pos.CENTER);
        Label sq23 = new Label("Tile" + 23);
        sq23.setPrefSize(100, 100);
        sq23.setBorder(gameTileBorder);
        sq23.setAlignment(Pos.CENTER);
        Label sq24 = new Label("Tile" + 24);
        sq24.setPrefSize(100, 100);
        sq24.setBorder(gameTileBorder);
        sq24.setAlignment(Pos.CENTER);
        Label sq25 = new Label("Tile" + 25);
        sq25.setPrefSize(100, 100);
        sq25.setBorder(gameTileBorder);
        sq25.setAlignment(Pos.CENTER);
        Label sq26 = new Label("Tile" + 26);
        sq26.setPrefSize(100, 100);
        sq26.setBorder(gameTileBorder);
        sq26.setAlignment(Pos.CENTER);
        Label sq27 = new Label("Tile" + 27);
        sq27.setPrefSize(100, 100);
        sq27.setBorder(gameTileBorder);
        sq27.setAlignment(Pos.CENTER);

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
        Label sq28 = new Label("Tile" + 28);
        sq28.setPrefSize(100, 100);
        sq28.setBorder(gameTileBorder);
        sq28.setAlignment(Pos.CENTER);
        Label sq29 = new Label("Tile" + 29);
        sq29.setPrefSize(100, 100);
        sq29.setBorder(gameTileBorder);
        sq29.setAlignment(Pos.CENTER);
        Label sq30 = new Label("Tile" + 30);
        sq30.setPrefSize(100, 100);
        sq30.setBorder(gameTileBorder);
        sq30.setAlignment(Pos.CENTER);
        Label sq31 = new Label("Tile" + 31);
        sq31.setPrefSize(100, 100);
        sq31.setBorder(gameTileBorder);
        sq31.setAlignment(Pos.CENTER);
        Label sq32 = new Label("Tile" + 32);
        sq32.setPrefSize(100, 100);
        sq32.setBorder(gameTileBorder);
        sq32.setAlignment(Pos.CENTER);
        Label sq33 = new Label("Tile" + 33);
        sq33.setPrefSize(100, 100);
        sq33.setBorder(gameTileBorder);
        sq33.setAlignment(Pos.CENTER);
        Label sq34 = new Label("Tile" + 34);
        sq34.setPrefSize(100, 100);
        sq34.setBorder(gameTileBorder);
        sq34.setAlignment(Pos.CENTER);
        Label sq35 = new Label("Tile" + 35);
        sq35.setPrefSize(100, 100);
        sq35.setBorder(gameTileBorder);
        sq35.setAlignment(Pos.CENTER);
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





        //Sprite objects


        Image image1 = new Image("player_boot.png", 30, 30, false, true);
        ImageView player1 = new ImageView(image1);
        Image image2 = new Image("player_cat.png",30, 30, false, true);
        ImageView player2 = new ImageView(image2);


        //Sprite movement
        Tokens[] tokList = new Tokens[2];
        tokList[0] = Tokens.Cat;
        tokList[1] = Tokens.Boot;

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
        GM.setup(playerCombo.getValue(), tokList);
        sq0.getChildren().add(player1);
        sq0.getChildren().add(player2);

        diceRoll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ImageView activePlayer = getActivePlayer(GM.getCurTurn());
                GM.moveNextPiece();
                sq0.getChildren().remove(GM.getCurTurn());

                int[] newCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());

                for (Node n : gp.getChildren()) {
                    if (n == getNodeByRowColumnIndex(newCoords[0], newCoords[1], gp)){
                        gp.getChildren().add(GM.getPlayer(GM.getCurTurn()).getPlace(), activePlayer);
                    }
                }

            }
        });

        nextTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GM.nextTurn();
            }
        });
    }
    //gm.getPlayer(gm.getCurTurn()).getPos()
    public ImageView getActivePlayer(int n){
        ImageView activePlayer = null;
        if(n == 0){
            Image image1 = new Image("player_boot.png", 30, 30, false, true);
            ImageView player1 = new ImageView(image1);
            activePlayer = player1;
        } else if (n == 1){
            Image image2 = new Image("player_cat.png",30, 30, false, true);
            ImageView player2 = new ImageView(image2);
            activePlayer = player2;
        }
        return activePlayer;
    }

    // code used from https://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column answer 1
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    public int[] coords(int position){
        return new int[]{0,0};
    }
}
