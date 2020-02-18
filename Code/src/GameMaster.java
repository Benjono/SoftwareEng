public class GameMaster {
    Board board;
    Player[] players;
    int curTurn;
    boolean canMove;
    boolean canNextTurn;
    public GameMaster(){
        board = new Board();
        curTurn=0;
    }
    public void setup(int numPlayers, Tokens[] playerTokens){
        for(int player=0;player<numPlayers;player++){
            players[player]=new Player(playerTokens[player]);
        }
        canMove = true;
        canNextTurn = false;
    }

    /**
     *
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
    public void nextTurn(){
        canNotTakeTurn();
        canMove();
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
}
