import java.util.Random;

public class Player {

    private int place;
    private Random random;
    private Tokens token;

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
    public int[] roll() {
        return new int[]{getRandom().nextInt(6) + 1, getRandom().nextInt(6) + 1};
    }

    public Player()
    {

    }

}
