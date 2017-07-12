package Bagua.Sites;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;

import java.util.HashMap;

/**
 * Created by benjioh5 on 12/07/2017.
 */
public class BitfineExAPI extends ISite {

    private String URL = "https://api.bitfinex.com/v1/pubticker"; // + XXXBTC

    private HashMap<String, CoinInfo> Cached;
    private EnumCoins[] AvailableCoins = {
        EnumCoins.ETH, EnumCoins.BTC, EnumCoins.LTC, EnumCoins.EOS,
        EnumCoins.IOTA, EnumCoins.ETC, EnumCoins.ZEC, EnumCoins.DASH,
        EnumCoins.XRP, EnumCoins.XMR, EnumCoins.BSS, EnumCoins.BCU,
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

                newInfo.Ask         = Double.parseDouble(need_to_parse.getDouble("ask"));
                newInfo.Bid         = Double.parseDouble(need_to_parse.getDouble("bid"));
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
