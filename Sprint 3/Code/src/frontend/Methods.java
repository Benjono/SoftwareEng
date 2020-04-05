package frontend;

import backend.BuyableTile;
import backend.GameMaster;
import backend.Tokens;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Iterator;

public class Methods {

    /**
     * Sets up the dialogs with header text the logo and not being able to be resized
     * @param d
     * @return d
     * @author Joe C
     */
    public Dialog setupDialog(Dialog d){
        Stage localStage = (Stage) d.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("logo.png"));
        d.setTitle("Property Tycoon");
        d.setResizable(false);
        return d;
    }

    /**
     * This is the first screen of the game. Returns the number of players
     * @return int
     * @author Jonathan Morris
     * @author Alex Homer
     * @author Joe Corbett
     */
    public int doDialoguePlayers(){
        //Initialization
        Dialog<Integer> dialogNumPlayers = setupDialog(new Dialog<>());
        Label playerLabel = new Label("Player Count: ");
        ComboBox<Integer> playerCombo = new ComboBox<>();
        HBox hboxPlayerCount = new HBox();
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        //modifying of initialized variables
        dialogNumPlayers.setHeaderText("Select the number of players.");
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
     * GridPane could be used instead of HBox VBox combination
     * @param players
     * @return Token array
     * @author Jonathan Morris
     * @author Alex Homer
     * @author Joe Corbett
     */
    public Tokens[] doPlayerTokens(int players){
        //initializing
        Dialog<Tokens[]> dialogPlayerTokens = setupDialog(new Dialog<>());
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Label playerLabel;
        Tokens[] allTokens = {Tokens.Boot, Tokens.Cat, Tokens.Goblet, Tokens.HatStand, Tokens.SmartPhone, Tokens.Spoon};
        VBox hBoxHolder = new VBox();
        HBox[] hBoxArray = new HBox[players];
        ComboBox[] tokenCombos = new ComboBox[players];
        dialogPlayerTokens.setHeaderText("Select the tokens you want to play with.");
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

    /**
     * Implements landing on an unowned tile, with options to buy or auction
     * @param GM
     * @return
     */
    public Boolean landOnBrought(GameMaster GM){
        Dialog<Boolean> landed = setupDialog(new Dialog<>());
        landed.setHeaderText("You Landed on " + GM.getTile(GM.getPlayer(GM.getCurTurn()).getPlace()).getName());
        landed.setContentText("Buy for " + ((BuyableTile)(GM.getBoard().getTileGrid())[GM.getPlayer(GM.getCurTurn()).getPlace()]).getCostToBuy());
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

    /**
     * Implements auction dialog
     * GridPane could be used instead of HBox VBox combination
     * @param GM
     * @return
     */
    public int[] doAuction(GameMaster GM){
        Dialog<int[]> auction = setupDialog(new Dialog<>());
        auction.setHeaderText("Auction of " + GM.getTile(GM.getPlayer(GM.getCurTurn()).getPlace()).getName());
        Label playerLabel = new Label("Enter amount in box, put 0 to not participate");
        VBox hBoxHolder = new VBox();
        hBoxHolder.getChildren().add(playerLabel);
        HBox[] hBoxArray = new HBox[GM.getPlayers().length];
        NumField[] bets = new NumField[GM.getPlayers().length];
        //creating box's for each player
        for (int i =0; i<GM.getPlayers().length ;i++){
            playerLabel = new Label("Player " + (i+1) + " Amount:");
            hBoxArray[i] = new HBox();
            hBoxArray[i].setMaxHeight(50);
            hBoxArray[i].setMinHeight(50);
            hBoxArray[i].setSpacing(15);
            hBoxArray[i].setAlignment(Pos.CENTER);
            bets[i] = new NumField();
            hBoxArray[i].getChildren().addAll(playerLabel, bets[i]);
        }
        //adding as children
        hBoxHolder.getChildren().addAll(hBoxArray);
        auction.getDialogPane().setContent(hBoxHolder);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        auction.getDialogPane().getButtonTypes().add(buttonTypeOk);

        auction.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                int[] moneyBets = new int[GM.getPlayers().length];
                for (int i =0; i<GM.getPlayers().length-1 ;i++){
                    moneyBets[i] = Integer.parseInt(bets[i].getCharacters().toString());
                }
                return moneyBets;
            }
            return null;
        });
        // show dialog
        auction.showAndWait();

        // get array of bets
        return auction.getResult();

    }

    private void showRoll(int[] roll, GameMaster GM){
        Dialog rollDialog = setupDialog(new Dialog());
        rollDialog.setHeaderText("Dice Roll");
        rollDialog.setContentText("Player " + (GM.getCurTurn()+1) + " just rolled a " + roll[0] + " and a " + roll[1] + "!");
        ButtonType ok = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
        rollDialog.getDialogPane().getButtonTypes().add(ok);
        rollDialog.showAndWait();

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
     * updates player's money, i here relates to the ith row in the list
     * @param GM
     * @param sideTab
     * @author Joe L
     * @author Joe C
     */

    public void updateSideTab(GameMaster GM, VBox sideTab){
        int playerNumber = 0;
        for(int i = 1; i < sideTab.getChildren().size()-2; i += 4){
            if (playerNumber == GM.getCurTurn()){
                sideTab.getChildren().get(i).setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
            }
            else{
                sideTab.getChildren().get(i).setStyle("");
            }
            sideTab.getChildren().set(i+2, new Label("  Money: " + GM.getPlayer(playerNumber).getMoney()));
            sideTab.getChildren().set(i+3, new Label("  Place: " + GM.getBoard().getTile(GM.getPlayer(playerNumber).getPlace()).getName()));
            playerNumber++;
        }
    }

    /**
     * returns current position in grid pane
     * @param position
     * @return int[]
     * @author Joe C
     * @author Joe L
     */
    public int[] coordinates(int position){
        if (position <= 9){ return new int[]{0,10 - position % 10}; }
        else if (position <= 19){ return new int[]{position % 10, 0}; }
        else if (position <= 29){ return new int[]{10, position % 10};}
        else if (position <= 39){ return new int[]{10 - position % 10, 10};}
        else {return new int[]{0,0};}
    }

    public void movePlayer(GameMaster GM, ImageView[] playerImages, GridPane gp){
        ImageView playerSprite = playerImages[GM.getCurTurn()];
        int[] oldCoordinates = coordinates(GM.getPlayer(GM.getCurTurn()).getPlace());
        Iterator<Node> children = gp.getChildren().iterator();
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == oldCoordinates[1] && GridPane.getColumnIndex(n) == oldCoordinates[0]) {
                //((Pane) n).getChildren().remove(playerSprite);
                break;
            }
        }
        showRoll(GM.moveNextPiece(),GM);
        int[] newCoordinates = coordinates(GM.getPlayer(GM.getCurTurn()).getPlace());
        //Look at each Pane() object within gridPane()
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == newCoordinates[1] && GridPane.getColumnIndex(n) == newCoordinates[0]) { //
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
    }

    private void landedTileAction(GameMaster GM) {
        if (GM.getBuyable(GM.getPlayer(GM.getCurTurn()).getPlace())){
            //can buy/auction time
            if(!landOnBrought(GM)){
                // auction
                GM.applyTileEffect(doAuction(GM));
                // winner dialog
                System.out.println(((BuyableTile)GM.getBoard().getTileGrid()[GM.getPlayer(GM.getCurTurn()).getPlace()]).getPlayer().getToken());

                //needs winner dialog here
            }
            else {
                // player buys tile
                GM.applyTileEffect(GM.getPlayer(GM.getCurTurn()));
            }
        }
        else{
            //rent time
        }
    }

}
