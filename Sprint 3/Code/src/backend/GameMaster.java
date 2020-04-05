package backend;

public class GameMaster {
    private Board board;
    private Player[] players;
    private int curTurn;
    private boolean canMove;
    private boolean canNextTurn;
    /**
     * Constructor
     * @author Jonathan Morris
     */
    public GameMaster(){
        board = new Board();
        setCurTurn(0);
    }
    /**
     * This function does the setup for the game.
     * @author Jonathan Morris
     * @param numPlayers
     * @param playerTokens
     */
    public void setup(int numPlayers, Tokens[] playerTokens){
        players = new Player[numPlayers];
        int jail = 0;
        for(int i=0;i<board.getTileGrid().length;i++){
            if(board.getTileGrid()[i].getName().equals("Jail")){
                jail=i;
            }
        }
        for(int player=0;player<numPlayers;player++){
            players[player]=new Player(playerTokens[player],jail);
            players[player].setMoney(1500);
        }
        canMove();
        canNotTakeTurn();
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
     * This function causes the next turn to happen if the player is allowed to.
     * @author Jonathan Morris
     */
    public void nextTurn() {
        if (isCanNextTurn()) {
            players[curTurn].setTurnsTaken(0); //no more turns taken in a row
            canNotTakeTurn();
            canMove();
            setCurTurn((getCurTurn() + 1) % players.length);
        }

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
     * Used for tiles that have been bought or have other effects
     */
    public void applyTileEffect(){
        Tile curTile = this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace());
        if (curTile instanceof BuyableTile){
            ((BuyableTile) curTile).rent(this.getPlayer(this.getCurTurn()));
        } else if (curTile instanceof Tax){
            ((Tax) curTile).payTax(this.getPlayer(this.getCurTurn()));
        } else if (curTile instanceof toJail){
            players[getCurTurn()].jail();
        } else if (curTile instanceof FreeParking){
            players[getCurTurn()].setMoney(players[getCurTurn()].getMoney()+((FreeParking)curTile).getCurrentPenalties());
            ((FreeParking)curTile).setCurrentPenalties(0);
        } else if (curTile instanceof CardDraw){
            //none
        }
    }

    /**
     * For buying a tile
     * @param newOwner
     */
    public void applyTileEffect(Player newOwner){
        ((BuyableTile)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).buyTile(newOwner);
    }

    /**
     * FOr auctuioning a tile
     * @param money
     */
    public void applyTileEffect(int[] money){
        ((BuyableTile)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).auction(this.getPlayers(),money);
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

    public Board getBoard(){
        return board;
    }
    public Tile getTile(int theTile){
        return board.getTile(theTile);
    }


}
