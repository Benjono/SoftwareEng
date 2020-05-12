import backend.GameMaster;
import backend.Tokens;
import frontend.*;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TileTest {
    @Test
    public void yeet(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        int[] players = {3,0};
        gm.setup(players,tk, -1);
    }
}
