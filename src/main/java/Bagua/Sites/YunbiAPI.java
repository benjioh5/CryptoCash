package Bagua.Sites;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;

import java.util.HashMap;

import org.json.simple.JSONObject;

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

    public YunbiAPI() {
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
                String[] string = { CreateKey(coin).toLowerCase() + "cny" };
                JSONObject need_to_parse = (JSONObject) Parser.getObject(string);

                newInfo.Ask         = Double.parseDouble(need_to_parse.get("buy").toString());
                newInfo.Bid         = Double.parseDouble(need_to_parse.get("sell").toString());
                newInfo.Max24Hr     = Double.parseDouble(need_to_parse.get("high").toString());
                newInfo.Min24Hr     = Double.parseDouble(need_to_parse.get("low").toString());
                
                Cached.put(CreateKey(coin), newInfo);
            }
        }
        catch (Exception Ect){
            return ;
        }


    }


}
