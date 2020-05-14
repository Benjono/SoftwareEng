package frontend;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class AbridgedDialog extends MonopolyDialog {
    public AbridgedDialog(){
        this.setHeaderText("Input number of turns to play.");
        this.getDialogPane().setContent(settingUpHBox());
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                return Integer.parseInt(((TextField)((HBox) this.getDialogPane().getContent()).getChildren().get(1)).getCharacters().toString());
            }
            return null;
        });
        //wait for return
        this.showAndWait();
    }

    private HBox settingUpHBox(){
        Label numberOfTurns = new Label("Number of Turns: ");
        HBox hBox = new HBox();
        HBox.setHgrow(numberOfTurns, Priority.ALWAYS);
        hBox.setMaxHeight(50);
        hBox.setMinHeight(50);
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(numberOfTurns, settingUpTextField());
        return hBox;

    }

    private TextField settingUpTextField(){
        TextField textField = new TextField();
        textField.addEventFilter(KeyEvent.KEY_TYPED, inputCharacter -> {
            if (!Character.isDigit(inputCharacter.getCharacter().toCharArray()[inputCharacter.getCharacter().toCharArray().length - 1])) {
                inputCharacter.consume();
            }
            else if (Integer.parseInt(inputCharacter.getCharacter()) == 0 && textField.getCharacters().length() == 0){
                inputCharacter.consume();
            }
        });
        HBox.setHgrow(textField, Priority.ALWAYS);
        return textField;
    }
}
