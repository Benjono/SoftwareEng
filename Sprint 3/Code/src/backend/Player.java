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
    private int countUtil;
    private int countTrain;
    private int money;
    private int jailTime;
    private int turnsTaken;
    private int outOfJailFreePotLuck;
    private int outOfJailFreeOpportunity;
    private int jail;

    /**
     * Constructor, takes the token to be assigned to the player
     * @param thisToken
     */
    public Player(Tokens thisToken, int jailAt){
        random = new Random();
        place = 0;
        token=thisToken;
        countTrain = 0;
        countUtil = 0;
        jailTime=0;
        outOfJailFreeOpportunity=0;
        outOfJailFreePotLuck=0;
        turnsTaken=0;
        jail=jailAt;
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
        if((getPlace() + rolls) % maxBoardTiles!=(getPlace() + rolls)){
            this.setMoney(this.getMoney()+200);
        }
        place = (getPlace() + rolls) % maxBoardTiles;

        return dice;
    }

    public void jail(){
        this.setPlace(jail);
        this.setJailTime(2);
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
    public int getCountUtil() {
        return countUtil;
    }

    /**
     * Sets the array
     * @param countUtil
     */
    public void setCountUtil(int countUtil) {
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

    public int getJailTime() {
        return jailTime;
    }

    public int getOutOfJailFreePotLuck() {
        return outOfJailFreePotLuck;
    }

    public int getOutOfJailFreeOpportunity() {
        return outOfJailFreeOpportunity;
    }

    public void setJailTime(int jailTime) {
        this.jailTime = jailTime;
    }

    public void setOutOfJailFreePotLuck(int outOfJailFreePotLuck) {
        this.outOfJailFreePotLuck = outOfJailFreePotLuck;
    }

    public void setOutOfJailFreeOpportunity(int outOfJailFreeOpportunity) {
        this.outOfJailFreeOpportunity = outOfJailFreeOpportunity;
    }

    public int getTurnsTaken() {
        return turnsTaken;
    }

    public void setTurnsTaken(int turnsTaken) {
        this.turnsTaken = turnsTaken;
    }
}
