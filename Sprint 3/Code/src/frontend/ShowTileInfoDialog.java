package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class ShowTileInfoDialog extends MonopolyDialog {
    public ShowTileInfoDialog(int tileNumber, GameMaster GM) throws InvalidHouseSetupException {
        this.setHeaderText("Tile Information for " + GM.getBoard().getTile(tileNumber).getName());
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(ok);
        VBox vBox = new VBox();
        if(((BuyableTile)(GM.getBoard().getTile(tileNumber))).getPlayer() != null){
            Label ownedBy = new Label("Owned by: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getPlayer().getToken());
            vBox.getChildren().add(ownedBy);
            showTileInfoIfOwner(tileNumber,GM);
        }
        else{
            Label ownedBy = new Label("Owned by: nobody");
            vBox.getChildren().add(ownedBy);
        }
        Label costToBuy = new Label("Cost to buy: "+((BuyableTile)(GM.getBoard().getTile(tileNumber))).getCostToBuy());
        vBox.getChildren().add(costToBuy);
        if(((BuyableTile)(GM.getBoard().getTile(tileNumber))).getClass() == Property.class){
            Label rate = new Label("Base Rate: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[0]);
            Label levelOne = new Label("1 House: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[1]);
            Label levelTwo = new Label("2 Houses: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[2]);
            Label levelThree = new Label("3 Houses: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[3]);
            Label levelFour = new Label("4 Houses: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[4]);
            Label hotel = new Label("Hotel: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[5]);
            vBox.getChildren().addAll(rate,levelOne,levelTwo,levelThree,levelFour,hotel);
        }
        else if(((BuyableTile)(GM.getBoard().getTile(tileNumber))).getClass() == Station.class){
            Label oneOwned = new Label("1 station owned: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[1]);
            Label twoOwned = new Label("2 stations owned: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[2]);
            Label threeOwned = new Label("3 stations owned: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[3]);
            Label fourOwned = new Label("4 stations owned: " + ((BuyableTile)(GM.getBoard().getTile(tileNumber))).getRent()[4]);
            vBox.getChildren().addAll(oneOwned,twoOwned,threeOwned,fourOwned);
        }
        else {
            // utility's
            Label oneOwned = new Label("One utility owned: " + ((BuyableTile) (GM.getBoard().getTile(tileNumber))).getRent()[1] + " times dice roll");
            Label twoOwned = new Label("Two utility owned: " + ((BuyableTile) (GM.getBoard().getTile(tileNumber))).getRent()[2] + " times dice roll");
            vBox.getChildren().addAll(oneOwned, twoOwned);
        }
        this.getDialogPane().setContent(vBox);
        Optional<Object> result = this.showAndWait();
        getTileInfoResult(result, tileNumber, GM);
    }

    private void getTileInfoResult(Optional<Object> result, int tileNumber, GameMaster GM) throws InvalidHouseSetupException {
        if (((ButtonType) result.get()).getButtonData() == ButtonBar.ButtonData.BACK_PREVIOUS){
            //unmortgage
            //
            ((BuyableTile)GM.getBoard().getTile(tileNumber)).unMortgageTile();
        }
        else if (((ButtonType) result.get()).getButtonData() == ButtonBar.ButtonData.APPLY){
            //mortgage throws invalid house exception
            ((BuyableTile)GM.getBoard().getTile(tileNumber)).mortgageTile();
        }
        else if(((ButtonType) result.get()).getButtonData() == ButtonBar.ButtonData.NO) {
            //sell
            if (((BuyableTile) (GM.getBoard().getTile(tileNumber))).getClass() == Property.class) {
                ((Property) GM.getBoard().getTile(tileNumber)).sellTile();
            } else if (((BuyableTile) (GM.getBoard().getTile(tileNumber))).getClass() == Utility.class) {
                ((Utility) GM.getBoard().getTile(tileNumber)).sellUtility();
            } else if (((BuyableTile) (GM.getBoard().getTile(tileNumber))).getClass() == Station.class) {
                ((Station) GM.getBoard().getTile(tileNumber)).sellStation();
            }
            else {
                System.out.println("Buyable tile not found");
            }
        }
        else {
            //ok
            System.out.println("ok");
        }
    }

    private void showTileInfoIfOwner(int tileNumber, GameMaster GM) {
        if(GM.getPlayers()[GM.getCurTurn()].equals(((BuyableTile)(GM.getBoard().getTile(tileNumber))).getPlayer())){
            if(((BuyableTile)GM.getBoard().getTile(tileNumber)).getMortgaged()){
                ButtonType unmortgage = new ButtonType("Unmortgage",ButtonBar.ButtonData.BACK_PREVIOUS);
                this.getDialogPane().getButtonTypes().add(unmortgage);
                // if pressed set mortgaged to false
            }
            else{
                ButtonType mortgage = new ButtonType("Mortgage",ButtonBar.ButtonData.APPLY);
                this.getDialogPane().getButtonTypes().add(mortgage);
                // if pressed set mortgaged to true
            }
            ButtonType sell = new ButtonType("Sell", ButtonBar.ButtonData.NO);
            this.getDialogPane().getButtonTypes().add(sell);
            //if called sell base tile

        }
    }

}
