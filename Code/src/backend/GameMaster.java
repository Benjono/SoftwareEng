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
        }
        canMove();
        canNotTakeTurn();
    }

    /**
     * This function will move player who's turn it is and then decide, based on the rolls returned, whether
     * or not to allow them to roll again.
     *
     * @return int array
     * @author Jonathan Morris
     */
    public int[] moveNextPiece(){
        int[] rolls = players[curTurn].move(board.getTileGrid().length);
        canNotMove();
        canTakeTurn();
        if (rolls[0]==rolls[1]){
            canMove();
            canNotTakeTurn();
        }
        return rolls;
    }

    /**
     * This function causes the next turn to happen.
     *
     * @author Jonathan Morris
     */
    public void nextTurn(){
        if(isCanNextTurn()) {
            canNotTakeTurn();
            canMove();
            setCurTurn((getCurTurn() + 1) % players.length);
        }
    }
    /******************************
     * Getters and Setters
     ******************************/
    /**
     * This function says that the next turn can be taken (so all actions that must be done by a player are done)
     *
     * @author Jonathan Morris
     */
    public void canTakeTurn(){
        canNextTurn=true;
    }

    /**
     * This function says that the next turn can't be taken
     *
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
    public void canMove(){
        canMove=true;
    }
    public void canNotMove(){
        canMove=false;
    }
    public int getCurTurn() {
        return curTurn;
    }
    public void setCurTurn(int newCurTurn){
        curTurn = newCurTurn;
    }

    public Player[] getPlayers() {
        return players;
    }
    public Player getPlayer(int player){
        return players[player];
    }
}
