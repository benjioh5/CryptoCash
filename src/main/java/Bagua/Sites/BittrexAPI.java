package Bagua.Sites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.SiteParser;
import Bagua.Interface.ISite;


public class BittrexAPI extends ISite
{
    final private String MarketToken = "MarketName";
    final private String BidToken = "Bid";
    final private String AskToken = "Ask";
    final private String HighestIn24Hr = "High";
    final private String LowestIn24Hr = "Low";

    SiteParser                Parser;
    HashMap<String, CoinInfo> Cached;


    public BittrexAPI() throws Exception
    {
        // Create As Bittrex Website;
        // This can throws exception when is failed to connect.
        Parser = new SiteParser("https://bittrex.com/api/v1.1/public/getmarketsummaries");
        Cached = new HashMap<String, CoinInfo>();
    }

    private boolean isaBitcoinVeriusRatio(String token)
    {
        return token.startsWith("BTC-");
    }

    public CoinInfo getCoinInfo(EnumCoins coin)
    {
        return Cached.get(CreateKey(coin));
    }

    public void Refresh()
    {
        // 다시 캐시하기전에 모든 캐시된 값을 클리어합니다.
        Cached.clear();

        ArrayList<JSONObject> InfoArray = (ArrayList<JSONObject>) Parser.getObject(
            // Get into depth...
            new String[] { "result" }
        );

        for(Map<String, String> InfoObject : InfoArray)
        {
            // 마켓의 이름을 가져옵니다.
            String marketName   = InfoObject.get(MarketToken);

            // BTC 을 기준으로 한 코인의 값이 아니라면 다음값을 구합니다.
            if(isaBitcoinVeriusRatio(marketName) == false) 
            {
                continue;
            }
            
            // 사이트로부터 값을 가져옵니다.
            // 사이트의 값들은 String값이므로 Double로 파싱이 필요합니다.
            CoinInfo newInfo = new CoinInfo();

            newInfo.Ask         = Double.parseDouble(InfoObject.get(BidToken));
            newInfo.Bid         = Double.parseDouble(InfoObject.get(AskToken));
            newInfo.Max24Hr     = Double.parseDouble(InfoObject.get(HighestIn24Hr));
            newInfo.Min24Hr     = Double.parseDouble(InfoObject.get(LowestIn24Hr));

            // 앞부분의 "BTC-"" 부분을 잘라냅니다.
            String TargetCoin   = marketName.substring(4);

            // 캐시된값을 넣습니다.
            Cached.put(TargetCoin, newInfo);
        }
    }
}