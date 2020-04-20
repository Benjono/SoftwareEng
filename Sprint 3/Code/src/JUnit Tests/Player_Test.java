
import backend.GameMaster;
import backend.Player;
import backend.Tokens;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Player_Test {

    @Test
    public void rollTest() {
        Player p = new Player(Tokens.Boot);
        int[] rolls = p.move(100);
        Assertions.assertTrue((rolls[0] < 7) && (rolls[0] > 0));
        Assertions.assertTrue((rolls[1] < 7) && (rolls[1] > 0));
        Assertions.assertTrue(rolls[0] + rolls[1] < 13);
    }

    @Test
    public void moveTest() {
        Player p = new Player(Tokens.Cat);
        Assertions.assertTrue(p.getPlace() == 0);
        int[] rolls = p.move(100);
        Assertions.assertTrue(p.getPlace() != 0);
        Assertions.assertTrue(p.getPlace() != 1);
    }

    @Test
    public void tokenCreation() {
        Player p = new Player(Tokens.Cat);
        Assertions.assertTrue(p.getToken() == Tokens.Cat);
    }

    @Test
    public void stationAndUtilCreation() {
        Player p = new Player(Tokens.Cat);
        Assertions.assertTrue(p.getCountTrain() == 0);
        Assertions.assertTrue(p.getCountUtil() == 0);
    }

    @Test
    public void moneyTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
        //On setup, all players should have 1500 for their money
        for(int i = 0; i < gm.getPlayers().length; i++) {
            Assertions.assertEquals(1500, gm.getPlayer(i).getMoney());
        }
    }
}