package Bagua;

import java.util.EnumMap;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.EnumSites;
import Bagua.Interface.ISite;
import Bagua.Sites.*;

public class CryptoCash
{
    // Singleton Implements.
    private static CryptoCash Instance;
    public static CryptoCash createInstance() throws Exception
    {
        if(Instance == null)
        {
            Instance = new CryptoCash();
        }
        return Instance;
    }
    public static CryptoCash getInstance()
    {
        return Instance;
    }


    // Real implements.
    EnumMap<EnumSites, ISite> Sites;

    CryptoCash() throws Exception
    {
        Sites.put(EnumSites.Bittrex, new BittrexAPI());
    }
    public void Refresh(EnumSites site)
    {
        Sites.get(site).Refresh();
    }
    public CoinInfo getCoinInfo(EnumSites site, EnumCoins coin)
    {
        return Sites.get(site).getCoinInfo(coin);
    }
}