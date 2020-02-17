import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PlayerTest {

    @Test
    public void rollTest(){
        Player p = new Player();
        int[] rolls = p.roll();
        Assertions.assertTrue ((rolls[0] < 7) && (rolls[0] > 0));
        Assertions.assertTrue((rolls[1] < 7) && (rolls[1] > 0));
        Assertions.assertTrue(rolls[0] + rolls[1] < 13);
    }

}