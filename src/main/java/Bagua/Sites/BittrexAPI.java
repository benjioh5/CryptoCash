package Bagua.Sites;

import java.util.List;
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
        // �ٽ� ĳ���ϱ����� ��� ĳ�õ� ���� Ŭ�����մϴ�.
        Cached.clear();

        List<JSONObject> InfoArray = (List<JSONObject>) Parser.getObject(
            // Get into depth...
            new String[] { "result" }
        );

        for(Map<String, String> InfoObject : InfoArray)
        {
            // ������ �̸��� �����ɴϴ�.
            String marketName   = InfoObject.get(MarketToken);

            // BTC �� �������� �� ������ ���� �ƴ϶�� �������� ���մϴ�.
            if(isaBitcoinVeriusRatio(marketName) == false) 
            {
                continue;
            }
            
            // ����Ʈ�κ��� ���� �����ɴϴ�.
            // ����Ʈ�� ������ String���̹Ƿ� Double�� �Ľ��� �ʿ��մϴ�.
            CoinInfo newInfo = new CoinInfo();

            newInfo.Ask         = Double.parseDouble(InfoObject.get(BidToken));
            newInfo.Bid         = Double.parseDouble(InfoObject.get(AskToken));
            newInfo.Max24Hr     = Double.parseDouble(InfoObject.get(HighestIn24Hr));
            newInfo.Min24Hr     = Double.parseDouble(InfoObject.get(LowestIn24Hr));

            // �պκ��� "BTC-"" �κ��� �߶���ϴ�.
            String TargetCoin   = marketName.substring(4);

            // ĳ�õȰ��� �ֽ��ϴ�.
            Cached.put(TargetCoin, newInfo);
        }
    }
}