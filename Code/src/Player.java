import java.util.Random;

public class Player {

    private int place;
    private Random random;
    private Tokens token;
    private int[] countUtil;
    private int countTrain;

    public int[] getCountUtil() {
        return countUtil;
    }

    public void setCountUtil(int[] countUtil) {
        this.countUtil = countUtil;
    }

    public int getCountTrain() {
        return countTrain;
    }

    public void setCountTrain(int countTrain) {
        this.countTrain = countTrain;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Random getRandom() {
        return random;
    }

    public Tokens getToken() {
        return token;
    }

    // Returns two randomly generated numbers to represent dice rolls
    private int[] roll() {
        return new int[]{ getRandom().nextInt(6) + 1,getRandom().nextInt(6) + 1 };
    }

    public Player(Tokens thisToken){
        random = new Random();
        place = 0;
        token=thisToken;
        countTrain = 0;
        countUtil[0] = 0;
        countUtil[1] = 0;
    }

    public int[] move(int maxBoardTiles){
        int[] dice = roll();
        int rolls = dice[0] + dice[1];
        int place = getPlace() + rolls;
        if(place >= maxBoardTiles){
            place = place - maxBoardTiles;
        }
        return dice;
    }

}
