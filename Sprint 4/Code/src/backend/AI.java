package backend;

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
     * @throws triggerAuctionException - is thrown if auction is needed
     */
    public void purchaseOrAuction(Property property) throws triggerAuctionException{
        if(getMoney()<property.costToBuy){
            throw new triggerAuctionException("auction");
        }
        else{
            //50/50 on buy or auction
            int comparitor = random.nextInt(100);
            if(comparitor <50){
                throw new triggerAuctionException("auction");
            }
            else{
                property.buyTile(this);
            }
        }
    }

    /**
     * decides how much to bid for a property in an auction
     * @param property - property being auctioned
     * @return - how much the AI is willing to bid on the property
     */
    public int bid(Property property){
        int currentOffer =(int) Math.floor(property.getCostToBuy()*0.9);
        currentOffer+=(Math.floor(currentOffer*(random.nextFloat()/2)));
        if(currentOffer>getMoney()){
            currentOffer = getMoney();
        }

        return currentOffer;
    }
    //after roll stuff e.g. house buying and finance raising
    public void optionalStuff(){

    }
}
