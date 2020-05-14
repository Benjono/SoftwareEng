package frontend;

import backend.AI;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Sets up the SideTab seen in game and adds the appropriate Labels and Buttons to it, using functions given in Backend.
 * @author Joe C
 */
public class SideTabGui extends VBox {

    GameMasterGui GM;
    BoardGui boardGui;
    ImageView[] buttonImages;

    public SideTabGui(GameMasterGui GM, BoardGui boardGui, int numPlayers){
        this.GM = GM;
        this.boardGui = boardGui;
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        this.setPrefWidth(200);
        this.buttonImages = new ImageView[2];
        buttonImages[0] = new ImageView(new Image("rolldice.png"));
        buttonImages[0].setFitHeight(64);
        buttonImages[0].setFitWidth(64);
        buttonImages[1] = new ImageView(new Image("skipturn.png"));
        buttonImages[1].setFitHeight(64);
        buttonImages[1].setFitWidth(64);
        //Player title
        //Label pTabTitle = new Label("Current Turn");
        //this.getChildren().add(pTabTitle);
        //Player name display

        for(int i = 0; i < numPlayers; i++){
            Label playerList = new Label("Player " + (i+1));
            Label token = new Label("   Token: " + GM.getPlayer(i).getToken().name());
            Label playerMoney = new Label("   Money: " + GM.getPlayer(i).getMoney());
            Label outOfJail = new Label("   Get out of Jail free cards: "+ (GM.getPlayer(i).getOutOfJailFreeOpportunity().size() + GM.getPlayer(i).getOutOfJailFreePotLuck().size()));
            Label numProperties = propertiesLabelSetup(i);
            Label place = new Label("  Place: " + GM.getBoard().getTile(GM.getPlayer(i).getPlace()).getName());
            this.getChildren().addAll(playerList, token, playerMoney, outOfJail, numProperties, place);
        }
        Label blank = new Label(" ");
        //button next turn and roll dice
        Button diceRollNextTurn = new Button();
        if(GM.getPlayer(GM.getCurTurn()) instanceof AI){
            diceRollNextTurn.setGraphic(buttonImages[1]);
        }
        else{
            diceRollNextTurn.setGraphic(buttonImages[0]);
        }

        diceRollNextTurn.setStyle("-fx-focus-color: transparent; -fx-background-color: transparent;");
        HBox containterForButton = new HBox();
        containterForButton.setAlignment(Pos.BOTTOM_CENTER);
        containterForButton.getChildren().add(diceRollNextTurn);
        this.getChildren().addAll(blank,containterForButton);

        updateSideTab(); //player 1 will have a highlighted label

        diceRollNextTurn.setOnAction(actionEvent -> {
            if(GM.getPlayer(GM.getCurTurn())instanceof AI){
                takeAiTurn(diceRollNextTurn);
            }
            else{
                takePlayerTurn(diceRollNextTurn);
            }
        });

    }

    /**
     * Takes a player's turn if clicked
     * @param diceRollNextTurn
     */
    private void takePlayerTurn(Button diceRollNextTurn){
        if (!GM.isCanNextTurn()) {
            boardGui.movePlayer();
            if (GM.isCanNextTurn()) {
                diceRollNextTurn.setGraphic(buttonImages[1]);
            } else {
                boardGui.setBoardRotation();
            }

        } else {
            endTurn(diceRollNextTurn);
        }
    }

    /**
     * Takes an AI's turn
     * @param diceRollNextTurn
     */
    private void takeAiTurn(Button diceRollNextTurn){
        while(!GM.isCanNextTurn()){
            boardGui.movePlayer();
            boardGui.setBoardRotation();
        }
        endTurn(diceRollNextTurn);
    }

    /**
     * Checks at the end of the turn if in jail, opitional stuff for AI, player has lost, game has been won.
     * @param diceRollNextTurn
     */
    private void endTurn(Button diceRollNextTurn){
        if(GM.getPlayer(GM.getCurTurn()).getJailTime() > 0){
            new TileEffectDialog(GM);
        }
        if (GM.getPlayer(GM.getCurTurn()) instanceof AI){
            ((AI) GM.getPlayer(GM.getCurTurn())).optionalStuff(GM.getBoard());
        }
        if(GM.getPlayer(GM.getCurTurn()).getMoney() < 1){
            // get token off board and properties reset to original image
            boardGui.removePlayer(GM.getPlayer(GM.getCurTurn()).getPlace());
            boardGui.removeHouseImages();
            // current player lost
            GM.playerLost();
            new TileEffectDialog(GM, -1);
        }
        if (GM.gameWon()){
            // current player won
            new TileEffectDialog(GM, GM.winner());
        }
        GM.nextTurn();
        updateSideTab();//the player with the current turn will have a highlighted label
        boardGui.setBoardRotation();
        if(!(GM.getPlayer(GM.getCurTurn()) instanceof AI)){
            diceRollNextTurn.setGraphic(buttonImages[0]);
        }
        else{
            diceRollNextTurn.fire();
        }
    }

    /**
     * Setups the property label with appropriate styles
     * @param playerNumber
     * @return the new property label
     */
    private Label propertiesLabelSetup(int playerNumber) {
        Label numProperties = new Label("   Properties owned: " + GM.getNumPlayerProperties(GM.getPlayer(playerNumber)));
        numProperties.setStyle("-fx-background-color: dimgray; -fx-text-fill: snow;");
        numProperties.setOnMouseClicked(mouseEvent -> {new TilesOwnedDialog(GM, playerNumber,boardGui);});
        return numProperties;
    }

    /**
     * adds a highlighted background to the label that represents the currently active player in the player tab
     * updates player's money, updates the place, and how many Get out of jail free cards,
     * i here relates to the ith row in the list
     * @author Joe L
     * @author Joe C
     */
    public void updateSideTab(){
        int playerNumber = 0;
        for(int i = 0; i < this.getChildren().size()-3; i += 6){
            if(GM.getPlayer(playerNumber) == null){
                // removes players who have lost from the game
                for(int j =0; j < 6; j++){
                    this.getChildren().set(i+j, null);
                }
            }
            else{
                if (playerNumber == GM.getCurTurn()){
                    this.getChildren().get(i).setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
                }
                else{
                    this.getChildren().get(i).setStyle("");
                }
                this.getChildren().set(i+2, new Label("   Money: " + GM.getPlayer(playerNumber).getMoney()));
                this.getChildren().set(i+3, new Label("   Get out of Jail free cards: "+ (GM.getPlayer(playerNumber).getOutOfJailFreeOpportunity().size() + GM.getPlayer(playerNumber).getOutOfJailFreePotLuck().size())));
                this.getChildren().set(i+4, propertiesLabelSetup(playerNumber));
                this.getChildren().set(i+5, new Label("   Place: " + GM.getBoard().getTile(GM.getPlayer(playerNumber).getPlace()).getName()));
            }
            playerNumber++;
        }
    }
}
