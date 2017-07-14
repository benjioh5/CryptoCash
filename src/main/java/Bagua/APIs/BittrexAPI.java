package Bagua.APIs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;
import Bagua.Interface.ICachedSite;

public class BittrexAPI implements ICachedSite
{
    final private String MarketToken = "MarketName";
    final private String BidToken = "Bid";
    final private String AskToken = "Ask";
    final private String HighestIn24Hr = "High";
    final private String LowestIn24Hr = "Low";

    final private String BittrexURL = "https://bittrex.com/api/v1.1/public/getmarketsummaries";

    Map<String, CoinPrice> CachedPrice;

    public BittrexAPI()
    {
        CachedPrice = new HashMap<String, CoinPrice>();
    }

    public void Refresh() throws IOException, ParseException
    {
        URL                 URLTarget = new URL(BittrexURL);
        InputStreamReader   URLReader = new InputStreamReader(URLTarget.openStream());
        JSONParser          URLParser = new JSONParser();


        Map<String, List< Map < String, String > > > ParsedInfo = (Map)URLParser.parse(URLReader);


        for(Map<String, String> CoinInfo : ParsedInfo.get("result"))
        {
            final String BTC_Prefix = "BTC-";

            if(CoinInfo.get(MarketToken).startsWith(BTC_Prefix) == false)
            {
                continue;
            }

            CoinPrice newPrice = new CoinPrice();

            newPrice.Bid = Double.parseDouble(CoinInfo.get(BidToken));
            newPrice.Ask = Double.parseDouble(CoinInfo.get(AskToken));
            newPrice.Highest = Double.parseDouble(CoinInfo.get(HighestIn24Hr)); 
            newPrice.Lowest  = Double.parseDouble(CoinInfo.get(LowestIn24Hr));

            CachedPrice.put(CoinInfo.get(MarketToken).substring(4), newPrice);
        }
    }
    public CoinPrice getPrice(EnumCoin coin)
    {
        return CachedPrice.get(coin.toString());
    }
}