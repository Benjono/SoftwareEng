package frontend;

import backend.BuyableTile;
import backend.InvalidHouseSetupException;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TilesOwnedDialog extends MonopolyDialog {
    private final int playerNumber;
    private final GameMasterGui GM;

    public TilesOwnedDialog(GameMasterGui GM, int playerNumber) {
        this.GM = GM;
        this.playerNumber = playerNumber;
        this.setHeaderText("Player " + (playerNumber+1) + "'s owned properties");
        this.getDialogPane().setContent(getProperties());

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.showAndWait();
    }

    private VBox getProperties() {
        VBox list = new VBox();
        for(int i = 0; i < GM.getBoard().getTileGrid().length; i++){
            if(GM.getBoard().getTile(i).getBuyable()){
                if (((BuyableTile)GM.getBoard().getTile(i)).getPlayer() != null){
                    if(((BuyableTile) GM.getBoard().getTile(i)).getPlayer().equals(GM.getPlayer(playerNumber))){
                        list.getChildren().add(getProperty(i));
                    }
                }
            }
        }
        return list;
    }
    private Label getProperty(int tileNumber) {
        Label property = new Label(GM.getBoard().getTile(tileNumber).getName());
        property.setOnMouseClicked(mouseEvent -> {
            try {
                new ShowTileInfoDialog(tileNumber, GM);
            } catch (InvalidHouseSetupException e) {
                e.printStackTrace();
            }
        });
        return property;
    }
}
