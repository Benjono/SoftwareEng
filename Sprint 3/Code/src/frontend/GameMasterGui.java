package frontend;

import backend.BuyableTile;
import backend.GameMaster;
import backend.Tokens;

public class GameMasterGui extends GameMaster {
    public GameMasterGui(int numPlayers, Tokens[] playerTokens){
        this.setup(numPlayers, playerTokens);
    }

    public void landedTileAction() {
        if (this.getBuyable(this.getPlayer(this.getCurTurn()).getPlace())){
            //can buy/auction time
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
        else{
            //rent time
        }
    }
}
