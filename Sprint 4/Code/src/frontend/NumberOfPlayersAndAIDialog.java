package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This is the first screen of the game. Returns the number of players after selected by the players.
 * @author Jonathan Morris
 * @author Alex Homer
 * @author Joe Corbett
 */

public class NumberOfPlayersAndAIDialog extends MonopolyDialog{

    VBox vBox;
    HBox[] hBoxes;
    int playerMax;
    public NumberOfPlayersAndAIDialog(){
        playerMax = 6;
        this.setHeaderText("Select the number of players or AI.");
        vBox = new VBox();
        vBox.getChildren().add(settingUpHBox());
        hBoxes = new HBox[playerMax];
        for (int i =0; i < playerMax; i++){
            hBoxes[i] = new HBox();
            hBoxes[i].setMaxHeight(50);
            hBoxes[i].setMinHeight(50);
            hBoxes[i].setSpacing(15);
            hBoxes[i].setAlignment(Pos.CENTER);
            Label currentLabel = new Label("Player " + (i+1) + " Type: ");
            ComboBox<String> playerAiCombo = new ComboBox<>();
            playerAiCombo.getItems().addAll("Player", "AI");
            playerAiCombo.setValue("Player");
            HBox.setHgrow(currentLabel, Priority.ALWAYS);
            HBox.setHgrow(playerAiCombo, Priority.ALWAYS);
            hBoxes[i].getChildren().addAll(currentLabel,playerAiCombo);
            if (i > 1){
                hBoxes[i].setVisible(false);
            }
            vBox.getChildren().add(hBoxes[i]);
        }
        this.getDialogPane().setContent(vBox);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        //lambda expressions

        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                boolean[] playersOrAi = new boolean[(int)((ComboBox)((HBox)((VBox)this.getDialogPane().getContent()).getChildren().get(0)).getChildren().get(1)).getValue()];
                for(int i=1; i <= (int)((ComboBox)((HBox)((VBox)this.getDialogPane().getContent()).getChildren().get(0)).getChildren().get(1)).getValue(); i++){
                    if ((((ComboBox)((HBox)((VBox)this.getDialogPane().getContent()).getChildren().get(i)).getChildren().get(1)).getValue()).equals("Player")){
                       playersOrAi[i-1] = true;
                    }
                    else{
                        playersOrAi[i-1] = false;
                    }
                }
                return playersOrAi;
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
        Label playerLabel = new Label("Total Player Count: ");
        HBox hBoxPlayerCount = new HBox();
        HBox.setHgrow(playerLabel, Priority.ALWAYS);
        hBoxPlayerCount.setMaxHeight(50);
        hBoxPlayerCount.setMinHeight(50);
        hBoxPlayerCount.setSpacing(15);
        hBoxPlayerCount.setAlignment(Pos.CENTER);
        hBoxPlayerCount.getChildren().addAll(playerLabel, settingUpTotalComboBox());
        return hBoxPlayerCount;
    }

    /**
     * Sets up the ComboBox in the Dialog in order for the players to choose the number of players they'd like
     * @return ComboBox
     */
    private ComboBox settingUpTotalComboBox() {
        ComboBox<Integer> playerCombo = new ComboBox<>();
        playerCombo.getItems().addAll(2, 3, 4, 5, 6);
        playerCombo.setValue(2);
        playerCombo.valueProperty().addListener((observableValue, integer, t1) -> addPlayerAISelection(observableValue.getValue()));
        HBox.setHgrow(playerCombo, Priority.ALWAYS);
        return playerCombo;
    }

    /**
     * Adds Player and AI combo box selections for each player/AI wanted
     * total being the total players/AI
      * @param total
     */
    private void addPlayerAISelection(int total){
        for (int i = 0; i< playerMax; i++){
            if(i < total){
                hBoxes[i].setVisible(true);
            }
            else{
                hBoxes[i].setVisible(false);
            }
        }
    }
}
