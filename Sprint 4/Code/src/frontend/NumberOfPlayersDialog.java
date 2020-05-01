package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * This is the first screen of the game. Returns the number of players after selected by the players.
 * @author Jonathan Morris
 * @author Alex Homer
 * @author Joe Corbett
 */

public class NumberOfPlayersDialog extends MonopolyDialog{
    public NumberOfPlayersDialog(){
        this.setHeaderText("Select the number of players.");
        this.getDialogPane().setContent(settingUpHBox());
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        //lambda expressions
        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                return ((ComboBox)((HBox) this.getDialogPane().getContent()).getChildren().get(1)).getValue();
            }
            return null;
        });
        //wait for return
        this.showAndWait();
        // end of player number dialog box
    }

    /**
     * Sets up the Hbox in the Dialog with Labels
     * @return HBox
     */
    private HBox settingUpHBox(){
        Label playerLabel = new Label("Player Count: ");
        HBox hboxPlayerCount = new HBox();
        HBox.setHgrow(playerLabel, Priority.ALWAYS);
        hboxPlayerCount.setMaxHeight(50);
        hboxPlayerCount.setMinHeight(50);
        hboxPlayerCount.setSpacing(15);
        hboxPlayerCount.setAlignment(Pos.CENTER);
        hboxPlayerCount.getChildren().addAll(playerLabel, settingUpComboBox());
        return hboxPlayerCount;
    }

    /**
     * Sets up the ComboBox in the Dialog in order for the players to choose the number of players they'd like
     * @return ComboBox
     */
    private ComboBox settingUpComboBox() {
        ComboBox<Integer> playerCombo = new ComboBox<>();
        playerCombo.getItems().addAll(2, 3, 4, 5, 6);
        playerCombo.setValue(2);
        HBox.setHgrow(playerCombo, Priority.ALWAYS);
        return playerCombo;
    }

}
