package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * When a tile is clicked this is called to display all the tile info rent, cost to buy who its owned by etc.
 * @author Joe C
 */
public class ShowTileInfoDialog extends MonopolyDialog {
    public ShowTileInfoDialog(BuyableTile tile, GameMaster GM) throws InvalidHouseSetupException {
        this.setHeaderText("Tile Information for " + tile.getName());
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(ok);
        VBox vBox = new VBox();
        if(tile.getPlayer() != null){
            Label ownedBy = new Label("Owned by: " + tile.getPlayer().getToken());
            vBox.getChildren().add(ownedBy);
            showTileInfoIfOwner(tile,GM);
        }
        else{
            Label ownedBy = new Label("Owned by: nobody");
            vBox.getChildren().add(ownedBy);
        }
        Label costToBuy = new Label("Cost to buy: "+tile.getCostToBuy());
        vBox.getChildren().add(costToBuy);
        if(tile instanceof Property){
            Label rate = new Label("Base Rate: " + tile.getRent()[0]);
            Label levelOne = new Label("1 House: " + tile.getRent()[1]);
            Label levelTwo = new Label("2 Houses: " + tile.getRent()[2]);
            Label levelThree = new Label("3 Houses: " + tile.getRent()[3]);
            Label levelFour = new Label("4 Houses: " + tile.getRent()[4]);
            Label hotel = new Label("Hotel: " + tile.getRent()[5]);
            vBox.getChildren().addAll(rate,levelOne,levelTwo,levelThree,levelFour,hotel);
        }
        else if(tile instanceof Station){
            Label oneOwned = new Label("1 station owned: " + tile.getRent()[1]);
            Label twoOwned = new Label("2 stations owned: " + tile.getRent()[2]);
            Label threeOwned = new Label("3 stations owned: " + tile.getRent()[3]);
            Label fourOwned = new Label("4 stations owned: " + tile.getRent()[4]);
            vBox.getChildren().addAll(oneOwned,twoOwned,threeOwned,fourOwned);
        }
        else {
            // utility's
            Label oneOwned = new Label("One utility owned: " + tile.getRent()[1] + " times dice roll");
            Label twoOwned = new Label("Two utility owned: " + tile.getRent()[2] + " times dice roll");
            vBox.getChildren().addAll(oneOwned, twoOwned);
        }
        this.getDialogPane().setContent(vBox);
        Optional<Object> result = this.showAndWait();
        getTileInfoResult(result, tile, GM);
    }

    /**
     * Returns the correct implementation depending on which button is pressed; unmortgage, mortgage, sell, ok
     * @param result
     * @param tile
     * @param GM
     * @throws InvalidHouseSetupException
     * @author Joe C
     */
    private void getTileInfoResult(Optional<Object> result, BuyableTile tile, GameMaster GM) throws InvalidHouseSetupException {
        if (((ButtonType) result.get()).getButtonData() == ButtonBar.ButtonData.BACK_PREVIOUS){
            //unmortgage
            tile.unMortgageTile();
        }
        else if (((ButtonType) result.get()).getButtonData() == ButtonBar.ButtonData.APPLY){
            //mortgage throws invalid house exception
            tile.mortgageTile();
        }
        else if(((ButtonType) result.get()).getButtonData() == ButtonBar.ButtonData.NO) {
            //sell
            if (tile instanceof Property) {
                ((Property) tile).sellTile();
            } else if (tile instanceof Utility) {
                ((Utility) tile).sellUtility();
            } else if (tile instanceof Station) {
                ((Station) tile).sellStation();
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

    /**
     * If clicked by the owner of the tile then these addition buttons are displayed to the user;
     * these being mortgage unmortgage and sell.
     * @param tile
     * @param GM
     * @author Joe C
     */
    private void showTileInfoIfOwner(BuyableTile tile, GameMaster GM) {
        if(GM.getPlayers()[GM.getCurTurn()].equals(tile.getPlayer())){
            if(tile.getMortgaged()){
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
