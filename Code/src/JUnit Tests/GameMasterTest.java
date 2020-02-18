import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
public class GameMasterTest {
    @Test
    public void setupTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
        Assertions.assertTrue(gm.getPlayers()[0].getToken()==Tokens.Boot);
        Assertions.assertTrue(gm.getPlayers()[1].getToken()==Tokens.Cat);
        Assertions.assertTrue(gm.getPlayers()[2].getToken()==Tokens.Goblet);

    }
}
