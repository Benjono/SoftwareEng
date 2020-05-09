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
     * @return int amount the property is worth to the bank
     */
    protected void sellBaseTile() throws InvalidHouseSetupException{
        if(!mortgaged){
            owner.setMoney(owner.getMoney() + costToBuy);
        }
        else{
            owner.setMoney(owner.getMoney() +(costToBuy/2));
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
            checkIfEnoughMoney((costToBuy/2),owner.getMoney(),owner);
            mortgaged = false;
            owner.setMoney(owner.getMoney() - (costToBuy / 2));
        }
        finally{

        }
    }

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
     * buy the tile from the bank for base price
     * @param newOwner player purchasing the tile
     */
    public void buyTile(Player newOwner) throws NotEnoughMoneyException{
        try{
            checkIfEnoughMoney(costToBuy,newOwner.getMoney(),newOwner);
            setOwner(newOwner);
            newOwner.setMoney(newOwner.getMoney() - costToBuy);
        }
        finally{

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
    public void checkIfEnoughMoney(int cost, int fundsAvailable,Player player) throws NotEnoughMoneyException {
        if(cost>fundsAvailable){
            throw new NotEnoughMoneyException("you don't have enough money to do that",cost-fundsAvailable,player);
        }
    }
}
