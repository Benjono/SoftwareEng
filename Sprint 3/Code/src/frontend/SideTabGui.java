package frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SideTabGui extends VBox {

    GameMasterGui GM;
    BoardGui boardGui;

    public SideTabGui(GameMasterGui GM, BoardGui boardGui, int numPlayers){
        this.GM = GM;
        this.boardGui = boardGui;
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        this.setPrefWidth(200);
        //Player title
        Label pTabTitle = new Label("Current Turn");
        this.getChildren().add(pTabTitle);
        //Player name display
        Label[] playerList = new Label[numPlayers];
        Label[] playerMoney = new Label[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerList[i] = new Label("Player " + (i+1));
            Label token = new Label("   Token: " + GM.getPlayer(i).getToken().name());
            playerMoney[i] = new Label("    Money: " + GM.getPlayer(i).getMoney());
            Label place = new Label("   Place: " + GM.getBoard().getTile(GM.getPlayer(i).getPlace()).getName());
            this.getChildren().addAll(playerList[i], token, playerMoney[i], place);
        }
        //button next turn and roll dice
        Button diceRollNextTurn = new Button("Roll dice");
        diceRollNextTurn.setPrefSize(100, 100);
        this.getChildren().add(diceRollNextTurn);

        updateSideTab(); //player 1 will have a highlighted label
        diceRollNextTurn.setOnAction(new EventHandler<>() {
            @Override
            public synchronized void handle(ActionEvent event) {
                if (!GM.isCanNextTurn()) {
                    boardGui.movePlayer();
                    if (GM.isCanNextTurn()) {
                        diceRollNextTurn.setText("Next Turn");
                    } else {
                        boardGui.setBoardRotation();
                    }

                } else {
                    GM.nextTurn();
                    updateSideTab();//the player with the current turn will have a highlighted label
                    boardGui.setBoardRotation();
                    diceRollNextTurn.setText("Roll Dice");
                }

            }
        });
    }

    /**
     * adds a highlighted background to the label that represents the currently active player in the player tab
     * updates player's money, i here relates to the ith row in the list
     * @author Joe L
     * @author Joe C
     */

    public void updateSideTab(){
        int playerNumber = 0;
        for(int i = 1; i < this.getChildren().size()-2; i += 4){
            if (playerNumber == GM.getCurTurn()){
                this.getChildren().get(i).setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
            }
            else{
                this.getChildren().get(i).setStyle("");
            }
            this.getChildren().set(i+2, new Label("  Money: " + GM.getPlayer(playerNumber).getMoney()));
            this.getChildren().set(i+3, new Label("  Place: " + GM.getBoard().getTile(GM.getPlayer(playerNumber).getPlace()).getName()));
            playerNumber++;
        }
    }
}
