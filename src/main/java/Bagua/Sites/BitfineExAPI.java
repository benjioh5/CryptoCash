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
public class BitfineExAPI extends ISite {

    private String URL = "https://api.bitfinex.com/v1/pubticker/"; // + XXXBTC

    private HashMap<String, CoinInfo> Cached;
    private EnumCoins[] AvailableCoins = {
        EnumCoins.ETH, EnumCoins.BTC, EnumCoins.LTC,EnumCoins.XMR, 
        EnumCoins.ETC, EnumCoins.ZEC, EnumCoins.DASH, EnumCoins.XRP, 
        /* EnumCoins.EOS, EnumCoins.IOTA, EnumCoins.BSS, EnumCoins.BCU, */
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

            for(EnumCoins coin : AvailableCoins) {
                CoinInfo newInfo = new CoinInfo();
                Parser = new SiteParser(URL + coin.toString() + "BTC");
                JSONObject need_to_parse = (JSONObject) Parser.getObject();

                newInfo.Ask         = Double.parseDouble(need_to_parse.get("ask").toString());
                newInfo.Bid         = Double.parseDouble(need_to_parse.get("bid").toString());
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
