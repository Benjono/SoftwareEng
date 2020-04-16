package frontend;

import backend.GameMaster;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implements auction dialog
 * GridPane could be used instead of HBox VBox combination
 * @author Joe C
 */
public class AuctionDialog extends MonopolyDialog {
    public AuctionDialog(GameMaster GM){
        this.setHeaderText("Auction of " + GM.getTile(GM.getPlayer(GM.getCurTurn()).getPlace()).getName());

        this.getDialogPane().setContent(getContents(GM));
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                int[] moneyBets = new int[GM.getPlayers().length];
                for (int i =0; i<GM.getPlayers().length-1 ;i++){
                    moneyBets[i] = Integer.parseInt(((NumField)((HBox)((VBox) this.getDialogPane().getContent()).getChildren().get(i)).getChildren().get(1)).getCharacters().toString());
                }
                return moneyBets;
            }
            return null;
        });
        // show dialog
        this.showAndWait();
    }

    private VBox getContents(GameMaster GM){
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
        return hBoxHolder;
    }
}