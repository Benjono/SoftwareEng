package frontend;

import backend.BuyableTile;
import backend.InvalidHouseSetupException;
import backend.Property;
import backend.Tile;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * Displays all the names of the properties that a player owns when clicked on
 * @author Joe C
 */
public class TilesOwnedDialog extends MonopolyDialog {
    private final int playerNumber;
    private final GameMasterGui GM;
    private final BoardGui boardGui;

    public TilesOwnedDialog(GameMasterGui GM, int playerNumber,BoardGui boardGui) {
        this.GM = GM;
        this.playerNumber = playerNumber;
        this.boardGui = boardGui;
        this.setHeaderText("Player " + (playerNumber+1) + "'s owned properties");
        this.getDialogPane().setContent(getProperties());

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.showAndWait();
    }

    /**
     * Gets the list of properties a player owns and returns a VBox of them
     * @return A list of all properties
     */
    private VBox getProperties() {
        VBox list = new VBox();
        for(Tile tile : GM.getPlayerProperties(GM.getPlayer(playerNumber))){
            if (tile != null){
                list.getChildren().add(getProperty(tile));
            }
        }
        return list;
    }

    /**
     * Returns a property label that reads the name of the property requested by the tile inputted.
     * Also makes the label clickable in order to see the tile's info.
     * @param tile
     * @return Label for the property in question
     */
    private Label getProperty(Tile tile) {
        Label property = new Label(tile.getName());
        property.setOnMouseClicked(mouseEvent -> {
            new ShowTileInfoDialog((BuyableTile)tile, GM, getTilePicked(tile),boardGui);
        });
        return property;
    }

    private int getTilePicked(Tile tile){
        for(int i = 0; i < GM.getBoard().getTileGrid().length; i++){
            if(tile.equals(GM.getTile(i))){
                return i;
            }
        }
        return 0;
    }
}
