import org.junit.Test;

public class PlayerTest {

    @Test
    public void rollTest(){
        Player p = new Player();
        int[] rolls = p.roll();
        assert (rolls[0] < 7) && (rolls[0] > 0);
        assert (rolls[1] < 7) && (rolls[1] > 0);
        assert (rolls[0] + rolls[1] < 13);
    }

}