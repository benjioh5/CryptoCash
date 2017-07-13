package Bagua.Sites;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;

import java.util.HashMap;

/**
 * Created by benjioh5 on 12/07/2017.
 */
public class YunbiAPI extends ISite {

    private String URL = "https://yunbi.com//api/v2/tickers.json";

    private HashMap<String, CoinInfo> Cached;
    private EnumCoins[] AvailableCoins = {
        EnumCoins.BTC, EnumCoins.ETH, EnumCoins.ZEC,
        /*EnumCoins.QTUM, EnumCoins.GXS, EnumCoins.EOS,*/ // Doesn't exist in enumcoins. need to be added.
        EnumCoins.ANS, EnumCoins.SC, EnumCoins.DGD,
        EnumCoins._1ST, EnumCoins.BTS, EnumCoins.GNT,
        EnumCoins.REP, EnumCoins.ETC,
    };
    SiteParser Parser;

    public BitfineExAPI() {
        Cached = new HashMap<String, CoinInfo>();
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

            for(EnumCoins coin : AvailableCoins) {
                CoinInfo newInfo = new CoinInfo();
                JSONObject need_to_parse = (JSONObject) Parser.getObject("{ " + coin.toString() +"cny }");

                newInfo.Ask         = Double.parseDouble(need_to_parse.getDouble("buy"));
                newInfo.Bid         = Double.parseDouble(need_to_parse.getDouble("sell"));
                newInfo.Max24Hr     = Double.parseDouble(need_to_parse.getDouble("high"));
                newInfo.Min24Hr     = Double.parseDouble(need_to_parse.getDouble("low"));
                
                Cached.put(CreateKey(coin), newInfo);
            }
        }
        catch (Exception Ect){
            return ;
        }


    }


}
