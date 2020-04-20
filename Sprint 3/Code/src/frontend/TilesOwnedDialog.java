package frontend;

import backend.BuyableTile;
import backend.InvalidHouseSetupException;
import backend.Tile;
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
        for(Tile tile : GM.getPlayerProperties(GM.getPlayer(playerNumber))){
            list.getChildren().add(getProperty(tile));
        }
        return list;
    }
    private Label getProperty(Tile tile) {
        Label property = new Label(tile.getName());
        property.setOnMouseClicked(mouseEvent -> {
            try {
                new ShowTileInfoDialog((BuyableTile)tile, GM);
            } catch (InvalidHouseSetupException e) {
                e.printStackTrace();
            }
        });
        return property;
    }
}
