import backend.GameMaster;
import backend.Tokens;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TileTest {
    @Test
    public void yeet(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
    }
}
