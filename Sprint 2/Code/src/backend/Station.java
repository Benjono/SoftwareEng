package backend;

public class Station extends Tile{
    int costToBuy;
    int[] rent;
    Player owner;
    boolean mortgage;

    /**
     * takes given parameters and constructs a station
     * @param costToBuy cost to buy tile
     * @param rent array of rent payed. indexes correspond to amount of stations the player owns
     * @author Alex
     */
    public Station(String name, int costToBuy, int[] rent){
        setBuyable(true);
        setName(name);
        mortgage = false;
        this.costToBuy = costToBuy;
        this.rent = rent;
    }
    /**
     * sets a player as the owner of the tile
     * @param owner - new owner of tile
     */
    public void setOwner(Player owner){
        this.owner = owner;
        owner.setCountTrain(owner.getCountTrain()+1);
    }

    /**
     * sets owner to null - selling property to bank
     * @return int amount the property is worth to the bank
     */
    private void sellBaseProperty(){
        owner.setCountTrain(owner.getCountTrain()-1);
        this.owner = null;
        if(!mortgage){
            owner.setMoney(owner.getMoney() + costToBuy);
        }
        else{
            owner.setMoney(owner.getMoney() +(costToBuy/2));
        }
    }
    public void mortgageProperty() throws InvalidHouseSetupException{
        mortgage = true;
        owner.setMoney(owner.getMoney()+(costToBuy/2));
    }

    public void unMortgageProperty(){
        mortgage = false;
        owner.setMoney(owner.getMoney()-(costToBuy/2));
    }

    /**
     * returns if the tile is mortgaged
     * @return mortgage, if the tile is mortgaged
     */
    public boolean getMortgage(){
        return mortgage;
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
        debtor.setMoney(debtor.getMoney() - rent[owner.getCountTrain()]);
        owner.setMoney(owner.getMoney() - rent[owner.getCountTrain()]);
    }

}
