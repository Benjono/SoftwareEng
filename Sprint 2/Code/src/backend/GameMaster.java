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
        for(int player=0;player<numPlayers;player++){
            players[player]=new Player(playerTokens[player]);
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
        if(isCanMove()) {
            int[] rolls = players[curTurn].move(board.getTileGrid().length);
            canNotMove();
            canTakeTurn();
            if (rolls[0] == rolls[1]) {
                canMove();
                canNotTakeTurn();
            }
            return rolls;
        }
        return null;
    }

    /**
     * This function causes the next turn to happen if the player is allowed to.
     * @author Jonathan Morris
     */
    public void nextTurn() {
        if (isCanNextTurn()) {
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
        if(this.getBoard().getTile(tileNum) instanceof BuyableTile){
            if(((BuyableTile) this.getBoard().getTile(tileNum)).owner==null){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void applyTileEffect(){
        Tile curTile = this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace());
        if (curTile instanceof BuyableTile){
            ((BuyableTile) curTile).rent(this.getPlayer(this.getCurTurn()));
        } else if (curTile instanceof Tax){
            ((Tax) curTile).payTax(this.getPlayer(this.getCurTurn()));
        } else if (curTile instanceof toJail){
            //none
        } else if (curTile instanceof FreeParking){
            //none
        } else if (curTile instanceof CardDraw){
            //none
        }
    }
    public void applyTileEffect(Player newOwner){
        ((BuyableTile)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).buyTile(newOwner);
    }
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
