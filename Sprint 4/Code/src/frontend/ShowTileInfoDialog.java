package frontend;

import backend.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.awt.*;

/**
 * When a tile is clicked this is called to display all the tile info rent, cost to buy who its owned by etc.
 * @author Joe C
 */
public class ShowTileInfoDialog extends MonopolyDialog {
    int tileClicked;
    Pane tileGui;
    BuyableTile tile;
    ImageView[] houses;
    public ShowTileInfoDialog(BuyableTile tile, GameMasterGui GM, int tileClicked, BoardGui boardGui) {
        this.tile = tile;
        this.tileClicked = tileClicked;
        this.tileGui = boardGui.findPane(boardGui.boardCoordinates(tileClicked));
        setupHouseImages();
        this.setHeaderText("Tile Information for " + tile.getName());
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(ok);
        VBox vBox = new VBox();
        Label ownedBy;
        if(tile.getPlayer() != null){
            ownedBy = new Label("Owned by: " + tile.getPlayer().getToken());
        }
        else{
            ownedBy = new Label("Owned by: nobody");
        }
        vBox.getChildren().add(ownedBy);
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
        addTileInfoButtons(vBox, GM);
        this.getDialogPane().setContent(vBox);
        this.showAndWait();
    }

    /**
     * If clicked by the owner of the tile then these addition buttons are displayed to the user;
     * these being mortgage unmortgage and sell.
     * @param vBox
     * @author Joe C
     */
    private void addTileInfoButtons(VBox vBox, GameMasterGui GM) {
        HBox[] container = new HBox[2];
        container[0] = new HBox();
        container[1] = new HBox();
        if(tile instanceof Property){
            Button buyHouse = new Button();
            Button sellHouse = new Button();
            currentHouseLevelChecks(GM,(Property) tile,sellHouse,buyHouse);
            buyHouse.setOnAction(actionEvent -> {
                try {
                    ((Property) tile).buyHouse(1);
                } catch (NotEnoughMoneyException e) {
                    e.printStackTrace();
                }
                currentHouseLevelChecks(GM,(Property) tile,sellHouse,buyHouse);
            });
            sellHouse.setOnAction(actionEvent -> {
                ((Property) tile).sellHouse(1);
                currentHouseLevelChecks(GM,(Property) tile,sellHouse,buyHouse);
            });
            container[0].getChildren().addAll(buyHouse,sellHouse);
        }
        Button mortgage = new Button();
        if(tile.getMortgaged()){
            mortgage.setText("unmortgage");
        }
        else{
            mortgage.setText("mortgage");
        }
        mortgage.setOnAction(actionEvent -> {
            if(tile.getMortgaged()){
                try {
                    tile.unMortgageTile();
                } catch (NotEnoughMoneyException e) {
                    e.printStackTrace();
                }
                mortgage.setText("mortgage");
            }
            else {
                try {
                    tile.mortgageTile();
                    mortgage.setText("unmortgage");
                    tileGui.getChildren().removeAll(houses);
                } catch (InvalidHouseSetupException e) {
                    System.out.println("mortgagefailed");
                }
            }
        });
        Button sell = new Button("Sell");
        //if called sell base tile
        sell.setOnAction(actionEvent -> {
            if (tile instanceof Property) {
                try {
                    ((Property) tile).sellTile();
                    setButtonsInvisible(container);
                    tileGui.getChildren().removeAll(houses);
                } catch (InvalidHouseSetupException e) {
                    System.out.println("sellTileFailed");
                }
            } else if (tile instanceof Utility) {
                ((Utility) tile).sellUtility();
                setButtonsInvisible(container);
            } else if (tile instanceof Station) {
                ((Station) tile).sellStation();
                setButtonsInvisible(container);
            }
            else {
                System.out.println("Buyable tile not found");
            }
        });
        container[1].getChildren().addAll(mortgage,sell);

        if(!GM.getPlayers()[GM.getCurTurn()].equals(tile.getPlayer())) {
            //set buttons to invisible
            setButtonsInvisible(container);
        }
        vBox.getChildren().addAll(container);
    }

    /**
     * sets all buttons to be invisable to player
     * @param container
     */
    private void setButtonsInvisible(HBox[] container){
        for (HBox hBox : container){
            for(Node b : hBox.getChildren()){
                b.setVisible(false);
            }
        }
    }

    /**
     * checks the current house level and adjusts the labels and background accordingly
     * @param property
     * @param sellHouse
     * @param buyHouse
     */
    private void currentHouseLevelChecks(GameMasterGui GM, Property property, Button sellHouse, Button buyHouse){
        sellHouse.setText("Sell House");
        buyHouse.setText("Buy House");
        buyHouse.setVisible(true);
        sellHouse.setVisible(true);
        tileGui.getChildren().removeAll(houses);
        if (property.getCurrentHouseLevel() == 0) {// disallow selling houses
            sellHouse.setVisible(false);

        } else if (property.getCurrentHouseLevel() == 1) {
            tileGui.getChildren().add(houses[0]);

        } else if (property.getCurrentHouseLevel() == 2) {
            tileGui.getChildren().add(houses[1]);

        } else if (property.getCurrentHouseLevel() == 3) {
            tileGui.getChildren().add(houses[2]);

        } else if (property.getCurrentHouseLevel() == 4) {
            buyHouse.setText("Buy Hotel");
            tileGui.getChildren().add(houses[3]);

        } else if (property.getCurrentHouseLevel() == 5) {
            sellHouse.setText("Sell Hotel");
            buyHouse.setVisible(false);
            tileGui.getChildren().add(houses[4]);
        }
        if (!GM.canBuyHouse(property,GM.getPlayer(GM.getCurTurn()))){
            buyHouse.setVisible(false);
        }
    }

    /**
     * Setup each of the houses with the correct image
     */
    private void setupHouseImages() {
        houses = new ImageView[5];
        for (int i = 0; i< houses.length; i++){
            if (i == 4){
                houses[i] = new ImageView(new Image("hotel.png"));
            }
            else {
                houses[i] = new ImageView(new Image("house-"+(i+1)+".png"));
            }
            houses[i].setFitWidth(64);
            houses[i].setFitHeight(64);
            if (tileClicked < 11){houses[i].setRotate(90);}
            else if (tileClicked < 20){houses[i].setRotate(180);}
            else if (tileClicked < 31){houses[i].setRotate(270);}
        }
    }
}
