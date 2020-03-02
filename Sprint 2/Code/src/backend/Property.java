package backend;

public class Property extends Tile {
    Enum colour;
    boolean mortgage;
    int costToBuy;
    int houseCost;
    int currentHouseLevel;
    int[] rent;
    Player owner;

    /**
     * constructs a property using the given parameters
     *
     * @param name name of tile
     * @param colour String converted to enum of color of group tile belongs to
     * @param costToBuy integer - cost to buy the tile
     * @param rent array - rent to be paid upon landing
     * @param houseCost - cost to buy house/ hotel
     * @author Alex
     */
    public Property(String name, String colour, int costToBuy, int[] rent,int houseCost){
        setBuyable(false);
        setName(name);
        mortgage = false;
        currentHouseLevel = 0;
        this.colour = Colours.valueOf(colour);
        this.costToBuy = costToBuy;
        this.rent = rent;
        this.houseCost = houseCost;
    }

    /**
     * sets a player as the owner of the tile
     * @param owner - new owner of tile
     */
    public void setOwner(Player owner){
        this.owner = owner;
    }

    /**
     * sets owner to null - selling property to bank
     * @return int amount the property is worth to the bank
     */
    private void sellBaseProperty(){
        this.owner = null;
        if(!mortgage){
            owner.setMoney(owner.getMoney() + costToBuy);
        }
        else{
            owner.setMoney(owner.getMoney() +(costToBuy/2));
        }
    }

    /**
     * sells levels of housing dependent on input
     * @param houseLevel - how many houses to be sold
     * @return int - money generated from sale
     */
    public void sellHouse(int houseLevel){
        currentHouseLevel -= houseLevel;
        owner.setMoney(owner.getMoney()+(houseLevel*houseCost));
    }

    /**
     * gets house level
     * @return int (0-5) current house level
     */
    public int getCurrentHouseLevel(){return currentHouseLevel;}

    /**
     * BEN READ THIS
     * mortgages a property, giving the player half of its buy cost if there are no houses present
     *
     * @return money the player made from mortgaging the property
     * @throws InvalidHouseSetupException if houses are present on property
     */
    public void mortgageProperty() throws InvalidHouseSetupException{
        if(currentHouseLevel ==0) {
            mortgage = true;
            owner.setMoney(owner.getMoney()+(costToBuy/2));
        }
        else{
            throw new InvalidHouseSetupException("cannot mortgage a property with houses/hotel built on them");
        }
    }

    /**
     * gets owner
     * @return current owner of the tie
     */
    public Player getOwner (){return owner;}

    /**
     * finds the highest bidder and makes them the owner of the tile
     * @param players list of players in the game
     * @param bids list of player bids
     */
    public void auction(Player[] players, int[] bids){
        int highestBid= 0;
        for(int i =0; i<bids.length;i++){
            if(bids[i]> bids[highestBid]){
                highestBid = bids[i];
            }
        }
        setOwner(players[highestBid]);
    }

    /**
     * player who lands on tile pays the owner of tile the appropriate rent
     * @param debtor - player who lands on property
     */
    public void rent(Player debtor){
        debtor.setMoney(debtor.getMoney() - rent[currentHouseLevel]);
        owner.setMoney(owner.getMoney() - rent[currentHouseLevel]);
    }
}
