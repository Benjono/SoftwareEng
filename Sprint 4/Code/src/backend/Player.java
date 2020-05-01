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
    private boolean passedGo;

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
        passedGo=false;
    }

    /**
     * Moves the player a number of spaces equal to the dice roll
     * @param maxBoardTiles the maximum number of board tiles
     * @return int[] the rolls of the player
     * @author Jonathan Morris
     * @author Joseph Corbett
     */
    public int[] move(int maxBoardTiles){
        int[] dice = roll();
        int rolls = dice[0] + dice[1];
        if((getPlace() + rolls) % maxBoardTiles!=(getPlace() + rolls)){
            this.setMoney(this.getMoney()+200);
            passedGo=true;
        }
        place = (getPlace() + rolls) % maxBoardTiles;

        return dice;
    }

    /**
     * Jails the player
     */
    public void jail(){
        this.setPlace(jail);
        this.setJailTime(2);
    }

    /**
     * Getter for the amount of money the player has
     * @return
     */
    public int getMoney() {
        return money;
    }

    /**
     * Setter for the amount of money the player has
     * @param money
     */
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

    /**
     * Getter for the amount of jail time the player has
     * @return
     */
    public int getJailTime() {
        return jailTime;
    }

    /**
     * Getter for if the player has one (or more) out of jail free cards from pot luck
     * @return
     */
    public int getOutOfJailFreePotLuck() {
        return outOfJailFreePotLuck;
    }

    /**
     * Getter for if the player has one (or more) out of jail free cards from opportunity knocks
     * @return
     */
    public int getOutOfJailFreeOpportunity() {
        return outOfJailFreeOpportunity;
    }

    /**
     * setter for the jail time
     * @param jailTime
     */
    public void setJailTime(int jailTime) {
        this.jailTime = jailTime;
    }
    /**
     * Setter for if the player has one (or more) out of jail free cards from opportunity knocks
     * @return
     */
    public void setOutOfJailFreePotLuck(int outOfJailFreePotLuck) {
        this.outOfJailFreePotLuck = outOfJailFreePotLuck;
    }
    /**
     * Setter for if the player has one (or more) out of jail free cards from opportunity knocks
     * @return
     */
    public void setOutOfJailFreeOpportunity(int outOfJailFreeOpportunity) {
        this.outOfJailFreeOpportunity = outOfJailFreeOpportunity;
    }
    /**
     * Getter for the number of turns the player has had
     * @return
     */
    public int getTurnsTaken() {
        return turnsTaken;
    }

    /**
     * Setter for the number of turns the player has had
     * @param turnsTaken
     */
    public void setTurnsTaken(int turnsTaken) {
        this.turnsTaken = turnsTaken;
    }
}