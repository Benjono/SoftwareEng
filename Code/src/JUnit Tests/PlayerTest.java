
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void rollTest(){
        Player p = new Player(Tokens.Boot);
        int[] rolls = p.move(100);
        Assertions.assertTrue ((rolls[0] < 7) && (rolls[0] > 0));
        Assertions.assertTrue((rolls[1] < 7) && (rolls[1] > 0));
        Assertions.assertTrue(rolls[0] + rolls[1] < 13);
    }
    @Test
    public void tempTest(){
        Assertions.assertTrue(true);
    }

}