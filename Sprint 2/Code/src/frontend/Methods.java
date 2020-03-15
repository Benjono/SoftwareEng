package frontend;

import backend.GameMaster;
import backend.Player;
import backend.Tokens;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Iterator;

public class Methods {

    /**
     * This is the first screen of the game. Returns the number of players
     * @return int
     * @author Jonathan Morris
     * @author Alex Homer
     * @author Joe Corbett
     */
    public int doDialoguePlayers(){
        //Initialization
        Dialog<Integer> dialogNumPlayers = new Dialog<>();
        Stage localStage = (Stage) dialogNumPlayers.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("logo.png"));
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
                //System.out.println(playerCombo.getValue());
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
    public Tokens[] doPlayerTokens(int players){
        //initializing
        Dialog<Tokens[]> dialogPlayerTokens = new Dialog<>();
        Stage localStage = (Stage) dialogPlayerTokens.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("logo.png"));
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

    public Boolean landOnBrought(GameMaster GM){
        Dialog<Boolean> landed = new Dialog<Boolean>();
        Stage localStage = (Stage) landed.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("logo.png"));
        landed.setTitle("Property Tycoon");
        landed.setResizable(false);
        landed.setHeaderText("You Landed on " + GM.getTile(GM.getPlayer(GM.getCurTurn()).getPlace()).getName());
        //Label amount = new Label("Buy for " + (GM.getBoard().getTileGrid())[GM.getPlayer(GM.getCurTurn()).getPlace()]
        //Label auction =
        ButtonType buyButton = new ButtonType("BUY", ButtonBar.ButtonData.YES);
        ButtonType auctionButton = new ButtonType("AUCTION", ButtonBar.ButtonData.NO);
        landed.getDialogPane().getButtonTypes().addAll(buyButton,auctionButton);
        landed.setResultConverter((ButtonType b) -> {
            if (b == buyButton){
                return true;
            }
            return false;
        });
        //wait for return
        landed.showAndWait();
        //return value
        return landed.getResult();
        // end of player number dialog box

    }

    public void showTileInfo(int i){
        //Dialog<>
    }

    public synchronized void waitBetweenMovements(){

        try {
            wait(1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ahhh");
        }
    }

    /**
     * Implements rotation of the Board
     * @param GM
     * @param  gp
     * @author Joe C
     */
    public GridPane setBoardRotation(GameMaster GM, GridPane gp){
        int place = GM.getPlayer(GM.getCurTurn()).getPlace();
        if(place < 10){ gp.setRotate(270); }
        else if (place < 20){ gp.setRotate(180);}
        else if (place < 31){ gp.setRotate(90);}
        else{ gp.setRotate(0);}
        return gp;
    }

    /**
     * Implements rotation of the Player sprite (same as above I know...)
     * @param GM
     * @param  imageView
     * @author Joe C
     */
    public ImageView setSpriteRotation(GameMaster GM, ImageView imageView){
        int place = GM.getPlayer(GM.getCurTurn()).getPlace();
        if(place < 10){ imageView.setRotate(90); }
        else if (place < 20){ imageView.setRotate(180);}
        else if (place < 31){ imageView.setRotate(270);}
        else{ imageView.setRotate(0);}
        return imageView;

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

    /**
     * adds a highlighted background to the label that represents the currently active player in the player tab
     * @param playerNum
     * @param playerList
     * @author Joe L
     */
    public void changeActiveColour(int playerNum, Label[] playerList){
        for(int i = 0; i < playerList.length; i++){
            playerList[i].setStyle("-fx-background-color: white; -fx-text-fill: black;");
        }
        playerList[playerNum].setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
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

    public GridPane movePlayer(GameMaster GM, ImageView[] playerImages, GridPane gp){
        ImageView playerSprite = playerImages[GM.getCurTurn()];
        int[] oldCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());
        Iterator<Node> children = gp.getChildren().iterator();
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == oldCoords[1] && GridPane.getColumnIndex(n) == oldCoords[0]) {
                //((Pane) n).getChildren().remove(playerSprite);
                break;
            }
        }
        GM.moveNextPiece();
        int[] newCoords = coords(GM.getPlayer(GM.getCurTurn()).getPlace());
        //Look at each Pane() object within gridPane()
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == newCoords[1] && GridPane.getColumnIndex(n) == newCoords[0]) { //
                //System.out.println("ok");
                playerSprite = setSpriteRotation(GM,playerSprite);
                ((Pane) n).getChildren().add(playerSprite);
                landedTileAction(GM);
                break;
            } else {
                //System.out.println("err");
                playerSprite = setSpriteRotation(GM,playerSprite);
                ((Pane) n).getChildren().add(playerSprite);
                //m.waitBetweenMovements();
                //((Pane) n).getChildren().remove(playerSprite);
            }
            if (!children.hasNext()) {
                children = gp.getChildren().iterator();
            }
        }

        return gp;
    }

    private void landedTileAction(GameMaster GM) {
        if (GM.getBuyable(GM.getPlayer(GM.getCurTurn()).getPlace()) == 2){
            //can buy/auction time
            if(!landOnBrought(GM)){
                // auction

                // dialog

                //GM.applyTileEffect();
            }
            else {
                GM.applyTileEffect(GM.getPlayer(GM.getCurTurn()));
            }
        }
        else{
            //rent time
        }
    }

}
