public class GameMaster {
    Board board;
    Player[] players;
    int curTurn;
    public GameMaster(){
        board = new Board();
        curTurn=0;
    }
    public void setup(int numPlayers, Tokens[] playerTokens){
        for(int player=0;player<numPlayers;player++){
            players[player]=new Player();
        }
        for(int token=0;token<numPlayers;token++){
            //players[token].setToken(playerTokens[token]);
        }
    }
}
