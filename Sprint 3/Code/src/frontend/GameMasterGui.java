package frontend;

import backend.*;

public class GameMasterGui extends GameMaster {
    public GameMasterGui(int numPlayers, Tokens[] playerTokens){
        this.setup(numPlayers, playerTokens);
    }

    public void landedTileAction() {
        if (this.getBuyable(this.getPlayer(this.getCurTurn()).getPlace())){
            //can buy/auction time
            buyingTile();
        }
        else if (this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace()).getClass() == CardDraw.class){
            //card draw
            cardDraw();
        }
        // free parking
        // go to jail
        else{
            //rent time
        }
    }

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

    private void cardDraw() {
        // OK or Potluck?
        //if (((Card)this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).)
            // get method effect potluck
        //else{
            // get method effect ok
        //}
    }
}
