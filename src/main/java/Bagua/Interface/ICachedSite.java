package Bagua.Interface;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.JsonTool;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public abstract class ICachedSite
{
    protected HashMap<String, CoinInfo> Cached;

    protected JSONParser                Parser;
    protected InputStreamReader         SiteReader;
    protected URL                       Site;

    public ICachedSite(String SiteURL) throws Exception
    {
        // Creating new common site parser.
        Parser      = new JSONParser();
        Cached      = new HashMap<String,CoinInfo>();

        // Initialize Reader.
        Site        = new URL(SiteURL);
        SiteReader  = new InputStreamReader(Site.openStream(), "UTF-8");
    }
    
    // 사이트로부터 오브젝트를 읽어옵니다.
    protected Object getObject()
    {
        Object object = null;

        try 
        {
            // Parse Site. and emit on object.
            object = (JSONObject)Parser.parse(SiteReader);
        }
        catch(Exception e)  // TODO : emit exception here.
        {

        }
        return object;
    }

    // 루트에 있는 오브젝트를 구해줍니다.
    // 예시 : getObject(new String[] {"result", "objects", "currency"})
    //          result : {
    //              objects : {
    //                  currency : {
    //                      bid : 1.23
    //                      ask : 1.22
    //                  }
    //              }
    //          }
    // ask, bid 의 값이 담겨있는 Map을 반환합니다.
    protected Object getObject(String[] Tokens)
    {
        Object object = getObject();
        return JsonTool.ParseWithTokenDepth(object, Tokens);
    }

    // 캐시된 코인들에 대한 키를 만듭니다.
    protected String CreateKey(EnumCoins coin)
    {
        String Token = coin.toString();

        if(coin == EnumCoins._1ST | coin == EnumCoins._2GIVE)
        {
            // 앞부분의 _을 잘라냅니다.
            Token = Token.substring(1);
        }

        return Token;
    }
}