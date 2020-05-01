package frontend;

import backend.Tokens;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Takes an integer and creates a dialogue box for assigning tokens to players
 * GridPane could be used instead of HBox VBox combination
 * @author Jonathan Morris
 * @author Alex Homer
 * @author Joe Corbett
 */
public class SelectingTokensDialog extends MonopolyDialog {
    public SelectingTokensDialog(int players){
        this.setHeaderText("Select the tokens you want to play with.");
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        this.getDialogPane().setContent(setContents(players));
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        //lambda expression
        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                Tokens[] outputList = new Tokens[players];
                for (int i =0; i<players ;i++){
                    outputList[i] = (Tokens) ((ComboBox)((HBox)((VBox) this.getDialogPane().getContent()).getChildren().get(i)).getChildren().get(1)).getValue();
                }
                return outputList;
            }
            return null;
        });
        //wait for response
        this.showAndWait();
        //return values
    }

    /**
     * Sets the contents of the Dialog with HBox and VBox combination along with a ComboBox inorder for the players to pick
     * @param players
     * @return VBox contains contents of Dialog
     */
    private VBox setContents(int players){
        Label playerLabel;
        Tokens[] allTokens = {Tokens.Boot, Tokens.Cat, Tokens.Goblet, Tokens.HatStand, Tokens.SmartPhone, Tokens.Spoon};
        VBox hBoxHolder = new VBox();
        HBox[] hBoxArray = new HBox[players];
        ComboBox[] tokenCombos = new ComboBox[players];

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
        return hBoxHolder;
    }

}
