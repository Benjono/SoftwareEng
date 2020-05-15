package backend;

public class BuyableTile extends Tile {
    int costToBuy;
    int[] rent;
    boolean mortgaged;
    Player owner;

    /**
     * get current mortgage status of tile
     * @return mortgaged, if the property is currently mortgaged
     */
    public boolean getMortgaged(){
        return mortgaged;
    }

    /**
     * sets current mortgage status on tile
     * @param mortgaged, what to set mortgaged too
     */
    protected void setMortgaged(boolean mortgaged){
        this.mortgaged = mortgaged;
    }

    /**
     * get how much property costs to buy
     * @return cost to buy property
     */
    public int getCostToBuy(){
        return costToBuy;
    }

    /**
     * sets the cost to buy the tile
     */
    protected void setCostToBuy(){
        this.costToBuy = costToBuy;
    }

    /**
     * gets an array of the rents for the tile
     * @return array of rents
     */
    public int[] getRent(){
        return rent;
    }

    /**
     * sets rent for tile
     * @param rent array of rents to be set for tile
     */
    protected void setRent(int[] rent){
        this.rent = rent;
    }

    /**
     * gets owner of tile
     * @return owner of tile
     */
    public Player getPlayer(){
        return owner;
    }

    /**
     * sets new owner of tile
     * @param owner new owner of the tile
     */
    protected void setOwner(Player owner){
        this.owner = owner;
    }

    /**
     * sets owner to null - selling property to bank
     */
    protected void sellBaseTile() throws InvalidHouseSetupException{
        if(!mortgaged){ //if not mortgaged
            owner.setMoney(owner.getMoney() + costToBuy); //give full cost
        }
        else{
            owner.setMoney(owner.getMoney() +(costToBuy/2)); //otherwise give half cost
        }
        this.owner = null;
    }

    /**
     * mortgage property to gain immediate funds, granting half its value to the owner
     */
    public void mortgageTile() throws InvalidHouseSetupException {
        mortgaged = true;
        owner.setMoney(owner.getMoney()+(costToBuy/2));
    }

    /**
     * cancel mortgage on tile, paying half of the value for this
     */
    public void unMortgageTile() throws NotEnoughMoneyException {
        try {
            checkIfEnoughMoney((costToBuy/2),owner.getMoney(),owner); //check if owner has enough money
            mortgaged = false; //if no exceptions then unmortgage
            owner.setMoney(owner.getMoney() - (costToBuy / 2)); //and subtract money from the player who unmortgaged
        }
        catch(NotEnoughMoneyException e){
            throw new NotEnoughMoneyException(e.getMessage(),e.moneyShort,e.player); //pass the exception up
        }
    }

    /**
     * finds the highest bidder and makes them the owner of the tile
     * @param players list of players in the game
     * @param bids list of player bids
     * @return the index of the winner of the auction
     */
    public int auction(Player[] players, int[] bids){
        int highestBid= 0;
        int highestBidder = 0;
        for(int i =0; i<bids.length;i++){ //go through each bid
            if(bids[i]> bids[highestBid]){ //if bid higher than highestBid
                highestBid = bids[i]; //new highest bid
                highestBidder = i; //new highest bidder
            }
        }
        setOwner(players[highestBidder]);
        return highestBidder;
    }

    /**
     * buy the tile from the bank for base price
     * @param newOwner player purchasing the tile
     */
    public void buyTile(Player newOwner) throws NotEnoughMoneyException{
        try{
            checkIfEnoughMoney(costToBuy,newOwner.getMoney(),newOwner);
            setOwner(newOwner);
            newOwner.setMoney(newOwner.getMoney() - costToBuy);
        }
        catch(NotEnoughMoneyException e){
            throw new NotEnoughMoneyException(e.getMessage(),e.moneyShort,e.player);
        }
    }

    /**
     * player pays rent to another player
     * @param debtor player who owes rent to the owner of tile
     */
    public int rent(Player debtor){
        //here to be overridden
        System.out.println("BEN YOU HAD ONE JOB WHY THIS PRINTING");
        return 0;
    }

    /**
     * Checks whether or not the player has enough money to perform that action and throws an exception if they don't
     * @param cost
     * @param fundsAvailable
     * @param player
     * @throws NotEnoughMoneyException
     */
    public void checkIfEnoughMoney(int cost, int fundsAvailable,Player player) throws NotEnoughMoneyException {
        if(cost>fundsAvailable){
            throw new NotEnoughMoneyException("you don't have enough money to do that",cost-fundsAvailable,player);
        }
    }
}
