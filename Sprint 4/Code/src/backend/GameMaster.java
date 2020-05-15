package backend;

public class GameMaster {
    private Board board;
    private Player[] players;
    private int totNumPlayers;
    private int numAI;
    private int curTurn;
    private boolean canMove;
    private boolean canNextTurn;
    private int numRounds;
    /**
     * Constructor
     * @author Jonathan Morris
     */
    public GameMaster(){
        board = new Board(this);
        setCurTurn(0);
    }
    /**
     * This function does the setup for the game.
     * @author Jonathan Morris
     * @param playerOrAI takes a boolean array, true for player, false for AI
     * @param playerTokens takes a Token array for the tokens the players are to use in order
     * @param startRounds the number of rounds for the abridged game. -1 is a non-abridged game.
     */
    public void setup(boolean[] playerOrAI, Tokens[] playerTokens, int startRounds){
        players = new Player[playerOrAI.length]; //instantiate player array
        numRounds = startRounds; //number of rounds = startRounds
        this.totNumPlayers =playerOrAI.length; //total number of players to start with is the length of playerOrAI
        numAI = 0;
        int jail = 0;
        for(int i=0;i<board.getTileGrid().length;i++){  //go over the tiles
            if(board.getTileGrid()[i].getName().equals("Jail")){ //find jail
                jail=i;
            }
        } //jail defaults to 0 if there is no jail
        for(int i = 0; i<playerOrAI.length;i++){ //for each item in playerOrAI
            if(playerOrAI[i]==true){ //if true
                players[i]=new Player(playerTokens[i],jail); //create player
                players[i].setMoney(1500);
            } else { //otherwise
                players[i]=new AI(playerTokens[i],jail); //create AI
                players[i].setMoney(1500);
                numAI++;
            }
        }
        canMove(); //first player can move
        canNotTakeTurn(); //first player can not take their turn
    }
    public int getNumAI(){
        return numAI;
    }

    /**
     * Returns the index of the player that won
     * @return
     */
    public int winner() {
        int[] money = new int[players.length]; //initialise array with the number of players who started the game
        int winner = 0;
        for (int i = 0; i < this.players.length; i++) { //for each player
            if (players[i] != null) { //if there is a player
                Tile[] playerTiles = this.getPlayerProperties(players[i]);
                money[i] = players[i].getMoney(); //get their money
                for (Tile t : playerTiles) { //for each tile the player owns
                    if (t instanceof BuyableTile) {
                        money[i] += ((BuyableTile) t).getCostToBuy(); //add the cost of it to the players wealth
                        if (((BuyableTile) t).mortgaged) { //if mortgaged then subtract half the cost
                            money[i] -= ((BuyableTile) t).getCostToBuy() / 2;
                        }
                        if (t instanceof Property){
                            money[i] += ((Property) t).getCurrentHouseLevel()*((Property) t).getHouseCost();
                        }
                    }
                }
            }
            if (money[i] > money[winner]) {
                winner = i;
            }
        }

        return winner;
    }
    /**
     * This function will move player who's turn it is and then decide, based on the rolls returned, whether
     * or not to allow them to roll again. Will stop them from rolling if they aren't allowed to roll anymore
     *
     * @return int array of rolls or null if player can't move
     * @author Jonathan Morris
     */
    public int[] moveNextPiece(){
        if(isCanMove() && players[curTurn].getJailTime()==0) { //is the player allowed to move (turn passed, double rolled) AND not in jail
            int[] rolls = players[curTurn].move(board.getTileGrid().length); //get rolls
            canNotMove();
            canTakeTurn();
            if (rolls[0] == rolls[1]) { //if a double is rolled
                if(players[curTurn].getTurnsTaken()==2){
                    players[curTurn].jail();
                } else {
                    canMove(); //can move
                    players[curTurn].setTurnsTaken(players[curTurn].getTurnsTaken() + 1); //increment number of turns taken in a row
                    canNotTakeTurn(); //can't pass turn
                }
            }
            return rolls;
        } else if (isCanMove() && players[curTurn].getJailTime()>0){ //if in jail
            players[curTurn].setJailTime(players[curTurn].getJailTime()-1); //just decrease remaining jail time
            canNotMove();
            canTakeTurn();
        }
        return null;
    }

    /**
     * Returns true if the game has been won, either the number of rounds left being 0 or the number of players left is 1
     * @return true if the game has been won, false otherwise
     */
    public boolean gameWon(){
        int playersLeft = 0;
        for (Player p: players){ //for each player
            if(p!=null){ //if they haven't lost
                playersLeft++; //increment the number of players left
            }
        }
        System.out.println(playersLeft);
        return playersLeft==1||numRounds==0; //if there is one player or 0 rounds left
    }

    /**
     * Decides whether or not the player whose current turn it is has lost the game by having no money left.
     * @return true if the player lost, false otherwise
     */
    public boolean playerLost() {
        System.out.println(players[this.getCurTurn()].getMoney()<1);
        if (players[this.getCurTurn()].getMoney() < 1) { //if they have no money
            Tile[] playersProperties = this.getPlayerProperties(players[this.getCurTurn()]);
            int monet = players[this.getCurTurn()].getMoney();
            System.out.println(playersProperties.length);
            for (Tile t : playersProperties) {
                if (t!=null) {
                    System.out.println(t.getName());
                    if (t instanceof BuyableTile) {
                        if (t instanceof Property) { //make sure they don't own property, any mortgages are absolved
                            ((Property) t).mortgaged = false;
                            ((Property) t).sellHouse(((Property) t).getCurrentHouseLevel());
                        }
                        System.out.println(((BuyableTile) t).owner);
                        ((BuyableTile) t).owner = null;
                    }
                }
            }
            players[this.getCurTurn()].setMoney(monet);
            if (players[this.getCurTurn()].getOutOfJailFreeOpportunity().size() > 0) { //return get out of jail free cards for Opportunity Knocks
                for (int i = 0; i < players[this.getCurTurn()].getOutOfJailFreeOpportunity().size(); i++) {
                    board.getOpportunityKnocks().add(players[this.getCurTurn()].getOutOfJailFreeOpportunity().get(i));
                }
            } else if (players[this.getCurTurn()].getOutOfJailFreePotLuck().size() > 0) { //return get out of jail free cards for Pot Luck
                for (int i = 0; i < players[this.getCurTurn()].getOutOfJailFreeOpportunity().size(); i++) {
                    board.getPotLuck().add(players[this.getCurTurn()].getOutOfJailFreePotLuck().get(i));
                }
            }
            totNumPlayers--;
            players[this.getCurTurn()] = null;
        }

        return players[this.getCurTurn()]==null;
    }

    /**
     * This function causes the next turn to happen if the player is allowed to.
     * @author Jonathan Morris
     */
    public void nextTurn() {
        if (isCanNextTurn()) {
            players[curTurn].setTurnsTaken(0); //no more turns taken in a row
            canNotTakeTurn();
            canMove();
            if((getCurTurn()+1)>= players.length){ //decriment number of rounds if the next turn would roll around to the first player
                numRounds--;
            }
            setCurTurn((getCurTurn() + 1) % players.length);
        }
        while(players[this.getCurTurn()]==null){
            setCurTurn((getCurTurn() + 1) % players.length);
        }
    }

    /**
     * Decides whether or not a house can be bought
     * @param tileToBuyHouseOn
     * @param player
     * @return true if a house can be bought on the tile, false otherwise
     */
    public boolean canBuyHouse(Property tileToBuyHouseOn, Player player){
        Tile[] tiles = board.getTileGrid();
        int numOwnedByPlayer = 0;
        int numOnBoard = 0;
        int min=50;

        for (Tile t: tiles){ //for each tile
            if(t instanceof Property){ //if a property
                if(((Property) t).colour==tileToBuyHouseOn.colour){ //if colour matches the one that is in parameter
                    numOnBoard++; //increment number of same colour on board
                    if(player.equals(((Property) t).owner)){ //if they are the owner
                        numOwnedByPlayer++; //increment number owned by player
                        if(((Property) t).getCurrentHouseLevel()<min){ //get the minimum house level.
                            min=((Property) t).getCurrentHouseLevel();
                        }
                    }
                }
            }
        }

        return numOwnedByPlayer==numOnBoard&&min+2>tileToBuyHouseOn.currentHouseLevel+1;
    }

    /**
     * THis function takes the tile number and returns whether or not that tile can be bought
     * @param tileNum the tile number the player is on
     * @return whether the tile can be bought
     */
    public boolean getBuyable(int tileNum){
        if(this.getBoard().getTile(tileNum) instanceof BuyableTile){ //if it is a tile that can be bought
            if(((BuyableTile) this.getBoard().getTile(tileNum)).owner==null){ //and no-one owns it
                return true; //true
            } else {
                return false; //otherwise false
            }
        }
        return false;
    }

    /**
     * Gets the players properties
     * @param p
     * @return
     */
    public Tile[] getPlayerProperties(Player p){
        Tile[] playerProperties = new Tile[40];
        int count = 0;
        for(Tile t: board.getTileGrid()){
            if(t instanceof BuyableTile && ((BuyableTile) t).getPlayer()!=null){
                if(((BuyableTile) t).getPlayer().equals(p)){
                    playerProperties[count]=t;
                    count++;
                }
            }
        }
        return playerProperties;
    }

    /**
     * gets the number of properties a player has
     * @param p
     * @return
     */
    public int getNumPlayerProperties(Player p){
        int count=0;
        for(Tile t: board.getTileGrid()){
            if(t instanceof BuyableTile && ((BuyableTile) t).getPlayer()!=null){
                if(((BuyableTile) t).getPlayer().equals(p)){
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * Used for tiles that have been bought or have other effects
     */
    public int applyTileEffect() {
        Tile curTile = this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace());
        if (curTile instanceof BuyableTile) { //if a buyable tile
            return ((BuyableTile) curTile).rent(this.getPlayer(this.getCurTurn())); //rent it
        } else if (curTile instanceof Tax) { //if tax
            ((Tax) curTile).payTax(this.getPlayer(this.getCurTurn())); //pay the tax
            for (Tile t : board.getTileGrid()) {
                if (t instanceof FreeParking) { //add tax to free parking
                    ((FreeParking) t).setCurrentPenalties(((FreeParking) t).getCurrentPenalties() + ((Tax) curTile).getTax());
                }
            }
            return ((Tax) curTile).getTax();
        } else if (curTile instanceof toJail) { //if go to jail
            players[getCurTurn()].jail(); //go to jail
        } else if (curTile instanceof FreeParking) { //if free parking
            players[getCurTurn()].setMoney(players[getCurTurn()].getMoney() + ((FreeParking) curTile).getCurrentPenalties()); //get money on free parking
            int penalties = ((FreeParking) curTile).getCurrentPenalties();
            ((FreeParking) curTile).setCurrentPenalties(0);
            return penalties;
        } else if (curTile instanceof CardDraw) { //if you draw a card
            if (((CardDraw) curTile).getDrawType().equals(DrawTypes.opportunityKnocks)) { //decide what type
                Card c = (Card) board.getOpportunityKnocks().get(0); //get the first card
                c.cardEffect(this.getPlayer(this.getCurTurn())); //apply the card effect
                board.getOpportunityKnocks().remove(c); //remove from front
                if (!c.getMethodName().equals("getOutOfJail")) { //if it's not a get out of jail free card
                    board.getOpportunityKnocks().add(c); //add to back
                }
            } else {
                Card c = (Card) board.getPotLuck().get(0);
                c.cardEffect(this.getPlayer(this.getCurTurn()));
                board.getPotLuck().remove(c);
                if (!c.getMethodName().equals("getOutOfJail")) {
                    board.getPotLuck().add(c);
                }
            }
        }
        return 0;
    }
    public int applyTileEffect(int roll){ //if utility call this
        return ((Utility)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).rent(this.getPlayer(this.getCurTurn()),roll);
    }
    /**
     * For buying a tile
     * @param newOwner
     */
    public void applyTileEffect(Player newOwner) throws NotEnoughMoneyException{ //if they want to buy the tile call this
        ((BuyableTile)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).buyTile(newOwner);
    }

    /**
     * FOr auctuioning a tile
     * @param money
     */
    public int applyTileEffect(int[] money){
        return ((BuyableTile)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).auction(this.getPlayers(),money);
    }
    /******************************
     * Getters and Setters
     ******************************/
    /**
     * This function says that the next turn can be taken (so all actions that must be done by a player are done
     * @author Jonathan Morris
     */
    public void canTakeTurn(){
        canNextTurn=true;
    }
    /**
     * This function says that the next turn can't be taken
     * @author Jonathan Morris
     */
    public void canNotTakeTurn() {
        canNextTurn=false;
    }
    /**
     * This function returns if 'canNextTurn' is true or false
     * @return boolean
     * @author Jonathan Morris
     */
    public boolean isCanNextTurn() {
        return canNextTurn;
    }
    /**
     * Sets whether the player can move to true
     */
    private void canMove(){
        canMove=true;
    }
    /**
     * Sets canMove to false
     */
    private void canNotMove(){
        canMove=false;
    }

    /**
     * Returns whether or not a player can move
     * @return boolean
     */
    public boolean isCanMove(){
        return canMove;
    }
    /**
     * Gets the current turn
     * @return int
     * @author Jonathan Morris
     */
    public int getCurTurn() {
        return curTurn;
    }
    /**
     * Sets the current turn to the integer passed to it
     * @param newCurTurn
     * @author Jonathan Morris
     */
    private void setCurTurn(int newCurTurn){
        curTurn = newCurTurn;
    }

    /**
     * Returns the array of players
     * @return Player[]
     * @author Jonathan Morris
     */
    public Player[] getPlayers() {
        return players;
    }
    /**
     * Takes an integer and returns the player at that index in the array players
     * @param player
     * @return Player
     * @author Jonathan Morris
     */
    public Player getPlayer(int player){
        return players[player];
    }

    /**
     * Getter for the board
     * @return
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Gets the tile in the tile array with theTile as the index
     * @param theTile
     * @return
     */
    public Tile getTile(int theTile){
        return board.getTile(theTile);
    }


}
