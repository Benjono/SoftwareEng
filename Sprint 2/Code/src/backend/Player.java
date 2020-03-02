package backend;

import java.util.Random;
/**
 * backend.Player class
 * Implements what the player does in the game
 * @author Joe C
 */

public class Player {

    private int place;
    private Random random;
    private Tokens token;
    private int[] countUtil;
    private int countTrain;
    private int money;

    /**
     * Constructor, takes the token to be assigned to the player
     * @param thisToken
     */
    public Player(Tokens thisToken){
        random = new Random();
        place = 0;
        token=thisToken;
        countTrain = 0;
        countUtil = new int[2];
        countUtil[0] = 0;
        countUtil[1] = 0;
    }

    /**
     *
     * @param maxBoardTiles
     * @return int[]
     * @author Jonathan Morris
     * @author Joseph Corbett
     */
    public int[] move(int maxBoardTiles){
        int[] dice = roll();
        int rolls = dice[0] + dice[1];
        place = (getPlace() + rolls) % maxBoardTiles;
        return dice;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }


    /**
     * Returns two randomly generated numbers to represent dice rolls
     * @return int[]
     * @author Jonathan Morris
     * @author Joseph Corbett
      */
    private int[] roll() {
        return new int[]{ getRandom().nextInt(6) + 1,getRandom().nextInt(6) + 1 };
    }
    /**
     * Gets the array of for the number of each type of utility the player owns
     * @return int[]
     * @author Alex Homer
     */
    public int[] getCountUtil() {
        return countUtil;
    }

    /**
     * Sets the array
     * @param countUtil
     */
    public void setCountUtil(int[] countUtil) {
        this.countUtil = countUtil;
    }

    /**
     * Gets the count for the number of trains the player has
     * @return
     */
    public int getCountTrain() {
        return countTrain;
    }

    /**
     * Sets the count of the number of trains the player has
     * @param countTrain
     */
    public void setCountTrain(int countTrain) {
        this.countTrain = countTrain;
    }

    /**
     * Gets the players position around the board
     * @return int
     */
    public int getPlace() {
        return place;
    }

    /**
     * Sets where the player is on the board.
     * @param place
     */
    public void setPlace(int place) {
        this.place = place;
    }

    /**
     * Gets the random variable
     * @return
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Gets the token assigned to the player
     * @return
     */
    public Tokens getToken() {
        return token;
    }
}
