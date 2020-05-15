import backend.Board;
import backend.GameMaster;
import backend.Tokens;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    /**
     * This test tests for if the setup correctly assigns tokens.
     */
    @Test
    public void setupTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        boolean[] players = {true, true, true};
        gm.setup(players,tk, -1);
        Board b = gm.getBoard();

        Assertions.assertEquals("Gangsters Paradise", gm.getTile(3).getName());
        Assertions.assertEquals(40, b.getTileGrid().length);
    }
}
