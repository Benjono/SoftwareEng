package frontend;

import backend.*;

/**
 * Extends the backend GameMaster in order to easily implement on the frontend. This when called sets up the game,
 * It has methods for when landing on a tile, buying a tile and drawing a card.
 * @author Joe C
 */
public class GameMasterGui extends GameMaster {
    private int plNum;
    private int okNum;

    public GameMasterGui(int numPlayers, Tokens[] playerTokens){
        this.setup(numPlayers, playerTokens);
        this.okNum = 0;
        this.plNum = 0;
    }

    /**
     * Works out what to do when a player lands on a specific tile. buy it, draw a card, etc.
     */
    public void landedTileAction() {
        if (this.getBuyable(this.getPlayer(this.getCurTurn()).getPlace())){
            //can buy/auction time
            buyingTile();
        }
        else if (this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof CardDraw){
            //card draw
            cardDraw();
        }
        else{
            //rent time
            // free parking
            // go to jail
            this.applyTileEffect();
            new TileEffectDialog(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
        }
    }

    /**
     * Implements the first time buying a property by working out whether it is being auctioned or brought for base price,
     * it then implements this.
     */
    private void buyingTile(){
        if(!(Boolean) (new FirstTimeLandedDialog(this)).getR()){
            // auction
            this.applyTileEffect((int[]) (new AuctionDialog(this)).getR());
            // winner dialog
            System.out.println(((BuyableTile)this.getBoard().getTileGrid()[this.getPlayer(this.getCurTurn()).getPlace()]).getPlayer().getToken());

            //needs winner dialog here
        }
        else {
            // player buys tile
            this.applyTileEffect(this.getPlayer(this.getCurTurn()));
        }
    }

    /**
     * Implements the card draw effects and determines the next card that will be drawn.
     */
    private void cardDraw() {
        // OK or Potluck?
        if (((CardDraw) this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).getDrawType() == DrawTypes.opportunityKnocks) {
            // get method effect opportunity knocks
            ((Card)this.getBoard().getOpportunityKnocks().get(okNum)).cardEffect(this.getPlayer(this.getCurTurn()));
            new CardDrawDialog(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()),(Card) this.getBoard().getOpportunityKnocks().get(okNum));
            okNum++;
            if (okNum >= 16){okNum = 0;}
        }
        else{
            // get method effect potluck
            ((Card)this.getBoard().getPotLuck().get(plNum)).cardEffect(this.getPlayer(this.getCurTurn()));
            new CardDrawDialog(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()),(Card) this.getBoard().getPotLuck().get(plNum));
            plNum++;
            if (plNum >= 16){plNum = 0;}
        }
    }
}
