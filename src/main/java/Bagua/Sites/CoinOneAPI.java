package Bagua.Sites;

import java.util.HashMap;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;
import Bagua.Exception.UnSupportCoinType;

public class CoinOneAPI extends ISite {

    private String URL = "https://api.coinone.co.kr/orderbook/";
    HashMap<String, CoinInfo> Cached;

    final private String BidToken = "Bid";
    final private String AskToken = "Ask";


    EnumCoins[] AvailableCoins = {

            EnumCoins.ETH   ,EnumCoins.ETC  ,EnumCoins.XRP
    };


    public CoinOneAPI() throws Exception
    {
        Cached = new HashMap<String, CoinInfo>();
    }


    public CoinInfo getCoinInfo(EnumCoins coin)
    {
        return Cached.get(CreateKey(coin));
    }

    public void Refresh() {
        Cached.clear();

        for(EnumCoins cointype : AvailableCoins){
            String urlTemp;
            SiteParser siteParser;
            try{
                urlTemp = URL + CreateKey(cointype);
                siteParser = new SiteParser(urlTemp);
            }catch(UnSupportCoinType exception){
                continue;
            }catch(Exception exception){
                continue;
            }


            CoinInfo newInfo = new CoinInfo();

            ArrayList<JSONObject> bidContext = (ArrayList<JSONObject>) siteParser.getObject(
                    new String[] { BidToken }
            );

           newInfo.Bid = Double.parseDouble(bidContext[0].get("price"));



            ArrayList<JSONObject> askContext  = (ArrayList<JSONObject>) siteParser.getObject(
                    new String[] { AskToken }
            );

            newInfo.Ask = Double.parseDouble(askContext[0].get("price"));

            Cached.put(CreateKey(cointype), newInfo);
        }

    }

}