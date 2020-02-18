public class GameMaster {
    private Board board;
    private Player[] players;
    private int curTurn;
    private boolean canMove;
    private boolean canNextTurn;

    public GameMaster(){
        board = new Board();
        setCurTurn(0);
    }

    /**
     * This function
     * @Author Jonathan Morris
     * @param numPlayers
     * @param playerTokens
     */
    public void setup(int numPlayers, Tokens[] playerTokens){
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
     */
    public int[] moveNextPiece(){
        int[] rolls = players[curTurn].move();
        canNotMove();
        canTakeTurn();
        if (rolls[0]==rolls[1]){
            canMove();
            canNotTakeTurn();
        }
        return rolls;
    }

    /**
     * This function
     */
    public void nextTurn(){
        canNotTakeTurn();
        canMove();
        setCurTurn((getCurTurn()+1)%players.length);
    }
    public void canTakeTurn(){
        canNextTurn=true;
    }
    public void canNotTakeTurn() {
        canNextTurn=false;
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
}
