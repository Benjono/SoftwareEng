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
     * @param numPlayers
     * @param playerTokens
     */
    public void setup(boolean[] playerOrAI, Tokens[] playerTokens, int startRounds){
        players = new Player[playerOrAI.length];
        numRounds = startRounds;
        this.totNumPlayers =playerOrAI.length;
        numAI = 0;
        int jail = 0;
        for(int i=0;i<board.getTileGrid().length;i++){
            if(board.getTileGrid()[i].getName().equals("Jail")){
                jail=i;
            }
        }
        for(int i = 0; i<playerOrAI.length;i++){
            if(playerOrAI[i]==true){
                players[i]=new Player(playerTokens[i],jail);
                players[i].setMoney(1500);
            } else {
                players[i]=new AI(playerTokens[i],jail);
                players[i].setMoney(1500);
                numAI++;
            }
        }
        canMove();
        canNotTakeTurn();
    }
    public int getNumAI(){
        return numAI;
    }

    public int winner() {
        int[] money = new int[this.totNumPlayers];
        int winner = 0;
        for (int i = 0; i < this.totNumPlayers; i++) {
            if (players[i] != null) {
                Tile[] playerTiles = this.getPlayerProperties(players[i]);
                money[i] = players[i].getMoney();
                for (Tile t : playerTiles) {
                    if (t instanceof BuyableTile) {
                        money[i] += ((BuyableTile) t).getCostToBuy();
                        if (((BuyableTile) t).mortgaged) {
                            money[i] -= ((BuyableTile) t).getCostToBuy() / 2;
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
    public boolean gameWon(){
        int playersLeft = 0;
        for (Player p: players){
            if(p!=null){
                playersLeft++;
            }
        }
        return playersLeft==1||numRounds==0;
    }
    public boolean playerLost() {
        if (players[this.getCurTurn()].getMoney() < 1) {
            Tile[] playersProperties = this.getPlayerProperties(players[this.getCurTurn()]);
            int monet = players[this.getCurTurn()].getMoney();
            for (Tile t : playersProperties) {
                ((BuyableTile)t).mortgaged=false;
                if(t instanceof Property){
                    ((Property) t).sellHouse(((Property) t).getCurrentHouseLevel());
                }
                ((BuyableTile) t).owner = null;
            }
            players[this.getCurTurn()].setMoney(monet);
            if (players[this.getCurTurn()].getOutOfJailFreeOpportunity().size() > 0) {
                for (int i = 0; i < players[this.getCurTurn()].getOutOfJailFreeOpportunity().size(); i++) {
                    board.getOpportunityKnocks().add(players[this.getCurTurn()].getOutOfJailFreeOpportunity().get(i));
                }
            } else if (players[this.getCurTurn()].getOutOfJailFreePotLuck().size() > 0) {
                for (int i = 0; i < players[this.getCurTurn()].getOutOfJailFreeOpportunity().size(); i++) {
                    board.getPotLuck().add(players[this.getCurTurn()].getOutOfJailFreePotLuck().get(i));
                }
            }
            totNumPlayers--;
            players[this.getCurTurn()] = null;
        }

        return players[this.getCurTurn()].getMoney() < 1;
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
            if((getCurTurn()+1)> totNumPlayers){
                numRounds--;
            }
            setCurTurn((getCurTurn() + 1) % totNumPlayers);
        }
        while(players[this.getCurTurn()]==null){
            setCurTurn((getCurTurn() + 1) % totNumPlayers);
        }
    }

    public boolean canBuyHouse(Property tileToBuyHouseOn, Player player){
        Tile[] tiles = board.getTileGrid();
        int numOwnedByPlayer = 0;
        int numOnBoard = 0;
        int min=50;

        for (Tile t: tiles){
            if(t instanceof Property){
                if(((Property) t).colour==tileToBuyHouseOn.colour){
                    numOnBoard++;
                    if(player.equals(((Property) t).owner)){
                        numOwnedByPlayer++;
                        if(((Property) t).getCurrentHouseLevel()<min){
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
    public int applyTileEffect(){
        Tile curTile = this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace());
        if (curTile instanceof BuyableTile){
            return ((BuyableTile) curTile).rent(this.getPlayer(this.getCurTurn()));
        } else if (curTile instanceof Tax){
            ((Tax) curTile).payTax(this.getPlayer(this.getCurTurn()));
            for(Tile t: board.getTileGrid()){
                if (t instanceof FreeParking){
                    ((FreeParking) t).setCurrentPenalties(((FreeParking) t).getCurrentPenalties()+((Tax) curTile).getTax());
                }
            }
            return ((Tax) curTile).getTax();
        } else if (curTile instanceof toJail){
            players[getCurTurn()].jail();
        } else if (curTile instanceof FreeParking){
            players[getCurTurn()].setMoney(players[getCurTurn()].getMoney()+((FreeParking)curTile).getCurrentPenalties());
            int penalties = ((FreeParking)curTile).getCurrentPenalties();
            ((FreeParking)curTile).setCurrentPenalties(0);
            return penalties;
        } else if (curTile instanceof CardDraw){
            if(((CardDraw)curTile).getDrawType().equals(DrawTypes.opportunityKnocks)){
                Card c = (Card)board.getOpportunityKnocks().get(0);
                c.cardEffect(this.getPlayer(this.getCurTurn()));
                board.getOpportunityKnocks().remove(c);
                if(!c.getMethodName().equals("getOutOfJail")) {
                    board.getOpportunityKnocks().add(c);
                }
            } else{
                Card c = (Card)board.getPotLuck().get(0);
                c.cardEffect(this.getPlayer(this.getCurTurn()));
                board.getPotLuck().remove(c);
                if(!c.getMethodName().equals("getOutOfJail")) {
                    board.getPotLuck().add(c);
                }
            }
        }
        return 0;
    }
    public int applyTileEffect(int roll){
        return ((Utility)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).rent(this.getPlayer(this.getCurTurn()),roll);
    }
    /**
     * For buying a tile
     * @param newOwner
     */
    public void applyTileEffect(Player newOwner) throws NotEnoughMoneyException{
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
