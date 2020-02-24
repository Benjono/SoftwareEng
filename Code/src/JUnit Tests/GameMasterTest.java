import backend.GameMaster;
import backend.Tokens;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
public class GameMasterTest {
    /**
     * This test tests for if the setup correctly assigns tokens.
     */
    @Test
    public void setupTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
        Assertions.assertTrue(gm.getPlayers()[0].getToken()==Tokens.Boot);
        Assertions.assertTrue(gm.getPlayers()[1].getToken()==Tokens.Cat);
        Assertions.assertTrue(gm.getPlayers()[2].getToken()==Tokens.Goblet);

    }

    /**
     * This test checks that the move function works
     */
    @Test
    public void moveTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
        int notDouble = 0;
        while (notDouble<3){
            System.out.println(gm.getCurTurn());
            int[] rolls = gm.moveNextPiece();
            System.out.println(rolls[0]+" "+rolls[1]);
            if (rolls[0]!=rolls[1]){
                notDouble+=1;
            }
            if(gm.isCanNextTurn()) {
                gm.nextTurn();
            }
        }
        Assertions.assertEquals(0,gm.getCurTurn());
    }
}
