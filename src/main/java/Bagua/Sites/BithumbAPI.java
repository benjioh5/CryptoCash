package Bagua.Sites;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;

import java.util.HashMap;

/**
 * Created by rinechran on 12/07/2017.
 */
public class BithumbAPI extends ISite {


    public BithumbAPI() {
        Cached = new  HashMap<String, CoinInfo>();
    }

    public CoinInfo getCoinInfo(EnumCoins coin)
    {
        return Cached.get(CreateKey(coin));
    }

    public void Refresh()
    {
        Cached.clear();
        try{
            Parser = new SiteParser(URL);
        }
        catch (Exception Ect){
            return ;
        }


    }

    private String URL = "https://api.bithumb.com/public/orderbook/ALL";

    private HashMap<String, CoinInfo> Cached;
    private EnumCoins[] AvailableCoins = {

            EnumCoins.BTC   ,EnumCoins.ETC  ,EnumCoins.XRP
            ,EnumCoins.DASH , EnumCoins.LTC, EnumCoins.ETC, EnumCoins.XRP
    };
    SiteParser Parser;


}
