package Bagua.APIs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;
import Bagua.Interface.ICachedSite;

public class PloniexAPI implements ICachedSite
{
    final String PloniexURL = "https://poloniex.com/public?command=returnTicker";

    final private String BidToken = "highestBid";
    final private String AskToken = "lowestAsk";
    final private String HighestIn24Hr = "high24hr";
    final private String LowestIn24Hr = "low24hr";

    EnumCoin[] AvailableCoins =
    {
        EnumCoin.BCN,   EnumCoin.BELA,  EnumCoin.BLK,  EnumCoin.BTCD, EnumCoin.BTM,  EnumCoin.BTS,  EnumCoin.BURST,
        EnumCoin.CLAM,  EnumCoin.DASH,  EnumCoin.DGB,  EnumCoin.DOGE, EnumCoin.EMC2, EnumCoin.FLDC, EnumCoin.FLO, 
        EnumCoin.GAME,  EnumCoin.GRC,   EnumCoin.HUC,  EnumCoin.LTC,  EnumCoin.MAID, EnumCoin.OMNI, EnumCoin.NAUT,
        EnumCoin.NAV,   EnumCoin.NEOS,  EnumCoin.NMC,  EnumCoin.NOTE, EnumCoin.NXT,  EnumCoin.PINK, EnumCoin.POT,
        EnumCoin.PPC,   EnumCoin.RIC,   EnumCoin.SJCX, EnumCoin.STR,  EnumCoin.SYS,  EnumCoin.VIA,  EnumCoin.XVC, 
        EnumCoin.VTC,   EnumCoin.XBC,   EnumCoin.XCP,  EnumCoin.XEM,  EnumCoin.XMR,  EnumCoin.XPM,  EnumCoin.XRP, 
        EnumCoin.SC,    EnumCoin.BCY,   EnumCoin.EXP,  EnumCoin.FCT,  EnumCoin.RADS, EnumCoin.AMP,  EnumCoin.DCR, 
        EnumCoin.LBC,   EnumCoin.STEEM, EnumCoin.SBD,  EnumCoin.ETC,  EnumCoin.REP,  EnumCoin.ARDR, EnumCoin.ZEC,
        EnumCoin.START, EnumCoin.NXC,   EnumCoin.PASC, EnumCoin.GNT,  EnumCoin.GNO,  EnumCoin.LSK,  EnumCoin.ETH,
        EnumCoin.VRC,
    };

    Map<EnumCoin, CoinPrice> CachedPrice;

    public PloniexAPI()
    {
        CachedPrice = new HashMap<EnumCoin, CoinPrice>();
    }

    public void Refresh() throws IOException, ParseException
    {
        URL                 URLTarget = new URL(PloniexURL);
        InputStreamReader   URLReader = new InputStreamReader(URLTarget.openStream());
        JSONParser          URLParser = new JSONParser();

        Map<String, Map<String, String>>    ParsedInfo = (Map)URLParser.parse(URLReader);


        // Clearing all caches from map.
        CachedPrice.clear();


        for(EnumCoin coin : AvailableCoins)
        {
            final String BTC_Prefix = "BTC_";

            Map<String, String> CoinInfo = ParsedInfo.get(BTC_Prefix + coin.toString());


            CoinPrice newPrice = new CoinPrice();

            newPrice.Bid = Double.parseDouble(CoinInfo.get(BidToken));
            newPrice.Ask = Double.parseDouble(CoinInfo.get(AskToken));
            newPrice.Highest = Double.parseDouble(CoinInfo.get(HighestIn24Hr)); 
            newPrice.Lowest  = Double.parseDouble(CoinInfo.get(LowestIn24Hr));

            CachedPrice.put(coin, newPrice);
        }
    }
    public CoinPrice getPrice(EnumCoin coin)
    {
        return CachedPrice.get(coin);
    }
}