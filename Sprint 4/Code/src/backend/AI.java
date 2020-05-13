package backend;
import java.util.ArrayList;
import java.util.Random;

/**
 * backend AI class
 * Automated decision making for AI players
 * @Author Alex
 */
public class AI extends Player {
    Random random;
    /**
     * constructor for AI player
     * @param thisToken
     * @param jailAt
     */
    public AI(Tokens thisToken, int jailAt){
        super(thisToken,jailAt);
        random = new Random();
    }

    /**
     *
     * @param property - property landed on by the AI
     * @return true for buying(no action needed), false for auction (trigger auction)
     * @throws triggerAuctionException - is thrown if auction is needed
     */
    public boolean purchaseOrAuction(BuyableTile property) throws NotEnoughMoneyException{
        boolean output;
        if(getMoney()<property.costToBuy){
            output = false;
        }
        else{
            //50/50 on buy or auction
            int comparitor = random.nextInt(100);
            if(comparitor <50){
                output = false;
            }
            else{
                try {
                    property.buyTile(this);
                    output = true;
                }
                catch(NotEnoughMoneyException e){
                    output = false;
                    throw new NotEnoughMoneyException(e.getMessage(),e.moneyShort,e.player);

                }
            }

        }
        return output;
    }

    /**
     * decides how much to bid for a property in an auction
     * @param property - property being auctioned
     * @return - how much the AI is willing to bid on the property
     */
    public int getBidOffer(Property property){
        int currentOffer =(int) Math.floor(property.getCostToBuy()*0.9);
        currentOffer+=(Math.floor(currentOffer*(random.nextFloat()/2)));
        if(currentOffer>getMoney()){
            currentOffer = getMoney();
        }

        return currentOffer;
    }

    /**
     * buys houses sometimes after moving - will increase a whole colours housing level by 1 at a time, prioritising the most expensive
     * @param board board being played on
     */
    public void optionalStuff(Board board) throws NotEnoughMoneyException {
        //amount of money that can be spent on houses each turn, rounded up so as to be able to afford at least 1 house for 50
        int spendingMoney = (int) (50*Math.floor((0.4*getMoney())/50));
        int[] setPrices = new int[]{100,150,300,300,450,450,600,400};;
        //chance to do after every turn
        if(random.nextFloat()<=0.4){

            //see what colours are fully owned
            boolean ownedColours[] = fullyOwnedSets(board);


            boolean[] canUpgrade = new boolean[8];
            for(int i=0;i<setPrices.length;i++) {
                if(spendingMoney >=setPrices[i] && ownedColours[i]==true) {
                    canUpgrade[i] = true;
                }
                else {
                    canUpgrade[i] = false;
                }
            }
            boolean purchased = false;
            while(!purchased) {
                try {
                    purchased = purchase(board, canUpgrade);
                }
                catch(NotEnoughMoneyException e){
                    throw new NotEnoughMoneyException(e.getMessage(),e.moneyShort,e.player);
                }
            }

        }

    }

    /**
     *
     * @param board - board being used
     * @param purchasable array of how many sets have housing that can be built
     * @return inverse of if the method will be run again due to no purchase being made (random choice was already max upgraded)
     */
    private boolean purchase(Board board, boolean[] purchasable) throws NotEnoughMoneyException {
        boolean brought = false;
        ArrayList randomiser = new ArrayList();
        for(int i =0;i<purchasable.length;i++){
            if(purchasable[i]){
                randomiser.add(i);
            }
        }
        int chosen = random.nextInt(randomiser.size());
        Colours setToBuy;
        switch (chosen) {
            case 0:
                setToBuy = Colours.brown;
                break;

            case 1:
                setToBuy = Colours.cyan;
                break;

            case 2:
                setToBuy = Colours.purple;
                break;

            case 3:
                setToBuy = Colours.orange;
                break;

            case 4:
                setToBuy = Colours.red;
                break;

            case 5:
                setToBuy = Colours.yellow;
                break;

            case 6:
                setToBuy = Colours.green;
                break;

            case 7:
                setToBuy = Colours.blue;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + chosen);
        }
        Property currentProperty;
        for (int i =0;i<board.getTileGrid().length;i++){
            if(board.getTileGrid()[i] instanceof Property){
                currentProperty = (Property) board.getTileGrid()[i];
                if(currentProperty.getColour().equals(setToBuy)){
                    if(currentProperty.getCurrentHouseLevel()<5) {
                        try {
                            currentProperty.buyHouse(1);
                            brought = true;
                        }
                        catch(NotEnoughMoneyException e){
                            throw new NotEnoughMoneyException(e.getMessage(),e.moneyShort,e.player);
                        }
                    }
                    else{
                        purchasable[chosen] = false;
                    }
                }
            }
        }
        boolean checkIfMoreToBuy = false;
        for(int i = 0;i<purchasable.length;i++){
            if(purchasable[i] == true){
                checkIfMoreToBuy = true;
            }
        }
        if(checkIfMoreToBuy == false){
            brought = true;
        }
        return brought;
    }
}
